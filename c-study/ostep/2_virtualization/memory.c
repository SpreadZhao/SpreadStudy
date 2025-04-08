#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main() {
    int *x = malloc(10 * sizeof(int));
    printf("size of int: %d\n", sizeof(int));
    printf("%d\n", sizeof(x));
    int a[10];
    printf("size of a: %d\n", sizeof(a));
    char str1[5] = "hello";
    char str2[] = "hello";
    printf("str1: sizeof is %d, strlen is %d, strnlen is %d\n", sizeof(str1), strlen(str1), strnlen(str1, 5));
    printf("str2: sizeof is %d, strlen is %d, strnlen is %d\n", sizeof(str2), strlen(str2), strnlen(str2, 5));
    char *str3 = "spreadzhao";
    char *str4 = strdup(str3);
    printf("str3: %s\nstr4: %s\n", str3, str4);
    free((void *) 1);
}