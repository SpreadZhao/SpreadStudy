#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

#define LEFT    30000000
#define RIGHT   30000200
#define THRNUM  (RIGHT-LEFT+1)

static void *checkIsPrimer(void *);

int main() {
    int i, j, mark;
    pthread_t threads[THRNUM];
    void *arg_space;

    for (i = LEFT; i <= RIGHT; i++) {
        int *arg_num = malloc(sizeof(int));
        *arg_num = i;
        pthread_create(threads + i - LEFT, NULL, checkIsPrimer, arg_num);
    }
    for (i = LEFT; i <= RIGHT; i++) {
        pthread_join(threads[i - LEFT], &arg_space);
        free(arg_space);
    }
    
    exit(0);
}

static void *checkIsPrimer(void *arg) {
    int i, j, mark;
    i = *(int *)arg;
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