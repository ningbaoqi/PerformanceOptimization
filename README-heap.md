###  内存分析工具 Heap Viewer
+ Heap Viewer的主要功能是查看不同数据类型在内存中的使用情况；

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/a20.jpg)

+ Heap Viewer不仅可以用来检测是否由内存泄漏，还可以检测到内存抖动，因为内存抖动时，会频繁发生GC,这时只需要开启Heap Viewer观察内存数据的变化，如果发生内存抖动，就可以观察到数据在这段时间内频繁更新；

|Heap Viewer面板|说明|
|-------|-------|
|总览页|查看整体内存的使用情况，包括已使用和未使用内存的占比|
|详情页|在详情页可以看到各种类型数据的内存开销|
|具体类型内存分配柱状图|可以查看选中类型的内存分析情况|
