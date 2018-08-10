### 图片管理模块设计与实现

#### [实现异步加载功能](https://github.com/ningbaoqi/PerformanceOptimization/commit/93d3da4538bd29c44f3f11b831f2da1861dc19b6)

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/IMG20180810141331.jpg)

#### [实现三重缓存](https://github.com/ningbaoqi/PerformanceOptimization/commit/0a3498d56b7154283b9aa9c50e3efa567151dca0)

+ 内存缓存、本地缓存、网络缓存；

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/IMG20180810154139.jpg)

#### [开源图片组件]()

|开源图片组件|说明|
|------|------|
|UIL|可配置度高，支持任务线程池，下载器，编码器，内存以及磁盘缓存，显示选项等的配置；包含内存缓存和磁盘缓存两级缓存，支持定义本地缓存文件名的规则；支持多线程，支持异步和同步加载，以及下载进度监听；支持多种缓存算法，下载进度监听，ListView图片错乱解决等；接口灵活可控，支持暂停图片加载，把CPU时间交给其他线程|
|Picasso|在Adapter中需要取消已经不在视野范围的ImageView图片资源的加载，否则会导致图片错位，Picasso已经解决了这个问题；使用复杂的图片压缩转换来尽可能的减少内存消耗；自带内存和硬盘二级缓存功能|
|Fresco|Fresco会将图片放到一个特别的内存区域，当图片不再显示时，占用的内存会自动释放；渐进式呈现：渐进式图片格式是指先呈现大致的图片轮廓，然后随着图片下载的继续，呈现逐渐清晰的图片；支持gif和webp图片格式|
