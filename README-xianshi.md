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
