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
