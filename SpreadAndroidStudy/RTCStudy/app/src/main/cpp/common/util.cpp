//
// Created by SpreadZhao on 2024/3/13.
//

#include "util.h"
#include "DXP.h"

// Read Write Lock

DRWLock::DRWLock() {
    pthread_rwlock_init(&m_lock, nullptr);
}

DRWLock::~DRWLock() {
    pthread_rwlock_destroy(&m_lock);
}

DVoid DRWLock::LockWrite() {
    pthread_rwlock_wrlock(&m_lock);
}

DVoid DRWLock::UnlockWrite() {
    pthread_rwlock_unlock(&m_lock);
}

DVoid DRWLock::LockRead() {
    pthread_rwlock_rdlock(&m_lock);
}

DVoid DRWLock::UnlockRead() {
    pthread_rwlock_unlock(&m_lock);
}

DSPinLock::DSPinLock() {
    m_start = 0;
    m_flag = 0;
}

DVoid DSPinLock::Reset() {
    m_flag = 0;
}

DVoid DSPinLock::Signal() {
    m_flag = 1;
}

DUInt32 DSPinLock::Wait(DUInt32 need_ms) {
    m_start = DXP::GetTickCount32();
    while (m_flag != 1) {
        m_now = DXP::GetTickCount32();
        if (m_now - m_start < need_ms) {
            DXP::SleepSec(0);
        } else {
            break;
        }
    }
    DUInt32 diff = m_now - m_start;
    if (diff < need_ms) {
        return diff + 1;
    } else {
        return 0;
    }
}

typedef struct tagDEventData {
    pthread_mutex_t mutex;
    pthread_cond_t cond;
    DBool flag;
    DCStr name;
} DEventData;

DEvent::DEvent() {
    handle = NULL;
}

DEvent::~DEvent() {
    Close();
}

DVoid DEvent::Create(DCStr wName, DBool bAuto) {
    DEventData *event = (DEventData *) malloc(DIntSizeOf(DEventData));
    event->flag = false;
    pthread_mutex_init(&event->mutex, 0);
    pthread_cond_init(&event->cond, 0);
    event->name = wName;
    handle = event;     // handle指向DEventData结构体
}

DVoid DEvent::Close() {
    if (handle) {
        pthread_mutex_destroy(&((DEventData *) handle)->mutex);
        free(handle);
        handle = NULL;
    }
}

DBool DEvent::Set() {
    DEventData *event = (DEventData *) handle;
    pthread_mutex_lock(&event->mutex);
    event->flag = true;
    pthread_cond_signal(&event->cond);
    pthread_mutex_unlock(&event->mutex);
    return true;
}

DBool DEvent::Reset() {
    if (handle) {
        DEventData *event = (DEventData *) handle;
        pthread_mutex_lock(&event->mutex);
        event->flag = false;
        pthread_mutex_unlock(&event->mutex);
        return true;
    }
    return false;
}

DBool DEvent::Pulse() {
    Set();
    Reset();
    return true;
}

DVoid DEvent::WaitEvent(DEvent &ev, DUInt32 timeinms) {
    DEventData *event = (DEventData *) ev.handle;
    struct timespec t;
    t.tv_nsec = timeinms * 1000 * 1000;
    pthread_mutex_lock(&event->mutex);
    while (!event->flag) {
        pthread_cond_timedwait(&event->cond, &event->mutex, &t);
    }
    pthread_mutex_unlock(&event->mutex);
}



