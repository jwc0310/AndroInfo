//
// Created by Administrator on 2020/1/16.
//

#include <stdio.h>
#include <stdlib.h>

int number() {
    int abc = rand();
    if (abc % 2 == 0) {
        return 0;
    } else {
        return 1;
    }
}

int main(void) {
    int a = number();
    if (0) {
        printf("acb");
    } else {
        printf("abc");
    }

    return 0;
}
