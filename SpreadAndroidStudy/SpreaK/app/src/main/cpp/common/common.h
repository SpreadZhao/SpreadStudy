//
// Created by spreadzhao on 3/20/24.
//

#ifndef SPREAK_COMMON_H
#define SPREAK_COMMON_H

#include <cstddef>
#include <errno.h>
#include <unistd.h>
#include <string>
#include "jni.h"
#include "log.h"

static constexpr const char* skSpreaKChunkHeaderTypeName = "spreak-chk";
static constexpr const char* skSpreaKKeyMapHeaderTypeName = "spreak-km";
static constexpr const char* skSpreaKChunkBitmapHeaderTypeName = "spreak-cbm";
static constexpr const char* skSpreaKBlockHeaderTypeName = "spreak-blk";
static constexpr const char* skSpreaKSingleValueHeaderTypeName = "spreak-sv";
static constexpr const char* skSpreaKMultiValueHeaderTypeName = "spreak-mv";
static constexpr const char* skSpreaKLocalExtraInfoHeaderTypeName = "spreak-lei";
static constexpr const char* skSpreaKMappingMarkBitmapHeaderTypeName = "spreak-mmb";
static constexpr const char* skSpreaKGlobalExtraInfoHeaderTypeName = "spreak-gei";


static constexpr size_t skMappingKeyMapDefaultSize = 4096;
static constexpr size_t skMappingKeyMapMaxSize = 40960000;
static constexpr size_t skMappingChunkDefaultSize = 4096;
static constexpr size_t skMappingChunkIncreasingSize = 4096;
static constexpr size_t skMappingChunkMaxSize = 40960000;

static constexpr size_t skMappingBlockDefaultSize = 8192;
static constexpr size_t skMappingBlockIncreasingSize = 4096;
static constexpr size_t skMappingBlockMaxSize = 81920000;

static constexpr size_t skProcessMutexFileDefaultSize = 128;

static constexpr uint32_t skInvalidIndex = static_cast<const uint32_t>(-1);
static constexpr uint32_t skNullIndex = 0xfffffffe;
static constexpr uint32_t skBigValue = 0xfffffffd;
static constexpr uint32_t skWrongType = 0xfffffffc;

static constexpr const char* skFileSeparator = "/";

static constexpr const char* skEmptyString = "";

extern bool is_open_key_map_optimized;

extern std::string sRepoDirPath;
extern std::string sInternalRepoDirPath;
extern std::string sFilesDirPath;

enum {
    TYPE_INVALID = 0,
    TYPE_BOOL = 1,
    TYPE_INT = 2,
    TYPE_LONG = 3,
    TYPE_FLOAT = 4,
    TYPE_DOUBLE = 5,
    TYPE_BASIC_TYPE_MAX = 5,
    TYPE_STRING = 6,
    TYPE_BYTES = 7
};

static const char* skTypeName[] = {
        "INVALID",
        "BOOL",
        "INT",
        "LONG",
        "FLOAT",
        "DOUBLE",
        "STRING",
        "BYTES"
};

enum {
    BLOCK_TYPE_BYTES = 0,
    BLOCK_TYPE_STRING_UTF8 = 1,
    BLOCK_TYPE_STRING_UTF16 = 2,
    BLOCK_TYPE_STRING_ARRAY = 3,
    BLOCK_TYPE_OBJECT = 4
};

#define EXT_TYPE_BYTES ((BLOCK_TYPE_BYTES << 4) | TYPE_BYTES)
#define EXT_TYPE_STRING_UTF8 ((BLOCK_TYPE_STRING_UTF8 << 4) | TYPE_BYTES)
#define EXT_TYPE_STRING_UTF16 ((BLOCK_TYPE_STRING_UTF16 << 4) | TYPE_BYTES)
#define EXT_TYPE_STRING_ARRAY ((BLOCK_TYPE_STRING_ARRAY << 4) | TYPE_BYTES)
#define EXT_TYPE_OBJECT ((BLOCK_TYPE_OBJECT) << 4 | TYPE_BYTES)

extern uint64_t GetCurrentMicroSeconds();
extern std::string EnsureCreateDir(const std::string &parent_dir_path, const std::string &dir_name);
extern bool EnsureDeleteDir(const std::string &parent_dir_path, const std::string &dir_name);
extern bool EnsureDeleteEmptyDir(const std::string &dir_path);
extern bool EnsureDeleteFile(const std::string &file_path);
extern bool IsPathExist(const std::string &file_path);
extern void RenameFile(const std::string &src_path, const std::string &dest_path);
extern void RandomSleep();
extern uint16_t CheckSum(const uint8_t *ptr, size_t size);


#endif //SPREAK_COMMON_H
