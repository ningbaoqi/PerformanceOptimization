//
// Created by 123 on 2018/8/7.
//

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* jvm ,void* reserved){
    struct sigaction act_crash;
    memset(&act_crash,0,sizeof(act_crash));
    memset(sg_arrary_oact_crash,0,sizeof(sg_arrary_oact_crash));//sg_arrary_oact_crash是要处理的信号值的int数组，当发生crash，信号量为crash_signal中的一种时，就进入_JniCrashHandler函数处理
    act_crash.sa_flags = SA_RESTART|SA_SIGINFO;
    sigemptyset(& act_crash.sa_mask);
    for(unsigned int i = 0;i<sizeof(crash_signal)/sizeof(crash_signal[0]);++i){
        sigaction(crash_signal[i],& act_crash,sg_arrary_oact_crash + i);
    }
    return JNI_VERSION_1_6;
}
static int crash_signal[]={
    SIGILL,
    SIGABRT,
    SIGBUS,
    SIGFPE,
    SIGSEGV,
    SIGPIPE
};
static void _JniCrashHandler(int n , siginfo_t* siginfo , void* sigcontext){
    char* stacks = (char*)calloc(STACK_MAX_LEN,sizeof(char));
    core_dump(n,siginfo,sigcontext,stacks,STACK_MAX_LEN);
    ......
}
//建议上报时使用Java层的方法统一上报，发生异常尽量先保存到本地，在下一次网络正常时再上传日志