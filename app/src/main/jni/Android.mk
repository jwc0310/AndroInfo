LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)


include $(CLEAR_VARS)

LOCAL_LDLIBS    := -llog

LOCAL_CFLAGS += -pie -fPIE
LOCAL_LDFLAGS += -pie -fPIE

LOCAL_MODULE    := inject
LOCAL_SRC_FILES := inject.c

include $(BUILD_EXECUTABLE)