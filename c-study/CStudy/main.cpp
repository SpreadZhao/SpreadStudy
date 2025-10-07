#include <iostream>

void test() {
    int start = 1;
    int *end = &start + 1;
    // end - start = 4 bytes
    std::cout << "end: " << end << ", start: " << &start << std::endl;
}

int main() {
    int a = 1;
    int *p1 = &a;
    int **p2 = &p1;
    int ***p3 = &p2;
    int ****p4 = &p3;
    std::printf("a: %d, p1: %p, p2: %p, p3: %p, p4: %p\n", a, p1, p2, p3, p4);
    return 0;
}
