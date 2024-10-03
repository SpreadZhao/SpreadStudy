#include <iostream>

void test() {
    int start = 1;
    int *end = &start + 1;
    // end - start = 4 bytes
    std::cout << "end: " << end << ", start: " << &start << std::endl;
}

int main() {
    test();
    return 0;
}
