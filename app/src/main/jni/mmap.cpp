#include <jni.h>
#include <fcntl.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/ioctl.h>
#include "android_log.h"

#define SHARE_SIZE (2 * 64 * 1024)
//共享内存
//驱动中设置共享内存节点
//userspace  设置共享
void test_mmap(void) {
    int fd;
    char *p_map;
    int i, len;

    //打开设备
    fd = open("/dev/binder", O_RDWR);   //读写  也可设置成只读
    if (fd < 0) {
        LOGE("open failed \n");
        return;
    }

    //内存映射                            //同理  也可以设置成只读
    p_map = (char *)mmap(0, SHARE_SIZE, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (p_map == MAP_FAILED) {
        LOGE("mmap failed\n");
        munmap(p_map, SHARE_SIZE);
        close(fd);
        return;
    }
    len = strlen(p_map);
    LOGE("share mem size : %d\n", len);
    char *p = strstr(p_map, "<WY>");
    if (p)
        LOGE("share mem : %s\n", p);

    munmap(p_map, SHARE_SIZE);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_test_tools_jni_Native_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    char *hello = "Hello from C++";
    test_mmap();
    return env->NewStringUTF(hello);
}
