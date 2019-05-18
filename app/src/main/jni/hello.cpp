//
// Created by Administrator on 2018/7/25.
//

#include "com_andy_androinfo_jni_TestJni.h"

#include <android/log.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <math.h>
#include <dlfcn.h>

#define LOG_TAG "Andy_jni"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LIB_PATH "/system/lib/libc.so"

typedef unsigned long int (*CALL_FUNC) (unsigned long int);

extern "C" uint32_t read_elf_header(const char *, const char *);

//test getauxal with value 16/26 hwcap/hwcap2
JNIEXPORT jstring JNICALL Java_com_andy_androinfo_jni_TestJni_getHello__
   (JNIEnv *env, jclass) {
     LOGI("getString");

     void *handle;
     char *error;

     CALL_FUNC call_func = NULL;

     //打开动态链接库
     handle = dlopen(LIB_PATH, RTLD_LAZY);
     if (!handle) {
        LOGI("dlopen error");
        return env->NewStringUTF("dlopen error");
     }
     //清除之前的错误
     dlerror();

     //获取一个函数
     *(void **) (&call_func) = dlsym(handle, "getauxval");
     if ((error = dlerror()) != NULL) {
        LOGI("dlsym error");
        return env->NewStringUTF("dlsym error");
     }

     //小米手机   3649750/0x37b0d6  31/0x1f     1101 1110 1100 0011 010 110
     //模拟器     12503/0x30d7      0/0x0                 1100 0011 010 111
     //小米平板   471286/0x730f6    0/0x0          1 1100  1100 0011 110 110
     LOGI("call %lu", (*call_func)(16));
     LOGI("call 0x%lx", (*call_func)(16));

     LOGI("call %lu", (*call_func)(26));
     LOGI("call 0x%lx", (*call_func)(26));

     //关闭动态链接库
     dlclose(handle);

     //read_elf_header("", "");
     return env->NewStringUTF("This is d");
 }

 JNIEXPORT jstring JNICALL Java_com_andy_androinfo_jni_TestJni_getHello__Ljava_lang_String_2
   (JNIEnv *env, jclass, jstring) {
     LOGI("getString2");
     return env->NewStringUTF("This is myLibrary2");
 }

 static void handler_sigtrap(int signo) {
    exit(-1);
 }

 static void handler_sigbus(int signo) {
    exit(-1);
 }

 int setupSigTrap() {
    // BKPT throws SIGTRAP on nexus 5 / oneplus one (and most devices)
    signal(SIGTRAP, handler_sigtrap);
    return 0;
 }
/*
 int tryBKPT() {
    // BKPT throws SIGBUS on nexus 4 signal(SIGBUS, handler_sigbus);
    // 只能编译arm库
    __asm__ __volatile__ ("bkpt 255");
 }
 */

 JNIEXPORT jint JNICALL Java_com_andy_androinfo_jni_TestJni_qemuBkpt
   (JNIEnv *env, jclass) {
    LOGI("qemuBkpt");

    pid_t child = fork();
    int child_status, status = 0;
    if(child == 0) {
        setupSigTrap();
        //tryBKPT();
    } else if(child == -1) {
        status = -1;
    } else {
        int timeout = 0;
        int i = 0;
        while (waitpid(child, &child_status, WNOHANG) == 0 ) {
            sleep(1);
            // Time could be adjusted here, though in my experience if the child has not returned instantly
            // then something has gone wrong and it is an emulated device   
            if(i++ == 1) {
                timeout = 1;
                break;
            }
        }

        if(timeout == 1) {
            // Process timed out - likely an emulated device and child is frozen
            status = 1;
        }
        LOGI("bkpt child status %d", child_status);

        if (WIFEXITED(child_status)) {
            // 子进程正常退出
            status = 0;
        } else {
            // Didn't exit properly - very likely an emulator
            status = 2;
        }
        // Ensure child is dead
        kill(child, SIGKILL);
    }

    LOGI("bkpt status %d", status);

    return status;
}

void polling_thread(void);
void* atomicallyIncreasingGlobalVarThread(void *);
void printHistogram();
double calculatEntropyValue();
uint32_t * histogram;
#ifdef __amd64__
	uint64_t global_value = 0;
#else
	uint32_t global_value = 0;
#endif

const int numberOfIncIns = 50;
int numberOfSamples = 0x100;//Make sure we dont overflow any entry of our histogram by using UINT_MAX
#define DEBUG 0

void polling_thread(void){
	int i = 0;
	for(i =0; i < numberOfSamples; i++){
		//LOGI("value: 0x%x\n",global_value);
		usleep(1);
		histogram[global_value]++;
	}
	if(DEBUG){
		printHistogram();
		LOGI("Entropy measurement: %f",calculatEntropyValue());
	}
}

double calculatEntropyValue(){
	double sum = 0.0, ent = 0.0;
	uint i = 0;
	for (i = 0; i < numberOfIncIns; i++)
	  sum += (double)histogram[i];

	for (i = 0; i < numberOfIncIns; i++){
		double pi = (double)histogram[i] / sum;
		if(pi == 0)
			continue;
	  	ent += pi* log(pi);
	}
	return -ent/log(sum);
}

void printHistogram(){
	int i;
	for(i = 0; i < numberOfIncIns; i++)
		LOGI("%d : %d", i, histogram[i]);
}

void initializeHistogram(){
	//Assume that we have ~numberOfIncIns asm increment instructions
	//so we know that we will have an index into histogram greater than numberOfIncIns
	#ifdef __amd64__
		histogram = (uint32_t *)malloc(sizeof(uint64_t) * (numberOfIncIns));
	#else
		histogram = (uint32_t *)malloc(sizeof(uint32_t) * (numberOfIncIns));
	#endif
	int i;
	for(i =0; i < numberOfIncIns; i++)
		histogram[i] = 0;
}

JNIEXPORT jdouble JNICALL Java_com_andy_androinfo_jni_TestJni_qemuFingerPrint
  (JNIEnv *env, jclass) {
/*
  	initializeHistogram();

  	pthread_t threadData;

  	if(pthread_create(&threadData, NULL, atomicallyIncreasingGlobalVarThread, NULL))
  		perror("pthread_create()");

  	polling_thread();
  	double entValue = calculatEntropyValue();
  	//pthread_kill(threadData, SIGSTOP);
  	free(histogram);

    return entValue;
    */
    return 0.0f;

  }