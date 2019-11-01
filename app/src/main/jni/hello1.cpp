#include <jni.h>
#include <string.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <elf.h>
#include <fcntl.h>

#define LOG_TAG "DEBUG"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

void hook_entry(char * a){
    LOGD("Hook success, pid = %d\n", getpid());
    while(1) {
        LOGD("Hello *********** %s\n", a);
    }
}