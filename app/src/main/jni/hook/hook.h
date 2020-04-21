//
// Created by Administrator on 2019/11/1.
//

#ifndef ANDROINFO_HOOK_H
#define ANDROINFO_HOOK_H

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


#endif //ANDROINFO_HOOK_H
