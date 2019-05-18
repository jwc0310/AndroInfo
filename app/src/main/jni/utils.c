#include <string.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <android/log.h>
#include "emulator_check.h"
#include "com_andy_androinfo_jni_TestJni.h"
#define LOG_TAG "Andy_jni"

int getFileSize() {
    struct stat buf;
    //文件路径
    if (!stat("/sdcard/Download/abc.txt", &buf)) {
        return buf.st_size;
    }

    return 4 * 1024; //page
}