//
// Created by SpreadZhao on 2024/3/10.
//

#define SIZE_OF_METHOD(X) (sizeof(X) / sizeof(JNINativeMethod))

#include "hello/hello.h"

static inline void registerJMethods(JNIEnv *env,
                                    std::string className,
                                    JNINativeMethod *methods,
                                    size_t size) {
    jclass clz = env->FindClass(className.c_str());
    if (clz == nullptr) return;
    env->RegisterNatives(clz, methods, size);
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    registerJMethods(env, class_name_hello_rtc, methods_hello_rtc,
                     SIZE_OF_METHOD(methods_hello_rtc));
    return JNI_VERSION_1_6;
}