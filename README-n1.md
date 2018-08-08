### [CPU分析器CPU Profiler]()
#### CPU Profiler概述
+ CPU分析器可实时检查应用程序的CPU使用情况和线程活动，并记录方法跟踪;

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/cpu1.jpg)

##### [一、Event timeline]()
+ 显示应用程序在其生命周期中转换不同状态的活动,并指示用户与设备的交互，包括屏幕旋转事件;
##### [二、CPU timeline]()
+ 显示应用程序的实时CPU使用率（占总可用CPU的百分比）以及应用程序使用的线程总数;时间轴还显示其他进程的CPU使用情况（如系统进程或其他应用程序）;
##### [三、Thread activity timeline]()
+ 列出属于您的应用程序进程的每个线程,并使用不同的颜色在时间轴上指示其活动;绿色: 线程处于活动状态或准备好使用CPU。也就是说，它处于”运行”或”可运行”状态;黄色： 线程处于活动状态，但是在完成其工作之前，它正在等待I / O操作（如文件或网络I / O）;灰色： 线程正在睡眠，不会消耗任何CPU时间，当线程需要访问尚未可用的资源时，有时会发生这种情况。要么线程进入自愿性睡眠，要么内核使线程休眠，直到所需的资源可用;
##### [四、Tracing type]()
+ Sampled： 在应用程序执行期间，可以频繁地捕获应用程序的调用堆栈;profiler将捕获的数据集进行比较，以获取关于应用程序代码执行的时间和资源使用信息;Instrumented:在应用程序运行时记录每个方法调用的开始和结束时的时间戳,收集时间戳并与生成方法跟踪数据进行比较，包括时间信息和CPU使用;
##### [五、Record button]()
+ 开始和停止记录方法跟踪;
#### 记录和检查方法跟踪
+ 记录方法跟踪,选择Sampled或Instrumented类型,单击Record开始进行记录，完成后点击Stop recording停止记录;profiler自动选择记录的时间帧，并在方法跟踪窗格中显示它的跟踪信息;

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/a1.jpg)

##### [一、Selected time frame]()
+ 在跟踪窗格中检查的记录时间框架的部分;第一次记录一个方法跟踪时，CPU分析器将自动选择您在CPU时间线中记录的整个长度;如果要检查仅记录的时间帧的一部分的方法跟踪数据，您可以单击并拖动高亮显示区域的边缘来修改它的长度;
##### [二、Timestamp]()
+ 表示记录方法跟踪的开始和结束时间;
##### [三、Trace pane]()
+ 显示您所选择的时间框架和线程的方法跟踪数据;仅当您记录至少一个方法跟踪后，此窗格才会显示。在此窗格中，您可以选择如何查看每个堆栈跟踪以及如何测量执行时间;
##### [四、Buttom Up]()
+ 选择显示为Top Down tree, Bottom Up tree, Call Chart, or Flame Chart这些类型的图;Wall clock time:表示实际经过时间;Thread time:计时信息表示实际的消耗时间减去不消耗CPU资源的那段时间的任何部分;

##### [使用Call Chart选项卡检查跟踪]()
+ Call Chart选项卡提供一个方法跟踪的图形表示，其中一个方法调用(或调用者)的周期和时间在水平轴上表示，而它的callees则显示在垂直轴上。对系统api的方法调用以橙色显示，调用您的应用程序自己的方法以绿色显示，方法调用第三方api(包括java语言api)以蓝色显示;

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/a2.jpg)
