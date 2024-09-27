/**
 * 由于Android基于Linux，不需要特殊的类型定义。
 */

typedef unsigned char DByte;
typedef char DChar;
typedef char *DStr;
typedef const char *DCStr;

typedef unsigned short DWChar;
typedef unsigned short *DWStr;
typedef const unsigned short *DCWStr;

typedef unsigned int DUChar;

typedef char DInt8;
typedef unsigned char DUInt8;
typedef short DInt16;
typedef unsigned short DUInt16;
typedef int DInt32;
typedef unsigned int DUInt32;
typedef long long DInt64;
typedef unsigned long long DUInt64;
typedef DUInt64 DPtrDiff;

typedef float DFloat;
typedef double DDouble;
typedef long double DLDouble;
typedef bool DBool;
typedef void DVoid;
typedef void *DVoidPtr;

typedef DUInt64 DSizeT;
typedef DUInt64 DHandle;
typedef DUInt64 DULong;

typedef int DSocket;

#define DIntSizeOf(x) (unsigned int)sizeof(x)

#define D_DISALLOW_COPY_AND_ASSIGN(TypeName) \
private: \
    TypeName(const TypeName&) = delete;      \
    TypeName& operator=(const TypeName&) = delete;