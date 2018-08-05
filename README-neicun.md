### [Android内存管理机制](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/README-neicun1.md)
### [内存分析工具](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/README-tools.md)

### 内存分析工具 Android Profiler
+ View->Tool Windows ->Android Profiler打开该工具；

|工具面板说明|说明|
|------|------|
|Memory|内存显示面板，显示运行当前应用程序时内存的变化情况；下面的蓝色部分表示当前占用的内存，上面的灰色部分表示是已经回收的内存，如果在图中看到尖峰就表示快速分配内存又被回收，也就是发生了内存抖动，需要优化|
|CPU|CPU显示面板，显示运行当前应用程序时CPU的变化情况|
|GPU|GPU显示面板，显示运行当前应用程序时GPU的变化情况|
|NetWord|NetWork面板，显示运行当前应用程序时占用网络资源的变化情况，能够准确统计并监控网络流量|
