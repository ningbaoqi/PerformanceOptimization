### 使用最优的数据类型
#### [HashMap与ArrayMap]()
+ HashMap并不是最节约的容器，会占用大量内存；Android为了解决HashMap这个问题提高了一个替代容器:ArrayMap;ArrayMap提供了和HashMap一样的功能，但避免了过多的内存开销；在ArrayMap中执行插入或删除操作时，从性能角度看，比HashMap还要差一些，但涉及很小的对象数，如1000以下，就不需要担心这个问题了；与HashMap相比，ArrayMap在循环遍历时更加简单高效；使用ArrayMap的场景：对象的数目非常小1000以内，但访问特别多，或者删除和插入频率不高时；当有映射容器，有映射发生，并且所有映射的容器也是ArrayMap时；
