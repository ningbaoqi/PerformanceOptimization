### 使用最优的数据类型
#### [HashMap与ArrayMap]()
+ HashMap并不是最节约的容器，会占用大量内存；Android为了解决HashMap这个问题提高了一个替代容器:ArrayMap;ArrayMap提供了和HashMap一样的功能，但避免了过多的内存开销；在ArrayMap中执行插入或删除操作时，从性能角度看，比HashMap还要差一些，但涉及很小的对象数，如1000以下，就不需要担心这个问题了；与HashMap相比，ArrayMap在循环遍历时更加简单高效；使用ArrayMap的场景：对象的数目非常小1000以内，但访问特别多，或者删除和插入频率不高时；当有映射容器，有映射发生，并且所有映射的容器也是ArrayMap时；
#### [枚举类型]()
+ 使用枚举类型来定义常量，会使代码更易读并且更安全，但在性能上却比普通常量定义差很多，而使用枚举类型的dex size是普通常量定义的dex size的13倍以上(只是dex code增加)；Android的官方文档也提醒了开发者尽量避免使用枚举类型，同时提供了注解来提供编译期间的类型检查；

```
    public static final int UI_PERF_LEVEL_0 = 0;
    public static final int UI_PERF_LEVEL_1 = 1;

    @IntDef({UI_PERF_LEVEL_0, UI_PERF_LEVEL_1})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PRE_LEVEL {

    }

    public static int getLevel(@PRE_LEVEL int level) {//通过这种方法既保证了类型安全，又不会给内存带来额外的开销
        switch (level) {
            case UI_PERF_LEVEL_0:
                return 0;
            case UI_PERF_LEVEL_1:
                return 1;
            default:
                throw new IllegalArgumentException("Unknow");
        }
    }
```
#### [LruCache]()
+ LruCache最近最少使用缓存，它用强引用保存需要缓存的对象；当其中的一个值被访问时，它被放到队列的尾部，当缓存将满时，队列头部的值被丢弃，之后可以被垃圾回收；

|LruCache比较重要的方法|说明|
|------|------|
|public final V get(K key)|返回cache中key对应的值，调用这个方法后，被访问的值会移动到队列的尾部|
|publiv final V put(K key , V value)|根据key存放value，存放的value会移动到队列的尾部|
|protected int sizeOf(K key,V value)|返回每个缓存对象的大小，用来判断缓存是否快满了，这个方法必须重写|
|protected void entryRemoved(boolean evicted , K key , V oldValue , V newValue)|当一个缓存对象被丢弃时调用的方法，这是一个空方法，可以重写，第一个参数为true：当缓存对象是为了腾出空间而被清理时；第一个参数为false：缓存对象的entry被remove移除或者被put覆盖时|
+ Android的官网也一直推荐使用LruCache作为图片内存缓存，里面保存了一定数量的对象强引用；

|Ｇoogle官方文档给出的建议，分配LruCache大小时考虑应用剩余内存有多大|
|------|
|一次屏幕显示多少张图片，有多少张图片是缓存起来准备显示的|
|考虑设备的分辨路和尺寸，缓存相同的图片数，手机的dpi越大，需要的内存也越大|
|图片分辨率和像素质量决定了占用内存的大小|
|图片访问的频繁程度是多少，如果存在多个不同要求的图片类型，可以考虑用多个LruCache来做缓存，按照访问的频率分配到不同的LruCache中|
||
