//
// Created by SpreadZhao on 2024/3/10.
//

#include "hello.h"

jstring helloRTC(JNIEnv *env, jobject thiz) {
    DCStr str = DXP::HelloStrNew();
    jstring res = env->NewStringUTF(str);
    return res;
}