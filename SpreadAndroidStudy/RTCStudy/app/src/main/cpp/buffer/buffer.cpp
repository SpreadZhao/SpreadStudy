#include "buffer.h"
#include <assert.h>
#include <stdlib.h>

struct DBufferMemory {
    DBufferData data;
    DByte *buf;
};
// https://stackoverflow.com/questions/21708606/why-does-an-in-place-member-initialization-use-a-copy-constructor-in-c11
const DBufferMemory nNullBuffer = {{{-1}, 0}, nullptr};
const DBufferData *_nullBufferData = &nNullBuffer.data;
// 将非const指针赋值给const指针是不推荐的。所以这里做了一个加法
const DByte *_nullBuffer = (const DByte *) _nullBufferData + sizeof(DBufferData);
//const DByte *_nullBuffer = nNullBuffer.buf;

/**
 * 因为DBuffer内部唯一占内存的就是m_pBuf，是一个DByte指针，
 * 所以上面DBufferMemory保存的buf就可以用来存放这个buffer。
 * 将它转成DBuffer是没有任何问题的。
 */
const DBuffer &DBuffer::GetNullBuffer() {
    return *(DBuffer *) &_nullBuffer;
}

DVoid DBuffer::CopyBeforeWrite() {
    if (GetData()->nRefs > 1) {
        DBufferData *pData = GetData();
        Release();
        if (AllocBuffer(pData->nAllocLength)) {
            memcpy(m_pBuf, pData->buf(), pData->nAllocLength);
        }
    }
    assert(GetData()->nRefs <= 1);
}

DBufferData *DBuffer::GetData() const {
    assert(m_pBuf != nullptr);
    return ((DBufferData *) m_pBuf) - 1;
}

DVoid DBuffer::Release() {
    if (GetData() != _nullBufferData) {
        assert(GetData()->nRefs != 0);
        DAtomDec32(&GetData()->nRefs);
        if (GetData()->nRefs <= 0) {
            free((void *) GetData());
        }
        Init();
    }
}

DVoid DBuffer::Init() {
    m_pBuf = GetNullBuffer().m_pBuf;
}

DBool DBuffer::AllocBuffer(DInt32 nLen) {
    assert(nLen >= 0);
    if (GetData()->nRefs >= 1) {
        Release();
    }
    if (nLen == 0) {
        Init();
    } else {
        DBufferData *pData = (DBufferData *) malloc(sizeof(DBufferData) + nLen);
        if (pData == nullptr) {
            return false;
        }
        pData->nRefs = 1;
        pData->nAllocLength = nLen;
        m_pBuf = pData->buf();
    }
    return true;
}

DVoid DBuffer::Attach(DByte *pBuf) {
    assert(m_pBuf == _nullBuffer);
    m_pBuf = pBuf;
}
