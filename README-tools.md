### 内存分析工具 
#### 一、内存分析工具 Android Profiler
+ View->Tool Windows ->Android Profiler打开该工具；

|工具面板说明|说明|
|------|------|
|Memory|内存显示面板，显示运行当前应用程序时内存的变化情况；下面的蓝色部分表示当前占用的内存，上面的灰色部分表示是已经回收的内存，如果在图中看到尖峰就表示快速分配内存又被回收，也就是发生了内存抖动，需要优化|
|CPU|CPU显示面板，显示运行当前应用程序时CPU的变化情况|
|GPU|GPU显示面板，显示运行当前应用程序时GPU的变化情况|
|NetWord|NetWork面板，显示运行当前应用程序时占用网络资源的变化情况，能够准确统计并监控网络流量|

#### 二、内存分析工具 Memory Monitor
+ 由于AndroidStudio的更新3.0以上，大多数Android设备监视器组件已弃用，需要手动开启，在`android-sdk/tools/`命令行下使用`monitor`，即可打开，但是有时候会报错，需要：一、在任务管理器进程里面关掉所有 monitor.exe 进程，二、 删除`C:\Users\123\.android`目录下的`monitor-workspace`整个文件夹，然后重新命令行打开即可；
##### 步骤一、选中调试包名点击Update Heap来统计信息，接着点击Cause GC制造GC操作，最后点击Dump HPROF file保存内存信息
![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/neicun1.jpg)
