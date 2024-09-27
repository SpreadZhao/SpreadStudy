//
// Created by SpreadZhao on 2024/2/3.
//

#ifndef NATIVESTUDY_COMMON_H
#define NATIVESTUDY_COMMON_H

#include <jni.h>
#include <string>
#include <android/log.h>
#include <vector>
#include <unordered_map>

static const char *TAG = "SpreadNative";

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)

#define SIZE_OF_METHOD(X) (sizeof(X) / sizeof(JNINativeMethod))

void timerPrepare(JavaVM *);
void testRegister(JNIEnv *, jobject);
jint createStream(JNIEnv *, jobject);
void destroyStream(JNIEnv *, jobject);
jint playSound(JNIEnv *, jobject thiz, jboolean);

typedef JNINativeMethod Methods[];

static const std::string class_name_simple_timer = "com/spread/nativestudy/fragments/SimpleTimerFragment";
static Methods methods_simple_timer = {
        {"testRegister", "()V", (void *)(testRegister)}
};

static const std::string class_name_oboe = "com/spread/nativestudy/fragments/OboeFragment";
static Methods methods_oboe = {
        {"createStream", "()I", (void *)(createStream)},
        {"destroyStream", "()V", (void *)(destroyStream)},
        {"playSound", "(Z)I", (void *)(playSound)}
};

static inline void registerJMethods(JNIEnv *env, std::string className, JNINativeMethod *methods, size_t size) {
    jclass clz = env->FindClass(className.c_str());
    if (clz == nullptr) return;
    env->RegisterNatives(clz, methods, size);
}

#endif //NATIVESTUDY_COMMON_H


