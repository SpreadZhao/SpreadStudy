#include "DXP.h"
#include <unistd.h>
#include <android/log.h>

DCStr DXP::GetOSName() {
#if defined(BUILD_FOR_ANDROID)
    return "Android";
#else
    return "Other";
#endif
}

DCStr DXP::HelloStr() {
    DCStr osName = GetOSName();
    const int size = sizeof(osName);
    // 不能返回栈上的变量！
    // TODO: 内存泄露？
    char *str = new char[size + 7];
//    char str[size + 7];
    strcpy(str, "Hello ");
    strcat(str, osName);
    return str;
}

DCStr DXP::HelloStrNew() {
    DCStr osName = GetOSName();
    // 智能指针
    std::unique_ptr<std::string> str = std::make_unique<std::string>("Hello ");
    *str += osName;
    return str->c_str();
}

DUInt32 DXP::GetTickCount32() {
    struct timeval tv;
    gettimeofday(&tv, nullptr);
    DUInt32 millis =
            static_cast<DUInt32>(tv.tv_sec * 1000) + static_cast<DUInt32>(tv.tv_usec / 1000);
    return millis;
}

DUInt64 DXP::GetTickCount64() {
    struct timeval tv;
    gettimeofday(&tv, nullptr);
    DUInt64 millis =
            static_cast<DUInt64>(tv.tv_sec * 1000) + static_cast<DUInt64>(tv.tv_usec / 1000);
    return millis;
}

DVoid DXP::SleepSec(DUInt32 second) {
    sleep(second);
}
