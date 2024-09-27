#include "DMsgQueue.h"
#include <map>

std::mutex g_id2qMutex;
std::map<DHandle, DMsgQueue *> g_id2q;
DHandle g_qid = 1;
#define DM_QUITMSG 1000

DVoid *DThreadForQueue(DVoid *pvParam) {
    g_id2qMutex.lock();
    auto pNodeQueue = g_id2q.find((DHandle) pvParam);
    if (pNodeQueue == g_id2q.end()) {
        g_id2qMutex.unlock();
        return 0;
    }
    DMsgQueue *pq = (DMsgQueue *) pNodeQueue->second;
    g_id2qMutex.unlock();

    DBool bQuit = false;
    while (true) {
        if (pq->m_queue.size() == 0) {
            pq->m_wait.Reset();
            DEvent::WaitEvent(pq->m_wait, 5000);
        }

        pq->m_queueMutex.lock();
        DMsg msgNode = pq->m_queue.front();
        pq->m_queue.pop_front();
        pq->m_queueMutex.unlock();

        pq->m_msgfuncMutex.lock();
        auto pFunc = pq->m_msgfunc.begin();
        while (pFunc != pq->m_msgfunc.end()) {
            if (msgNode.msg == DM_QUITMSG) {
                bQuit = true;
                break;
            }

            DMsgFunc func = *pFunc;
            func(msgNode.msg, msgNode.para1, msgNode.para2);

            pFunc++;
        }
        pq->m_msgfuncMutex.unlock();

        if (bQuit) {
            break;
        }
    }

    return 0;
}

DMsgQueue::DMsgQueue(DCStr queueName, DUInt32 maxSize) : m_maxSize(maxSize) {
    m_name = queueName;
}

DHandle DMsgQueue::Create(DCStr queueName, DUInt32 maxSize) {
    DMsgQueue *pq = new DMsgQueue(queueName, maxSize);
    pq->m_queue.clear();
    pq->m_msgfunc.clear();
    pq->m_wait.Create(NULL);
    pq->m_wait.Reset();
    pq->m_t.reset(new std::thread(DThreadForQueue, (DVoid *) (g_qid)));
    g_id2qMutex.lock();
    g_id2q.insert(std::pair<DHandle, DMsgQueue *>(g_qid, pq));
    g_id2qMutex.unlock();
    return g_qid++;
}

DHandle DMsgQueue::GetQueue(DCStr queueName) {
    return 0;
}




