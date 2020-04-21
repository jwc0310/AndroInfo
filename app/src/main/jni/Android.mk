LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_LDLIBS    := -llog

LOCAL_MODULE    := MyLibrary
LOCAL_SRC_FILES := hello.cpp
LOCAL_SRC_FILES += read_elf_head.c
LOCAL_SRC_FILES += emulator_check.c     \
                    mmap.cpp        \
                    hook\hook.c
LOCAL_SRC_FILES += utils.c

include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)

LOCAL_LDLIBS    := -llog

LOCAL_MODULE    := hello
LOCAL_SRC_FILES := hello1.cpp

include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)

LOCAL_LDLIBS    := -llog

LOCAL_CFLAGS += -pie -fPIE
LOCAL_LDFLAGS += -pie -fPIE

LOCAL_MODULE    := inject
LOCAL_SRC_FILES := inject.c

include $(BUILD_EXECUTABLE)