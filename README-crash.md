### Crash监控
+ Crash(应用崩溃)是由于代码异常而导致App非正常退出，导致应用程序无法继续使用，所有工作都停止的现象；
#### [Java层Crash监控](https://github.com/ningbaoqi/PerformanceOptimization/commit/830f5dd853740cbd085903bffbb215bb0846f17d)
#### [Native层Crash监控]()

|AndroidNativeCrash信号|说明|
|-------|-------|
|SIGILL|执行了非法指令、可执行文件本身出错、堆栈溢出等情况|
|SIGABRT|调用abort函数生成|
|SIGBUS|访问非法地址，包括内存地址对齐出错|
|SIGFPE|算术运算错误，如溢出及除0等|
|SIGSEGV|访问不属于自己的存储空间或访问只读存储空间|
|SIGPIPE|管道异常，这个信号通常在进程间通信时产生|
