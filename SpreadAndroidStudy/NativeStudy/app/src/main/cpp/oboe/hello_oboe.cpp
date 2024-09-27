//
// Created by spreadzhao on 2/13/24.
//
#include "OboeSinePlayer.h"

static OboeSinePlayer *player = nullptr;

jint createStream(JNIEnv *env, jobject thiz) {
    player = new OboeSinePlayer();
    return player ? 0 : -1;
}

void destroyStream(JNIEnv *env, jobject thiz) {
    if (player == nullptr) return;
    delete player;
    player = nullptr;
}

jint playSound(JNIEnv *env, jobject thiz, jboolean enable) {
    jint result = 0;
    if (player) {
        player->enable(enable);
    } else {
        result = -1;
    }
    return result;
}