#include "../common.h"

/**
 * Main
 */
extern "C"
JNIEXPORT jstring
JNICALL
Java_com_spread_nativestudy_fragments_SimpleClassNameFragment_stringFromJNI(
        JNIEnv *env,
        jobject thiz) {
    jclass jclass_mainActivity = env->GetObjectClass(thiz);
    jmethodID method_getClass = env->GetMethodID(jclass_mainActivity, "getClass",
                                                 "()Ljava/lang/Class;");
    jobject classOf_MainActivity = env->CallObjectMethod(thiz, method_getClass);

    jclass jclass_mainActivityClass = env->GetObjectClass(classOf_MainActivity);
    jmethodID method_getName = env->GetMethodID(jclass_mainActivityClass, "getName",
                                                "()Ljava/lang/String;");
    jstring className = static_cast<jstring>(env->CallObjectMethod(classOf_MainActivity,
                                                                   method_getName));

    LOGE("class name: %s", env->GetStringUTFChars(className, 0));
    return className;
}