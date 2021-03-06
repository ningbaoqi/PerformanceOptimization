### 减少不必要的内存开销
#### [自动装箱]()
+ `自动装箱会造成多余的内存开销，所以需要注意，通过traceview看见大量的integer.value`说明发生了自动装箱，需要优化；
#### [内存复用]()

|内存复用|说明|
|------|------|
|有效利用系统自带的资源|直接使用系统资源不仅可以一定程度上减少内存的开销，还可以减少应用程序的自身负重，减少APK的大小，并且复用性更好|
|视图复用|出现大量重复子组件，而子组件是大量重复的，可以使用ＶiewHolder实现ConvertView复用|
|对象池|可以在设计程序时显式的在程序中创建对象池，然后实现复用逻辑，对相同的类型数据使用同一块内存空间，也可以利用系统框架既有的具有复用特性的组件减少对象的重复创建，从而减少内存的分配与回收|
|Bitmap对象的复用|利用Bitmap的inBitmap的高级特性，提高Android系统在Bitmap的分配与释放效率，不仅可以达到内存复用，还提高了读写速度|
