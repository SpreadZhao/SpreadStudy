//
// Created by SpreadZhao on 2024/3/13.
//

#ifndef RTCSTUDY_UTIL_H
#define RTCSTUDY_UTIL_H

#include "type.h"
#include "DAtomic.h"

class DRWLock {
public:
    DRWLock();

    ~DRWLock();

    DVoid LockWrite();

    DVoid UnlockWrite();

    DVoid LockRead();

    DVoid UnlockRead();

public:
    pthread_rwlock_t m_lock;
};

class DSPinLock {
public:
    DSPinLock();

    DVoid Reset();

    DVoid Signal();

    DUInt32 Wait(DUInt32 need_ms);

private:
    DUInt32 m_start;
    DUInt32 m_now;
    DAtomInt32 m_flag;
};

class DEvent {
public:
    DEvent();

    ~DEvent();

    DVoid Create(DCStr wName, DBool bAuto = false);

    DVoid Close();

    DBool Set();

    DBool Reset();

    DBool Pulse();

    operator DVoid *() { return handle; }

    static DVoid WaitEvent(DEvent &ev, DUInt32 timeinms);

public:
    DVoid *handle;
};


#endif //RTCSTUDY_UTIL_H
