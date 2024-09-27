//
// Created by SpreadZhao on 2024/3/12.
//

#ifndef RTCSTUDY_DATOMIC_H
#define RTCSTUDY_DATOMIC_H

#ifndef D_USE_CPP11_ATOMIC
#ifndef D_USE_PLATFORM_ATOMIC
#define D_USE_CPP11_ATOMIC 1
#endif
#endif

#if defined(D_USE_CPP11_ATOMIC)

#include <atomic>

typedef std::atomic_bool DAtomBool;
typedef std::atomic_char DAtomChar;
typedef std::atomic_schar DAtomInt8;
typedef std::atomic_uchar DAtomUInt8;
typedef std::atomic_short DAtomInt16;
typedef std::atomic_ushort DAtomUInt16;
typedef std::atomic_int DAtomInt32;
typedef std::atomic_uint DAtomUInt32;
typedef std::atomic_llong DAtomInt64;
typedef std::atomic_ullong DAtomUInt64;
typedef std::atomic_intptr_t DAtomIntPtr;
typedef std::atomic_uintptr_t DAtomUIntPtr;
typedef std::atomic_size_t DAtomSizeT;

#define DAtomDec32(addr) (*addr)--

#endif

#endif //RTCSTUDY_DATOMIC_H
