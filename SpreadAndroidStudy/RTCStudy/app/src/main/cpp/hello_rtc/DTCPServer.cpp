#include "DTCPServer.h"
#include "../common/DMsgQueue.h"

#define SERVER_SEND_MSG 10000
#define SERVER_SENDALL_MSG 10001

#define HELLO_SC_CMD_CNAME 101
#define HELLO_SC_CMD_LEAVE 102
#define HELLO_SC_CMD_PMSG 103
#define HELLO_SC_CMD_GMSG 104

DVoid *SendHandler(DUInt32 msg, DVoid *para1, DVoid *para2);

DVoid *SendCleaner(DUInt32 msg, DVoid *para1, DVoid *para2);

DTCPServer::DTCPServer() {
    m_nObjState = DTCPSERVER_STATE_STOPED;
    m_wPort = 0;
    m_backlog = 0;
    m_pRecvSink = nullptr;
    m_pSendSink = nullptr;
    m_vecClients.clear();
    m_sendQueue = 0;
}

DTCPServer::~DTCPServer() {
    Stop();
    m_nObjState = DTCPSERVER_STATE_STOPED;
    m_wPort = 0;
    m_backlog = 0;
    m_pRecvSink = nullptr;
    m_pSendSink = nullptr;
    m_vecClients.clear();
    m_sendQueue = 0;
}

DBool DTCPServer::Start(DUInt16 wPort, DUInt16 backlog) {
    if (m_nObjState != DTCPSERVER_STATE_STOPED) {
        return false;
    }

    m_wPort = wPort;
    m_backlog = backlog;

    m_clientsMutex.lock();
    m_vecClients.clear();
    m_clientsMutex.unlock();

    // 创建 Server 监听线程
    m_waitStart.Reset();
    m_serverthread.reset(new std::thread(&DTCPServer::ServerLoop, this));
    m_waitStart.Wait(300);
    // 创建发送队列
    m_sendQueue = DMsgQueue::Create("ServerSend");
    DMsgQueue::AddHandler(m_sendQueue, SendHandler);
    DMsgQueue::SetCleaner(m_sendQueue, SendCleaner);
    m_nObjState = DTCPSERVER_STATE_STARTING;
    return true;
}

DVoid *SendHandler(DUInt32 msg, DVoid *para1, DVoid *para2) {
    if (msg == SERVER_SEND_MSG) {
        DBuffer buf;
        buf.Attach((DByte *) para1);
        DSocket sock = (DSocket) para2;
        DTCPSocket ts(sock);
        ts.SyncSend(buf);
    }
    return nullptr;
}

DVoid *SendCleaner(DUInt32 msg, DVoid *para1, DVoid *para2) {
    if (msg == SERVER_SEND_MSG) {
        DBuffer buf;
        buf.Attach((DByte *) para1);
    }
    return nullptr;
}