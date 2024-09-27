#include "type.h"
#include "../buffer/buffer.h"
#include "util.h"
#include <list>
#include <mutex>
#include <thread>
#include <memory>

typedef DVoid *(*DMsgFunc)(DUInt32, DVoid *, DVoid *);

typedef struct tagDMsg {
    DUInt32 msg;
    DVoid *para1;
    DVoid *para2;
} DMsg;

class DMsgQueue {
public:
    static DHandle Create(DCStr queueName, DUInt32 maxSize = 500);

    static DHandle GetQueue(DCStr queueName);

    static DVoid RemoveQueue(DHandle qid);

    static DUInt32 PostQueueMsg(DHandle qid, DUInt32 msg, DVoid *para1, DVoid *para2);

    static DVoid PostQuitMsg(DHandle qid);

    static DVoid Quit(DHandle qid);

    static DVoid *GetThreadHandle(DHandle qid);

    static DBool IsInQueue(DHandle qid);

    static DUInt32 GetQueueSize(DHandle qid);

    static DUInt32 GetQueueMaxSize(DHandle qid);

    static DHandle GetCurQueueID();

    static DCStr GetCurQueueName();

    static DUInt32 GetCoreCount();

    // Msg Handler
    static DVoid AddHandler(DHandle qid, DMsgFunc handler);

    static DVoid RemoveHandler(DHandle qid, DMsgFunc handler);

    static DUInt32 GetHandlerSize(DHandle qid);

    static DMsgFunc GetHandler(DHandle qid, DUInt32 nIndex);

    static DVoid RemoveAllHandler(DHandle qid);

    // Msg Cleaner
    static DVoid SetCleaner(DHandle qid, DMsgFunc handler);

    static DVoid ClearAllMsg(DHandle qid);

public:
    DMsgQueue(DCStr queueName, DUInt32 maxSize);

    ~DMsgQueue() = default;

    std::list<DMsg> m_queue;
    std::mutex m_queueMutex;

    std::list<DMsgFunc> m_msgfunc;
    std::mutex m_msgfuncMutex;
    DMsgFunc m_msgcleaner;

    std::shared_ptr<std::thread> m_t;
    std::string m_name;
    DEvent m_wait;
    const DUInt32 m_maxSize;

public:
    // 禁止赋值或者用于构造，防止其它人持有
D_DISALLOW_COPY_AND_ASSIGN(DMsgQueue)
};
