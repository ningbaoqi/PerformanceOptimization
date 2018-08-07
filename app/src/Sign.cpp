//
// Created by 123 on 2018/8/7.
//
//需要先在应用程序中注册导致应用Crash的信号量处理函数，在AndroidNative层注册信号量函数的代码如下
#include <signal.h>
int sigaction(int sig,struct sigaction* act , struct sigaction* oact);
struct sigaction{
    void(* sa_handler)(int);
    void(* sa_sigaction)(int ,siginfo_t*,void*);
    sigset_t sa_mask;
    int sa_flags;
    void(*sa_restorer)(void);
}
