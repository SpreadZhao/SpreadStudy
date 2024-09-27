//
// Created by SpreadZhao on 2024/3/13.
//

#ifndef RTCSTUDY_DXP_H
#define RTCSTUDY_DXP_H

#include "type.h"
#include "common.h"
#include "platform.h"

// XP: Cross Platform
class DXP {
private:
    static DCStr GetOSName();

public:
    static DCStr HelloStr();

    static DCStr HelloStrNew();

    //in MILI second since system started, up to 49.7 days on Win32
    static DUInt32 GetTickCount32();

    static DUInt64 GetTickCount64(); //in MILI second since system started
    static DVoid SleepSec(DUInt32 second);
};


#endif //RTCSTUDY_DXP_H
