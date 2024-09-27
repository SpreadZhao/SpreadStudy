//
// Created by SpreadZhao on 2024/3/13.
//

#ifndef RTCSTUDY_DTCPSOCKET_H
#define RTCSTUDY_DTCPSOCKET_H

#include "../common/common.h"
#include "../common/type.h"
#include "../buffer/buffer.h"

class DTCPSocket {
public:
    DTCPSocket();

    explicit DTCPSocket(DSocket socket);

    ~DTCPSocket();

    DTCPSocket(const DTCPSocket &socket);

    DTCPSocket &operator=(const DTCPSocket &socket);

public:
    DBool Create();

    DVoid Close();

    DVoid Attach(DSocket socket);

    DVoid Detach();

    DVoid Renew();

    DBool operator==(const DTCPSocket socket);

    DBool IsValid();

    std::string GetName();

public:
    DBool Bind(DUInt16 port);

    DBool Listen(DInt32 backlog);

    DTCPSocket Accept();

    DInt32 Shutdown(DInt32 how);

public:
    DBool SetNonBlock();

    DBool SetBlock();

    DUInt32 GetBufRead();

    DInt32 IsListen();

public:
    DBool SyncConnect(DCStr strIP, DUInt16 wPort);

    DBool SyncSend(DBuffer buf);

    DBuffer SyncRecv(DUInt32 size, DInt32 *res);

public:
    DSocket m_socket;
};

#endif //RTCSTUDY_DTCPSOCKET_H
