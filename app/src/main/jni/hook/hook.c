//
// Created by Administrator on 2019/11/1.
//

#include "hook.h"

uint32_t get_fun_addr(char *modulepath, char *symbolname) {

    int fd;
    fd = open(modulepath,O_RDONLY);
    if (fd == -1){
        LOGD("[-] open the so file fail");
    }
    else{
        LOGD("[+] open the so file success");
    }

    Elf_Ehdr *ehdr = (Elf_Ehdr *)malloc(sizeof(Elf_Ehdr));
    read(fd, ehdr, sizeof(Elf_Ehdr));
    if(fd == -1){
        LOGD("[-] read the elf header fail!!");
    }
    else{
        LOGD("[+] read the elf header success!!!");
    }
    //LOGD("[+] the elf header information:");
    uint32_t shdr_base = ehdr -> e_shoff;
    uint16_t shnum = ehdr -> e_shnum;
    uint32_t shstr_base = shdr_base + ehdr -> e_shstrndx * sizeof(Elf32_Shdr);

    /*LOGD("[+] shstrndx = %d",ehdr -> e_shstrndx);
    LOGD("[+] shoff = %d",shdr_base);
    LOGD("[+] shnum = %d",shnum);
    LOGD("[+] shstr_base = %d",shstr_base);*/

    Elf_Shdr *shstr = (Elf_Shdr *)malloc(sizeof(Elf_Shdr));
    lseek(fd, shstr_base, SEEK_SET);
    read(fd, shstr, sizeof(Elf_Shdr));
    if(fd == -1){
        LOGD("[-] read the shdr section str fail!!");
    }
    else{
        LOGD("[+] read the shdr section str success!!");
    }


    char *shstrtab = (char *)malloc(shstr -> sh_size);
        lseek(fd, shstr -> sh_offset, SEEK_SET);
        read(fd, shstrtab, shstr -> sh_size);
        if(fd == -1){
            LOGD("[-] read section str fail!!");
        }
        else{
            LOGD("[+] read section str success!!");
        }

        Elf_Shdr *shdr = (Elf_Shdr *)malloc(sizeof(Elf_Shdr));
        lseek(fd, shdr_base, SEEK_SET);

        uint16_t i;
        char *str = NULL;
        Elf_Shdr *relplt_shdr = (Elf_Shdr *) malloc(sizeof(Elf_Shdr));
        Elf_Shdr *dynsym_shdr = (Elf_Shdr *) malloc(sizeof(Elf_Shdr));
        Elf_Shdr *dynstr_shdr = (Elf_Shdr *) malloc(sizeof(Elf_Shdr));
        Elf_Shdr *got_shdr = (Elf_Shdr *) malloc(sizeof(Elf_Shdr));


        for(i = 0; i < shnum; ++i) {
            read(fd, shdr, sizeof(Elf_Shdr));
            if(fd == -1){
                LOGD("[-] read fail!!");
            }
            str = shstrtab + shdr -> sh_name;
            if(strcmp(str, ".dynsym") == 0)
                memcpy(dynsym_shdr, shdr, sizeof(Elf_Shdr));
            else if(strcmp(str, ".dynstr") == 0)
                memcpy(dynstr_shdr, shdr, sizeof(Elf_Shdr));
            else if(strcmp(str, ".got") == 0)
                memcpy(got_shdr, shdr, sizeof(Elf_Shdr));
            else if(strcmp(str, ".rel.plt") == 0)
                memcpy(relplt_shdr, shdr, sizeof(Elf_Shdr));
        }

        char *got = (char *) malloc(sizeof(char) * got_shdr->sh_size);
        lseek(fd, got_shdr->sh_offset,SEEK_SET);
        if(read(fd, got, got_shdr->sh_size) != got_shdr->sh_size)
            LOGD("[-] read the got fail!!");
        else{
            LOGD("[+] read the GOT success!!");
        }

        char *dynstr = (char *) malloc(sizeof(char) * dynstr_shdr->sh_size);
        lseek(fd, dynstr_shdr->sh_offset, SEEK_SET);
        if(read(fd, dynstr, dynstr_shdr->sh_size) != dynstr_shdr->sh_size)
            LOGD("[-] read the dynstr fail!!");
        else{
            LOGD("[+] read the dynstr success!!");
        }


        Elf_Sym *dynsymtab = (Elf_Sym *) malloc(dynsym_shdr->sh_size);
        //LOGD("[+] dynsym_shdr->sh_size\t0x%x\n", dynsym_shdr->sh_size);
        lseek(fd, dynsym_shdr->sh_offset, SEEK_SET);
        if(read(fd, dynsymtab, dynsym_shdr->sh_size) != dynsym_shdr->sh_size)
            LOGD("[-] read the dynsym fail!!");
        else{
            LOGD("[+] read the dynsym success");
        }

        Elf_Rel *rel_ent = (Elf_Rel *) malloc(sizeof(Elf_Rel));
        lseek(fd, relplt_shdr->sh_offset, SEEK_SET);
        if(read(fd, rel_ent, sizeof(Elf_Rel)) != sizeof(Elf_Rel))
            LOGD("[-] read the rel_ent fail!!");
        else{
            LOGD("[+] read the rel_ent success");
        }


    uint32_t offset = 0;
    for (i = 0; i < relplt_shdr->sh_size / sizeof(Elf_Rel); i++)
    {

        uint16_t ndx = ELF_R_SYM(rel_ent->r_info);
        //LOGD("[+] ndx = %d, str = %s", ndx, dynstr + dynsymtab[ndx].st_name);
        if (strcmp(dynstr + dynsymtab[ndx].st_name, symbolname) == 0)
        {
            LOGD("[+] The offset of %s int the dyn GOT table: 0x%x", symbolname, rel_ent->r_offset);
            offset = rel_ent->r_offset;
            break;
        }
        if(read(fd, rel_ent, sizeof(Elf_Rel)) != sizeof(Elf_Rel))
        {
            LOGD("Fail to get the address of %s", symbolname);
        }
    }


    if(offset == 0)
    {
        LOGD("[-] can't find the address of %s,maybe it's static", symbolname);
        for(i = 0; i < (dynsym_shdr->sh_size) / sizeof(Elf_Sym); ++i)
        {
            if(strcmp(dynstr + dynsymtab[i].st_name, symbolname) == 0)
            {
                LOGD("[+] The address of %s为 int the static GOT table: 0x%x", symbolname, dynsymtab[i].st_value);
                offset = dynsymtab[i].st_value;
                break;
            }
        }
    }

    if(offset == 0)
    {
        LOGD("[-] Fail to get the address of %s", symbolname);
    }
    close(fd);

    return offset;
}

//获取so文件的基地址
static uint32_t get_module_base(pid_t pid, char *modulepath){
    FILE *fp = NULL;
    char *pch = NULL;
    char filename[32];
    char line[512];
    uint32_t addr = 0;
    LOGD("[+] get libc base...");

    if (pid < 0) {
        snprintf(filename, sizeof(filename), "/proc/self/maps");
        LOGD("pid < 0");
    } else {
        snprintf(filename, sizeof(filename), "/proc/%d/maps", pid);
        LOGD("pid > 0");
    }

    if ((fp = fopen(filename, "r")) == NULL) {
        LOGD("[-]open %s failed!", filename);
        return 0;
    }
    LOGD("[+]open %s success!", filename);
    while (fgets(line, sizeof(line), fp)) {
        if (strstr(line, modulepath)) {
            pch = strtok(line, "-");
            addr = strtoul(pch, NULL, 16);
            if(addr==0x8000)
                addr = 0;
            break;
        }
    }

    fclose(fp);
    LOGD("[+] libc base:0x%x...\n",addr);

    return addr;
}

//首先写一个open函数用于替换旧的open函数，之后将这个函数的地址替换到目标so的open函数地址
int *hook_my_open(char * path, int flag) {
    //char * retu = "0X00000000";
    LOGD("[+] start hook open()");
    LOGD("[+] hook_open path:%s",path);
    LOGD("[+] hook_open flag:%d",flag);
    LOGD("[+] ++++++++++++++++++++++++++++++++++");
    return open(path,flag);
}

//地址替换
void myHook(char *modulepath,char *symbolname,char *hookfunname){

    uint32_t old_addr = get_fun_addr(modulepath, symbolname);
    mprotect((void *)((uint32_t)old_addr & ~4095), 4096 , PROT_READ | PROT_WRITE);

    uint32_t old_fun_addr = *(uint32_t *)old_addr;
    *(uint32_t *)old_addr = (uint32_t)hookfunname;
}