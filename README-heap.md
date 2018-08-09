###  内存分析工具 Heap Viewer
+ Heap Viewer的主要功能是查看不同数据类型在内存中的使用情况；

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/a20.jpg)

+ Heap Viewer不仅可以用来检测是否由内存泄漏，还可以检测到内存抖动，因为内存抖动时，会频繁发生GC,这时只需要开启Heap Viewer观察内存数据的变化，如果发生内存抖动，就可以观察到数据在这段时间内频繁更新；

|Heap Viewer面板|说明|
|-------|-------|
|总览页|查看整体内存的使用情况，包括已使用和未使用内存的占比；Heap Size：堆栈分配给App的内存大小；Allocated：已分配使用的内存大小；Free：空闲的内存大小；%Used:Allocated/Heap size使用率；#Objects:对象数量|
|详情页|在详情页可以看到各种类型数据的内存开销；free:空闲的对象；data object:数据对象，Java类类型对象，是最主要的观察对象；class Object：Java类类型的引用对象；1-byte array:一字节的数组对象；2-byte array：两字节的数组对象；4-byte array:四字节的数组对象；8-byte array:8字节的数组对象；non-java object:非java对象；Count：数量；Total sizeL：总共占用的内存大小；Smallest：将对象占用内存从小到大排列，排在第一个的对象占用内存大小；Largest：将对象占用内存大小从小到大排列，排在最后一个的对象占用的内存大小l；Mediam：将对象占用内存的大小从小到大排列，排在中间的对象占用的内存大小；Average：平均值|
|具体类型内存分配柱状图|可以查看选中类型的内存分析情况|
