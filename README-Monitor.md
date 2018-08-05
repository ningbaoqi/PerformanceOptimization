### 内存分析工具 Memory Monitor
+ 由于AndroidStudio的更新3.0以上，大多数Android设备监视器组件已弃用，需要手动开启，在`android-sdk/tools/`命令行下使用`monitor`，即可打开，但是有时候会报错，需要：一、在任务管理器进程里面关掉所有 monitor.exe 进程，二、 删除`C:\Users\123\.android`目录下的`monitor-workspace`整个文件夹，然后重新命令行打开即可；
##### 步骤一、选中调试包名点击Update Heap来统计信息，接着点击Cause GC制造GC操作，最后点击Dump HPROF file保存内存信息
![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/neicun1.jpg)
##### 步骤二、将保存的文件拖动到编写代码的窗口
![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/neicun2.jpg)

|区域|说明|
|------|------|
|A|应用中所有类的名字；App Heap：当前App使用的Heap；Image Heap：磁盘上当前App的内存映射拷贝；Zygote Heap：Zygote进程Heap，即Framework中通用类的Heap；Class Name 类名：Heap中的所有Class；Total Count：内存中该类这个对象总共的数量，有的在栈中，有的在堆中；Heap Count：堆内存中这个类对象的个数；Sizeof ：每个该实例占用的内存大小；Shallow Size：所有该类的实例占用的内存大小 ；Retained Size ：所有该类对象被释放掉，会释放多少内存|
|B|左边类的所有实例；Instance：该类的实例；Depth ：深度，从任一GC Root点到该实例的最短跳数；Dominating Size：该实例可支配的内存大小|
|C|选中B中实例后，这个实例的引用树|

##### 步骤三、点击左上边的运行三角可以直接将可能导致内存泄漏的类找出来
![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/neicun3.jpg)

