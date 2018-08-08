### 应用常用优化方案
#### [计算优化]()
+ `缩短代码产生指令运行的时间`进而减少应用对CPU时间片的占用时间；尽量减少浮点运算；避开浮点运算的优化方法：除法变乘法；充分利用移位；查表法，直接使用映射关系，但会增加内存开销，视具体场景而定；利用`arm neon`指令集做并行运算，需要ARM V7及以上架构CPU才能支持；
#### [避免WakeLock使用不当]()
+ 在Android操作系统中，最常用的唤醒手机的方法是使用PowerManager.WakeLock来保存CPU工作并防止屏幕自动变暗关闭，PowerManager负责对Android设备电源相关进行管理，WakeLock也是一种锁机制，只要应用中有WakeLock，通过相关参数去获取对应的锁，既可以达到对应的电源管理目的，也可以使系统无法进入休眠阶段；

```    
/**
 * 获取WakeLock
 *
 * @param context
 */
private void acquirWakeLock(Context context) {
    PowerManager.WakeLock lock = null;
    if (lock == null) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //第一个参数：指定要获取哪种类型的锁，不同的锁对系统CPU、屏幕和键盘有不同的管理策略，第二个参数是锁的自定义名称
        lock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "TEST");
        if (lock != null) {
            lock.acquire();
        }
    }
}
```

|Android中的WakeLock类型|说明|
|------|------|
|PARTIAL_WAKE_LOCK|保持CPU正常运转，屏幕和键盘灯有可能会关闭|
|SCREEN_DIM_WAKE_LOCK|保持CPU运转，允许保持屏幕显示但是有可能变暗，允许关闭键盘灯|
|SCREEN_BRIGHT_WAKE_LOCK|保持CPU运转，允许保持屏幕高亮显示，允许关闭键盘灯|
|FULL_WAKE_LOCK|保持CPU运转，保持屏幕高亮显示，键盘灯也保持亮度|
|ACQUIRE_CAUSES_WAKEUP|强制使屏幕亮起，这种锁主要用于一些必须通知用户的操作|
|ON_AFTER_RELEASE|当锁被释放时，保持屏幕亮起一段时间|

```
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

```
/**
 * 释放WakeLock
 */
private void releaseWakeLock() {
    if (lock != null) {
        lock.release();
        lock = null;
    }
}
```
