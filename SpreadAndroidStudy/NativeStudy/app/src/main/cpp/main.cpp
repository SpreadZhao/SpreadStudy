//
// Created by spreadzhao on 2/13/24.
//
#include "common.h"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv((void **)&env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    timerPrepare(vm);
    registerJMethods(env, class_name_simple_timer, methods_simple_timer,
                     SIZE_OF_METHOD(methods_simple_timer));
    registerJMethods(env, class_name_oboe, methods_oboe, SIZE_OF_METHOD(methods_oboe));
    return JNI_VERSION_1_6;
}