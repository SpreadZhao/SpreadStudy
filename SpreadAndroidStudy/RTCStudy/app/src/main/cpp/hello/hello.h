//
// Created by SpreadZhao on 2024/3/10.
//

#ifndef RTCSTUDY_HELLO_H
#define RTCSTUDY_HELLO_H

#include "../common/common.h"
#include "../common/platform.h"
#include "../common/type.h"
#include "../common/DXP.h"

// Function declaration comes first! Or helloRTC() etc. are not declared!

jstring helloRTC(JNIEnv *env, jobject thiz);

static const std::string class_name_hello_rtc = "com/example/rtcstudy/fragment/HelloRTCFragment";
static JNINativeMethod methods_hello_rtc[] = {
        {"helloRTC", "()Ljava/lang/String;", (void *) (helloRTC)}
};


#endif //RTCSTUDY_HELLO_H
