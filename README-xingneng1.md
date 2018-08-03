### 卡顿检测工具Profile GPU Rendering
+ `从应用层绘制一个页面（View），主要有三个过程：CPU准备数据、GPU从数据缓存列表中获取数据、Display设备绘制`；

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/pic-7.jpg)

+ 打开Profile GPU Rendering后可以看到实时刷新的彩色图，每一根竖线表示一帧，由多个颜色组成，不同颜色的解释；

|不同颜色的解释|
|------|
|蓝色代表测量绘制的时间；在蓝色的线很高时，有可能因为需要重绘，或者自定义视图的onDraw方法处理事情太多|
|红色代表执行的时间；当红色的线非常高时，可能是由于重新提交了视图而导致的|
|橙色部分表示处理的时间，如果柱状图很高，就意味着GPU太繁忙了|
|绿色表示将资源转移到渲染线程的时间|

+ 可以使用`adb shell dumpsys gfxinfo （包名）`把具体的耗时输出到日志中来分析；任何时候超过绿线(对应时长是16ms)，就有可能丢失一帧的内容；
