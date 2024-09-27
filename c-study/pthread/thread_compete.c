#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define THRNUM      20000

static int tick_tok = 1;

static void* plusOne()
{
    int oldval = tick_tok;
    sleep(1);
    tick_tok = oldval + 1;
    pthread_exit(NULL);
}

int main()
{
    pthread_t threads[THRNUM];
    for (int i = 0; i < THRNUM; i++)
    {
        pthread_create(threads + i, NULL, plusOne, NULL);
    }

    for (int i = 0; i < THRNUM; i++)
    {
        pthread_join(threads[i], NULL);
    }
    printf("ticktok: %d\n", tick_tok);
    exit(0);
}