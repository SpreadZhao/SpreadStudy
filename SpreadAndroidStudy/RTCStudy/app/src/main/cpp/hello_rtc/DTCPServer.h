//
// Created by SpreadZhao on 2024/3/13.
//

#ifndef RTCSTUDY_DTCPSERVER_H
#define RTCSTUDY_DTCPSERVER_H

#include "DTCPSocket.h"
#include "../common/util.h"
#include <string>
#include <vector>
#include <mutex>
#include <thread>
#include <utility>

class DTCPServerSink {
public:
    virtual DVoid OnListening(DSocket socket, DUInt16 wPort) {};

    virtual DVoid OnListeningOK(DSocket socket, DUInt16 wPort) {};

    virtual DVoid OnListenError(DSocket socket, DUInt32 code, std::string strReason) {};

    virtual DVoid OnNewConn(DSocket socket, DSocket newSocket) {};

    virtual DVoid OnError(DSocket socket, DUInt32 code, std::string strReason) {};

    virtual DVoid OnStop(DSocket socket) {};

    DTCPServerSink() { m_bIsAlive = true; }

    virtual ~DTCPServerSink() { m_bIsAlive = false; }

    inline bool IsAlive() { return m_bIsAlive; }

private:
    DAtomBool m_bIsAlive;
};

#define DTCPSERVER_STATE_STOPED 0           // 初始状态
#define DTCPSERVER_STATE_STARTING 1         // after call server.Start(DUInt16, DUInt16)
#define DTCPSERVER_STATE_RUNNING 2          // 成功监听之后

/**
 * 一个这样的结构体，表示一个这样的TCP链接，由服务端维护。
 * 结构体内部就是客户端的链接状态。
 */
typedef struct tagDClientData {
    DSocket m_sock;
    DUInt32 m_id;
    std::string m_name;
    DBool m_bQuit;
} DClientData;


class DTCPServer : public DTCPSocket {
public:
    DTCPServer();

    ~DTCPServer();

public:
    DBool Start(DUInt16 wPort, DUInt16 backlog = 0);

    DBool Stop();

    DUInt16 m_wPort;
    DInt32 m_backlog;

    inline DUInt32 GetState() { return m_nObjState; }

    // 对应上面DTCPSERVER_STATE_XXX
    DAtomInt32 m_nObjState;

    std::string GetServerInfo();

    DSocket FindSockByID(DUInt32 id);

    DUInt32 FindIDBySock(DSocket sk);

    DBool SetIDName(DUInt32 id, std::string name);

    DVoid NotifyNameChange(DSocket fromSock, std::string newName);

    DVoid SendGroupMsg(DSocket fromSock, std::string text);

    DVoid SendOneMsg(DSocket toSock, DUInt32 fromID, std::string text);

public:
    DVoid SetSink(DTCPServerSink *pSink);

    DTCPServerSink *m_pRecvSink;
    DTCPServerSink *m_pSendSink;
    DRWLock m_SinkLock;

public:
    DVoid AsyncSend(DSocket sock, DBuffer buf);

    DUInt32 m_sendQueue;

public:
    DUInt32 GetClientCount();

    DClientData GetClient(DUInt32 index);

    DVoid RemoveClient(DSocket client);

    std::vector<DClientData> m_vecClients;      // 上面客户端链接状态的列表
    std::mutex m_clientsMutex;                  // m_vecClients的锁
    DUInt32 m_gCounter;

protected:
    DVoid ServerLoop();

    std::shared_ptr<std::thread> m_serverthread;
    DSPinLock m_waitStart;
    DSPinLock m_waitFinish;

    DVoid Process(DBuffer buf, DSocket client);

private:
    DVoid SendOneCNameMsg(DSocket toSock, DUInt32 userID, std::string name);

    DVoid SendOneLeaveMsg(DUInt32 userID);
};


#endif //RTCSTUDY_DTCPSERVER_H
