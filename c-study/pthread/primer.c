#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

#define LEFT    30000000
#define RIGHT   30000200
#define THRNUM  (RIGHT-LEFT+1)

static void *checkIsPrimer(void *);

struct th_arg_num {
    int num;
};


int main() {
    int i, j, mark;
    pthread_t threads[THRNUM];
    struct th_arg_num *arg;
    void *return_arg;               // 用来接收thread返回的内存

    for (i = LEFT; i <= RIGHT; i++) {
        arg = malloc(sizeof(*arg));
        arg->num = i;
        pthread_create(threads + i - LEFT, NULL, checkIsPrimer, arg);
    }
    for (i = LEFT; i <= RIGHT; i++) {
        pthread_join(threads[i - LEFT], &return_arg);
        free(return_arg);
    }
    
    exit(0);
}

static void *checkIsPrimer(void *arg) {
    int i, j, mark;
    i = ((struct th_arg_num*)arg)->num;
    mark = 1;
    for (j = 2; j < i / 2; j++) {
        if (i % j == 0) {
            mark = 0;
            break;
        }
    }
    if (mark) {
        printf("%d is a primer\n", i);
    }
    pthread_exit(arg);      // 将arg返回，到pthread_join中释放
}