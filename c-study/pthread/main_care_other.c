#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

static void *func(void*) {
    printf("th is working\n");
    while (1) {

    }
}

int main() {
    printf("main working!\n");
    pthread_t th;
    pthread_create(&th, NULL, func, NULL);
    printf("main is going to die\n");
    pthread_join(th, NULL);         // Java中相当于自动加上了这句话。main线程要等待所有非守护线程结束之后才能结束。
    exit(0);
}