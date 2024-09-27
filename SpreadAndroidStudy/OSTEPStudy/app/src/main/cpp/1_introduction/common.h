//
// Created by SpreadZhao on 2024/3/24.
//

#ifndef OSTEPSTUDY_COMMON_H
#define OSTEPSTUDY_COMMON_H

#include <sys/time.h>
#include <sys/stat.h>
#include <assert.h>

double GetTime() {
    struct timeval t;
    int rc = gettimeofday(&t, NULL);
    assert(rc == 0);
    return (double) t.tv_sec + (double) t.tv_usec / 1e6;
}

void Spin(int howlong) {
    double t = GetTime();
    while (GetTime() - t < (double) howlong);
}

#endif //OSTEPSTUDY_COMMON_H
