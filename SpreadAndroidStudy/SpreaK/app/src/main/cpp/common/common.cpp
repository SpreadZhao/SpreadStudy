#include <sys/time.h>
#include <sys/stat.h>
#include <dirent.h>
#include <cstring>
#include <cstdlib>
#include <errno.h>
#include <zlib.h>
#include "common.h"

#define NANO_TO_MILLI(ns) (ns * 1000000)

std::string sRepoDirPath;
std::string sInternalRepoDirPath;
std::string sFilesDirPath;

uint64_t GetCurrentMicroSeconds() {
    struct timeval current;
    int ret;
    ret = gettimeofday(&current, nullptr);
    return static_cast<uint64_t>(ret == 0 ? NANO_TO_MILLI(current.tv_sec) + current.tv_usec : 0);
}

void RandomSleep() {
    srand(static_cast<unsigned int>(GetCurrentMicroSeconds()));
    int r = rand();
    int sl = 2;
    if (r < (RAND_MAX >> 2)) {
        sleep(sl);
    }
}

std::string EnsureCreateDir(const std::string &parent_dir_path, const std::string &dir_name) {
    // "/home/spread/" + "dir" + "/" ==> "/home/spread/dir/"
    std::string full_dir_path = parent_dir_path + dir_name + skFileSeparator;
    const char* path = full_dir_path.c_str();
    struct stat dir_stat;
    // if file existed already.
    if (stat(path, &dir_stat) == 0 && S_ISDIR(dir_stat.st_mode)) {
        return full_dir_path;
    }
    // if file no exist, create it. S_IRWXU makes it readable, writable, executable.
    // errno ensure the result is for the calling thread, so you don't need
    // caring about threads competition.
    if (mkdir(path, S_IRWXU) == 0 || errno == EEXIST) {
        return full_dir_path;
    }

    LOGE("fail to create dir %s, err: %s", path, strerror(errno));
    return skEmptyString;
}

/**
 *
 */
bool EnsureDeleteDir(const std::string &parent_dir_path, const std::string &dir_name) {
    const std::string full_dir_path = parent_dir_path + dir_name + skFileSeparator;
    if (!IsPathExist(full_dir_path)) {
        return true;
    }
    DIR *dir = opendir(full_dir_path.c_str());
    if (dir == nullptr) {
        return false;
    }
    bool result = true;
    struct dirent *p;
    while (result && (p = readdir(dir))) {
        if (strcmp(p->d_name, ".") == 0 || strcmp(p->d_name, "..") == 0) {
            continue;
        }
        struct stat file_stat;
        std::string file_path = full_dir_path + p->d_name;
        if (stat(file_path.c_str(), &file_stat) == 0) {
            if (S_ISDIR(file_stat.st_mode)) {
                result = (result && EnsureDeleteDir(full_dir_path, p->d_name));
            } else {
                result = (result && EnsureDeleteFile(file_path));
            }
        }
    }
    result = result && (closedir(dir) == 0);
    result = result && (EnsureDeleteEmptyDir(full_dir_path));
    return result;
}

bool EnsureDeleteEmptyDir(const std::string &dir_path) {
    if (!IsPathExist(dir_path)) {
        return true;
    }
    bool result = rmdir(dir_path.c_str()) == 0;
    if (!result) {
        LOGE("failed to rm empty dir %s, err: %s", dir_path.c_str(), strerror(errno));
    }
    return result;
}

bool EnsureDeleteFile(const std::string &file_path) {
    if (!IsPathExist(file_path)) {
        return true;
    }
    bool result = unlink(file_path.c_str()) == 0;
    if (!result) {
        LOGE("failed to rm file %s, err: %s", file_path.c_str(), strerror(errno));
    }
    return result;
}

void RenameFile(const std::string &src_path, const std::string &dest_path) {
    if (rename(src_path.c_str(), dest_path.c_str()) == -1) {
        LOGE("fail to rename %s to %s, err: %s", src_path.c_str(), dest_path.c_str(), strerror(errno));
    }
}

bool IsPathExist(const std::string &file_path) {
    return access(file_path.c_str(), F_OK) == 0;
}