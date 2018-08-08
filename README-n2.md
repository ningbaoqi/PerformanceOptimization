### 内存分析器memory profiler
+ 可以帮助您识别内存泄漏和内存溢出，从而导致存根、冻结甚至应用程序崩溃。它显示了应用程序内存使用的实时图，让您捕获堆转储、强制垃圾收集和跟踪内存分配；

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/a8.jpg)

##### [一、强制执行垃圾收集事件的按钮]()
##### [二、捕获堆转储的按钮]()
##### [三、记录内存分配的按钮]()
##### [四、放大时间线的按钮]()
##### [五、跳转到实时内存数据的按钮]()
##### [六、事件时间线显示活动状态、用户输入事件和屏幕旋转事件]()
##### [七、内存使用时间表]()
+ 每个内存类别使用多少内存的堆栈图，如左边的y轴和顶部的颜色键所示;虚线表示已分配对象的数量，如右侧y轴所示;每个垃圾收集事件的图标;

#### [记录内存分配]()
+ 查看堆转储时，查看分配了多少内存的快照很有用，它不会显示如何分配内存。为此，您需要记录内存分配;要查看应用程序的内存分配，请单击内存分析器工具栏中的`Record memory allocations`;当它记录时，与你的应用程序进行交互，以引起内存溢出或内存泄漏。完成后，单击`Stop recording`;浏览列表以查找具有非常大的堆计数且可能泄漏的对象，要帮助查找已知类，请单击类名列标题按字母顺序排序。然后单击一个类名，Instance View 窗格就会显示在右侧，显示该类的每个实例;在Instance View窗格中，单击一个实例。Call Stack选项卡显示在下面，显示了哪个实例被分配在哪个线程中;在Call Stack选项卡中，单击任意行可以在编辑器中跳转到该代码;

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/a9.jpg)

+ Arrange by class： 根据类名分配;Arrange by package：根据包名分配;Arrange by callstack: 根据调用堆栈排序;
