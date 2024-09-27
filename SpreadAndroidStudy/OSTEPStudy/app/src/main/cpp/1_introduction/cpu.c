//
// Created by SpreadZhao on 2024/3/24.
//
#include <stdlib.h>
#include "common.h"
#include <jni.h>


JNIEXPORT void JNICALL
Java_com_spread_ostepstudy_chapter_1fragment_introduction_CPUFragment_spin(JNIEnv *env,
                                                                           jobject thiz,
                                                                           jint howlong) {
    Spin(howlong);
}