//
// Created by spreadzhao on 3/20/24.
//

#ifndef SPREAK_LOG_H
#define SPREAK_LOG_H

#include <stdio.h>
#define LOG_TAG "SPREAK.NATIVE"
#define REPORT_MSG_BUF_SIZE 256

#if ANDROID
#include <android/log.h>

static constexpr char cond = false;
#define LOGF(format, ...) __android_log_assert(&cond, LOG_TAG, format, ##__VA_ARGS__)
#define LOGE(format, ...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, format, ##__VA_ARGS__)
#define LOGW(format, ...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, format, ##__VA_ARGS__)
#define LOGI(format, ...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, format, ##__VA_ARGS__)

#ifndef NDEBUG
#define NDEBUG
#endif

#ifdef NDEBUG
#define LOGD(format, ...)
#define LOGV(format, ...)
#else
#define LOGD(format, ...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, format, ##__VA_ARGS__)
#define LOGV(format, ...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, format, ##__VA_ARGS__)
#endif


#endif //SPREAK_LOG_H
