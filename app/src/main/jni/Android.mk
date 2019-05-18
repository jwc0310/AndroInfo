LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_LDLIBS    := -llog

LOCAL_MODULE    := MyLibrary
LOCAL_SRC_FILES := hello.cpp
LOCAL_SRC_FILES += read_elf_head.c
LOCAL_SRC_FILES += emulator_check.c     \
                    mmap.cpp        \
LOCAL_SRC_FILES += utils.c

include $(BUILD_SHARED_LIBRARY)