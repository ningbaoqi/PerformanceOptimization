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

#### [捕获堆转储]()
+ 堆转储显示在捕获堆转储时应用程序正在使用内存的对象;特别是在扩展用户会话之后,堆转储可以通过显示仍然在内存中的对象来帮助识别内存泄漏。捕获堆转储后可以查看:应用程序分配了哪些类型的对象,以及每个对象的数量;每个对象使用多少内存;每个对象的引用被保留在你的代码中;调用堆栈，用于分配对象的位置(只有在记录分配时捕获堆转储);

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/a10.jpg)

+ 要捕获堆转储，单击Memory-Profiler工具栏中的dump Java堆;在转储堆时，Java内存的数量可能会暂时增加。这是正常的，因为堆转储发生在与应用程序相同的进程中，需要一些内存来收集数据;浏览列表以查找具有异常大堆计数的对象，因为它可能会被泄露。为了帮助查找已知类，请单击类名列标题以按字母顺序排序。然后单击类名。实例视图窗格出现在右边，显示该类的每个实例;在Instance View窗格中，单击一个实例。 References选项卡显示在下面，显示对该对象的所有引用。或者单击实例名称旁边的箭头以查看其所有字段，然后单击字段名称以查看其所有引用。如果要查看某个字段的实例详细信息，请右键单击该字段，然后选择Go to Instance;在References选项卡中，如果识别可能是内存泄漏的引用，请右键单击它，然后选择Go to Instance.。这将从堆转储中选择相应的实例，显示您自己的实例数据;默认情况下，堆转储不会显示每个已分配对象的堆栈跟踪。要获取堆栈跟踪，您必须在单击转储Java堆之前开始记录内存分配;如果您这样做，您可以在实例视图中选择一个实例，并在References选项卡旁边看到Call Stack选项卡;

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/a11.jpg)

+ Heap Count: 堆中的实例数;Shallow Size: 此堆中所有实例的总大小(以字节为单位);Retained Size: 这个类的所有实例(以字节为单位)保留的内存总大小;Default heap: 当系统没有指定堆时;App heap: 应用程序分配内存的主堆;Image heap: 系统引导映像，包含在引导期间预加载的类。这里的分配保证永远不会移动或离开;Zygote heap: Android系统中分发应用程序进程的写时复制堆;Depth：从任何GC根到所选实例的跳数最短;Shallow Size：此实例的大小;Retained Size：此实例支配的内存大小;
