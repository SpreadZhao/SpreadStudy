#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

static void *foo(void *p)
{
    puts("Thread is working!");
    pthread_exit(NULL);
}

int main()
{
    pthread_t tid;

    puts("begin!");

    int err = pthread_create(&tid, NULL, foo, NULL);
    if (err) {
        fprintf(stderr, "pthread_create error: %d", err);
    }

    pthread_join(tid, NULL);        // 等待线程运行结束后收尸
    puts("end!");                   // 然后才能打印end

    exit(0);
}