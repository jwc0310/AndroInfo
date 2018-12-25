//
// Created by Administrator on 2018/7/25.
//

#include "com_andy_androinfo_jni_TestJni.h"

#include <android/log.h>

#define LOG_TAG "Andy_jni"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

JNIEXPORT jstring JNICALL Java_com_andy_androinfo_jni_TestJni_getHello__
   (JNIEnv *env, jclass) {
     LOGI("getString");
     return env->NewStringUTF("This is myLibrary");
 }

 JNIEXPORT jstring JNICALL Java_com_andy_androinfo_jni_TestJni_getHello__Ljava_lang_String_2
   (JNIEnv *env, jclass, jstring) {
     LOGI("getString2");
     return env->NewStringUTF("This is myLibrary2");
 }