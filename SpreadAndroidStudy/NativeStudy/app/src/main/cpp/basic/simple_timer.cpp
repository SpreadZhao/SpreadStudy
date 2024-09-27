//
// Created by SpreadZhao on 2024/2/3.
//
#include "../common.h"
#include <chrono>
#include <ctime>
#include <sstream>
#include <iomanip>

typedef struct tick_context {
    JavaVM *javaVm;
    jclass jniHelperClz;
    jobject jniHelperObj;
    jclass simpleTimerClz;
    jobject simpleTimerObj;
    pthread_mutex_t mutex;
    bool interrupted;
} TickContext;

TickContext context;


void testRegister(JNIEnv *env, jobject thiz) {
    LOGI("Hello World!");
}

void timerPrepare(JavaVM *vm) {
    memset(&context, 0, sizeof(context));
    context.javaVm = vm;
    context.interrupted = false;
    context.simpleTimerObj = nullptr;
}

std::string getCurrTimeStr() {
    auto now = std::chrono::system_clock::now();
    auto now_c = std::chrono::system_clock::to_time_t(now);
    std::tm *local_now = std::localtime(&now_c);
    std::stringstream ss;
    ss << "curr time: " << std::put_time(local_now, "%Y-%m-%d %H:%M:%S");
    return ss.str();
}

jstring stdStringToJString(JNIEnv *env, const std::string &str) {
    size_t len = str.size();
    jcharArray chars = env->NewCharArray(len);
    if (chars == nullptr) {
        return nullptr;
    }
    env->SetCharArrayRegion(chars, 0, len, reinterpret_cast<const jchar*>(str.data()));
    jstring jStr = env->NewString(reinterpret_cast<const jchar *>(chars), len);
    env->DeleteLocalRef(chars);
    return jStr;
}

jstring stdStringToJString2(JNIEnv *env, const std::string &str) {
    return env->NewStringUTF(str.c_str());
}

void *UpdateTime(void *ctx) {
    TickContext *pctx = (TickContext *)ctx;
    JavaVM *jvm = pctx->javaVm;
    JNIEnv *env;
    jint res = jvm->GetEnv((void **)&env, JNI_VERSION_1_6);
    if (res != JNI_OK) {
        res = jvm->AttachCurrentThread(&env, nullptr);
        if (res != JNI_OK) {
            LOGE("Failed to AttachCurrentThread, ErrorCode: %d", res);
            pthread_exit(nullptr);
        }
    }
    const std::string time_str = getCurrTimeStr();
    LOGI("%s", time_str.c_str());
    jmethodID setCurrTime = env->GetMethodID(pctx->simpleTimerClz,
                                             "setCurrTime",
                                             "(Ljava/lang/String;)V");
    jstring time_jstr = stdStringToJString2(env, time_str);
    env->CallVoidMethod(pctx->simpleTimerObj, setCurrTime, time_jstr);
    pthread_exit(nullptr);
}

void *StartTimer(void *ctx) {
    LOGI("timer thread id: %d", pthread_gettid_np(pthread_self()));
    TickContext *pctx = (TickContext *)ctx;
    JavaVM *jvm = pctx->javaVm;
    if (jvm == nullptr) {
        LOGE("jvm is null.");
        pthread_exit(nullptr);
    }
    JNIEnv *env;
    jint res = 0;
    jvm->GetEnv((void **)&env, JNI_VERSION_1_6);
    res = jvm->AttachCurrentThread(&env, nullptr);
    LOGI("Attach return: %d", res);
    jmethodID updateTime = env->GetMethodID(pctx->simpleTimerClz, "updateTime", "()V");
    struct timespec sleepTime;
    sleepTime.tv_sec = 1;
    sleepTime.tv_nsec = 0;
    pctx->interrupted = false;
    while (true) {
        if (pctx->interrupted) {
            LOGI("Timer interrupted");
            break;
        }
        env->CallVoidMethod(pctx->simpleTimerObj, updateTime);
        nanosleep(&sleepTime, nullptr);
    }
    jvm->DetachCurrentThread();
    pthread_exit(nullptr);
}

extern "C"
JNIEXPORT jstring
JNICALL
Java_com_spread_nativestudy_fragments_SimpleTimerFragment_getDescription(JNIEnv *env,
                                                                         jobject thiz) {
#if defined(__arm__)
#if defined(__ARM_ARCH_7A__)
#if defined(__ARM_NEON__)
#if defined(__ARM_PCS_VFP)
#define ABI "armeabi-v7a/NEON (hard-float)"
#else
#define ABI "armeabi-v7a/NEON"
#endif
#else
#if defined(__ARM_PCS_VFP)
#define ABI "armeabi-v7a (hard-float)"
#else
#define ABI "armeabi-v7a"
#endif
#endif
#else
#define ABI "armeabi"
#endif
#elif defined(__i386__)
#define ABI "x86"
#elif defined(__x86_64__)
#define ABI "x86_64"
#elif defined(__mips64) /* mips64el-* toolchain defines __mips__ too */
#define ABI "mips64"
#elif defined(__mips__)
#define ABI "mips"
#elif defined(__aarch64__)
#define ABI "arm64-v8a"
#else
#define ABI "unknown"
#endif
    return env->NewStringUTF("Hello from JNI !  Compiled with ABI " ABI ".");
}

extern "C"
JNIEXPORT void JNICALL
Java_com_spread_nativestudy_fragments_SimpleTimerFragment_requestForCurrTime(JNIEnv *env, jobject thiz) {
    const std::string curr_time = getCurrTimeStr();
    LOGI("%s", curr_time.c_str());
    jstring curr_time_j = stdStringToJString2(env, curr_time);
    jclass clz = env->GetObjectClass(thiz);
    jmethodID setCurrTime = env->GetMethodID(clz, "setCurrTime", "(Ljava/lang/String;)V");
    env->CallVoidMethod(thiz, setCurrTime, curr_time_j);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_spread_nativestudy_fragments_SimpleTimerFragment_startTimer(JNIEnv *env, jobject thiz) {
    jclass clz = env->GetObjectClass(thiz);
    context.simpleTimerClz = static_cast<jclass>(env->NewGlobalRef(clz));
    context.simpleTimerObj = env->NewGlobalRef(thiz);
    pthread_t th;
    pthread_create(&th, nullptr, StartTimer, &context);
    // 调用这句直接ANR。因为执行它的是应用的主线程，直接就卡斯了。
//    pthread_join(th, nullptr);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_spread_nativestudy_fragments_SimpleTimerFragment_stopTimer(JNIEnv *env, jobject thiz) {
    env->DeleteGlobalRef(context.simpleTimerClz);
    env->DeleteGlobalRef(context.simpleTimerObj);
    context.interrupted = true;
}