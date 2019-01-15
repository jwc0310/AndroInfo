//
// 解析so文件，其实主要目的就是获得ELF文件中以下三个表的信息：
//   字符串表：包含以空字符结尾的字符序列，使用这些字符来描绘符号和节名；
//   符号表：保存了一个程序在定位和重定位时需要的定义和引用的信息；
//   重定位表：保存了需要重定位的符号的信息；
//
#include <stdio.h>
#include <stdlib.h>
#include <elf.h>
#include <fcntl.h>
#include <android/log.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <utime.h>
#include <dirent.h>

#ifdef __x86_64
    #define Elf_Eh Elf64_Ehdr
    #define Elf_Shdr Elf64_Shdr
    #define Elf_Sym Elf64_Sym
    #define Elf_Rel Elf64_Rela
    #define ELF_R_SYM ELF64_R_SYM

#else
    #define Elf_Ehdr Elf32_Ehdr
    #define Elf_Shdr Elf32_Shdr
    #define Elf_Sym Elf32_Sym
    #define Elf_Rel Elf32_Rel
    #define ELF_R_SYM ELF32_R_SYM
#endif

#define module_path "/system/lib/libjavacore.so"
#define LOG_TAG    "native_hook_log"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)