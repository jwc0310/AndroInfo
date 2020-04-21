LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_LDLIBS    := -L$(SYSROOT)/usr/lib -llog
LOCAL_MODULE    := property
LOCAL_SRC_FILES := property/com_andy_detect_jni_Property.cpp
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_LDLIBS    := -L$(SYSROOT)/usr/lib -llog
LOCAL_MODULE    := detect
LOCAL_SRC_FILES := emulator/com_andy_detect_jni_EmulatorDetectUtil.cpp
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_LDLIBS    := -L$(SYSROOT)/usr/lib -llog
LOCAL_MODULE    := test
LOCAL_SRC_FILES := test.c
include $(BUILD_SHARED_LIBRARY)