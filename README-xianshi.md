### Android系统显示原理

+ Android的显示过程可以简单的概括为：`Android应用程序把经过测量、布局、绘制后的surface缓存数据，通过SurfaceFlinger把数据渲染到显示屏幕上，通过Android的刷新机制来刷新数据，也就是说应用曾负责绘制，系统层负责渲染，通过进程间通信把应用层需要绘制的数据传递到系统层服务，系统层服务通过刷新机制把数据更新到屏幕上`；Android的`图形显示系统采用的是Client/Server架构`。`SurfaceFlinger（server）由C++代码编写`;

#### 绘制原理

+ `绘制任务是由应用层发起的，最终通过系统层绘制到硬件屏幕上`，也就是说，`应用进程绘制好后，通过跨进程通信机制把需要显示的数据传到系统层，有系统层中的SurfaceFlinger服务绘制到屏幕上`；

##### 应用层

+ UI界面的典型构成框架如下；

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/pic-2.jpg)

+ 在Android的每个View绘制中有三个核心的步骤；

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/pic-3.jpg)

##### Measure
+ `获取当前View的正确宽度childWidthMeasureSpec和高度childHeightMeasureSpec之后，可以调用它的成员方法Measure来设置它的大小，如果当前正在测量的子视图child是一个视图容器，那么它又会重复执行操作，直到它的所有子孙视图的大小都测量完成为止`；
##### Layout
+ `用深度优先原则递归得到所有视图（View）的位置，当一个子View在应用程序窗口左上角的位置确定之后，在结合它在前面测量过程中确定的宽度和高度，就可以完全确定它在应用程序窗口中的布局`；
##### Draw
+ 目前Android支持了两种绘制方式：`软件绘制（CPU）和硬件加速（GPU）`，其中`硬件加速`在Android3.0开始已经全面支持，很明显，`硬件加速在UI显示和绘制的效率远远高于CPU绘制`，但`硬件加速`存在明显的缺点；

|硬件加速的缺点|
|------|
|耗电；GPU的功耗比CPU高|
|兼容问题：某些接口和方法不支持硬件加速|
|内存大；使用OpenGL的接口至少需要8MB内存|

+ 所以是否使用硬件加速，需要考虑一些接口是否支持硬件加速，同时结合产品的形态和平台，如TV版本就不需要考虑功耗的问题，而且TV屏幕大，使用硬件加速容易实现更好的显示效果；


##### 系统层
+ `真正把需要显示的数据渲染到屏幕上，是通过系统级进程中的SurfaceFlinger服务来实现的`；该服务主要做的工作如下；

|SurfaceFlinger服务的主要工作|
|------|
|`响应客户端事件`，创建Layer与客户端的Surface建立链接|
|`接收客户端数据及属性`，修改Layer属性，如尺寸、颜色、透明度等|
|`将创建的Layer内容刷新到屏幕上`|
|`维持Layer的序列`，并对Layer最终输出做出裁剪计算|

+ 既然是两个不同进程，那么肯定需要一个跨进程的通信机制来完成数据传输，`在Android的显示系统，使用了Android的匿名共享内存，ShardClient，每一个应用和SurfaceFlinger之间都会创建一个SharedClient`，如图所示；

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/pic-4.jpg)

+ 在每一个`SharedClient`中，`最多可以创建31个SharedBufferStack`，`每个Surface都对应一个SharedBufferStack，也就是一个window`；`一个SharedClient对应一个Android应用程序，而一个Android应用程序可能包含多个窗口，即Surface，也就是说SharedClient包含的是SharedBufferStack的集合，因为最多可以创建31个SharedBufferStack，也就意味着一个Android应用程序最多可以包含31个窗口`，同时每个SharedBufferStack中又包含了两个（低于4.1版本）或三个（4.1及版本以上）缓冲区，即在后面的显示刷新机制中会提到的`双缓冲`和`三重缓冲技术`；最后总结起来显示整体流程分为三个模块，`应用层绘制到缓冲区`，`SurfaceFlinger把缓冲区数据渲染到屏幕上`，`由于是两个不同的进程，所以使用Android的匿名共享内存SharedClient缓存需要显示的数据来达到目的`；

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/pic-5.jpg)

+ 绘制过程首先是`CPU准备数据`，`通过Driver层把数据交给GPU渲染`，`其中CPU主要负责Measure、Layout、Record、Execute的数据计算工作`，`GPU（图形处理器）负责Rasterization(栅格化)、渲染`。由于图形API不允许CPU直接与GPU通信，而是`通过中间的一个图形驱动层`来连接这两部分；`图形驱动维护了一个队列`，`CPU把display list添加到队列中，GPU从这个队列取出数据进行绘制，最终才在显示屏幕上显示出来`；

#### 到底绘制一个单元多长时间才是合理的？
+ `FPS（Frames Per Second）：表示每秒传递的帧数`，在理想情况下，`60FPS就感觉不到卡，这意味着每次绘制时长应该在16ms以内`；

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/pic-6.jpg)

+ `Android系统每隔16ms发出VSYNC信号，触发对UI进行渲染，如果每次渲染都成功，这样就能够达到流畅的画面所需的60FPS，即为了实现60FPS，就意味着程序的大多数绘制操作都必须在16ms内完成`；`如果某个操作花费的时间是24ms，系统在得到VSYNC信号时无法进行正常渲染，这样就发生了丢帧现象，那么用户在32ms内看到的会是同一帧画面`，主要场景在`执行动画`或者`滑动ListView`时更容易感知到卡顿不流畅，是因为这里的操作相对复杂，容易发生丢帧的现象，从而感觉卡顿，`很多原因可以导致CPU或者GPU负载过重从而出现丢帧现象`；`可能是Layout太复杂，无法在16ms内完成渲染`，`可能是UI上有层叠太多的绘制单元`，`还有可能是动画执行的次数过多`；`最终的数据是刷新机制通过系统去刷新数据，刷新不及时也是引起卡顿的一个主要原因`；`GC的时候所有线程都将暂停`；`overdraw过多绘制`：`在屏幕上某个像素在每一帧内被绘制了很多次，经常出现在多层次的UI结构里面；View设置成invisible和gone的时候，有时候也会操作绘制`；

|从Android系统的显示原理来看，影响绘制的根本原因有以下两个方面|
|------|
|绘制任务太重，绘制一帧内容耗时太长|
|主线程太忙了，导致VSYNC信号来时还没有准备好数据导致丢帧|
|`同一时间动画执行的次数过多，导致CPU或GPU负载过重`|
|`View的过度绘制，导致某些像素在同一帧时间内被绘制多次，从而使CPU或GPU负载过重`|
|`View频繁的触发measure、layout，导致measure、layout累计耗时过多及整个View频繁的重新渲染`|
|`内存频繁出发GC过多，导致暂时阻塞渲染操作`|
|`冗余资源及逻辑导致加载和执行缓慢`|
|`ANR`|
