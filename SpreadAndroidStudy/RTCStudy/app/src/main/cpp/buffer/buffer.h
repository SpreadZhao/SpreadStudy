#include "../common/type.h"
#include "../common/DAtomic.h"

/**
 * 简单的buffer类：
 * pBuf: 二进制数据的起始地址
 * nSize: 二进制数据的字节数
 */
typedef struct tagBuffer {
    DByte *pBuf;
    DUInt32 nSize;
} Buffer;

struct DBufferData {
    DAtomInt32 nRefs;
    DInt32 nAllocLength;

    DByte *buf() {
        return (DByte *) (this + 1);
    }
};

class DBuffer {
public:
    DBuffer();

    explicit DBuffer(DUInt32 nSize);

    DBuffer(DVoid *pBuf, DUInt32 size);

    ~DBuffer();

    DVoid Init();

public:
    DBuffer(const DBuffer &buffer);

    DBuffer &operator=(const DBuffer &bufSrc);

public:
    DBool Reserve(DInt32 nLen);

    DBuffer GetSub(DInt32 start, DInt32 end);

    DVoid SetSub(DInt32 start, DByte *pBuf, DUInt32 len);

public:
    // Base16 = HEX
    DBool InitWithHexString(DCStr str);

    DBool InitWithHexString(std::string &str);

    std::string ToHexString(DUInt32 maxlen = 0);

    std::string ToHexList(DUInt32 width = 16);

    DBool HexToBuffer(DCStr hexStr);

    static DBool IsValidHexStr(DCStr hexStr, DUInt32 *reason = NULL);

    // Base64
    DBool InitWithBase64String(DCStr str);

    DBool InitWithBase64String(std::string &str);

    std::string ToBase64String();

    static DBool IsValidBase64Str(DCStr base64Str, DUInt32 *reason = NULL);

    static DUInt32 GetBase64BufSize(DCStr base64Str);

public:
    DUInt32 GetRefCount() const;

    DByte *GetBuf() const;

    DUInt32 GetSize() const;

    DVoid AddRef();

    DVoid Release();

    DVoid Attach(DByte *pBuf);

    DVoid Detach();

    DBool IsNull();

public:
    DVoid SetAt(DUInt32 index, DByte v);

    DByte GetAt(DUInt32 index);

    DVoid Zero();

    DVoid FillWithRandom();

    DVoid Xor(DByte mask);

public:
    DInt32 Compare(const DBuffer &buf) const;

    friend inline DBool operator==(const DBuffer &s1, const DBuffer &s2) {
        return (s1.Compare(s2) == 0);
    }

public:
    DByte *LockBuffer();

    DVoid UnlockBuffer();

public:
    static DVoid Release(DBufferData *pData);

    static const DBuffer &GetNullBuffer();

protected:
    DByte *m_pBuf;

    DBufferData *GetData() const;

protected:
    DBool AllocBuffer(DInt32 nLen);

    DVoid AssignCopy(DInt32 nSrcLen, DByte *lpszSrcData);

    DBool AllocBeforeWrite(DInt32 nLen);

    DVoid CopyBeforeWrite();
};
