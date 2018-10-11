LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_LDLIBS    := -llog

LOCAL_MODULE    := MyLibrary
LOCAL_SRC_FILES := hello.cpp

include $(BUILD_SHARED_LIBRARY)