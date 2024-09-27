#include <iostream>
#include <stdlib.h>
using namespace std;

typedef unsigned char DByte;

struct Data {
    std::string name;
    int size;
    DByte *data() {
        return (DByte *)(this + 1);
    }
};

int main() {
    Data *pData = (Data *)malloc(sizeof(Data) + 10);
    pData->name = "Spread's Buffer";
    pData->size = 10;
    memcpy(pData->data(), "haha", sizeof("haha"));
    std::cout << "name: " << pData->name << "[" << &(pData->name) << "]" << std::endl;
    std::cout << "size: " << pData->size << "[" << (void*)&(pData->size) << "]" << std::endl;
    std::cout << "data: " << pData->data() << "[" << (void*)pData->data() << "]" <<  std::endl;
    return 0;
}