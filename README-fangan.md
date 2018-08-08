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
#### [使用Job Scheduler]()

|在应用中常见的耗电场景|
|------|
|经常为了使一个特殊模块正常工作，而通过唤醒CPU去执行对应程序，但Google测试发现，每次唤醒CPU，即使程序只运行1s，但实际上消耗了大约两分钟的耗电量|
|一些不重要的数据，在非Wifi环境下上报，如上传日志等|
|在CPU高负荷状态下做一些数据清理工作|

+ 在Android5.0提供了Job Scheduler组件，只有一系列的预置条件满足时，才执行相应的操作，这样既可以省电，又保证了功能的完整性，也就是说可以将不紧急的任务交给Job Scheduler处理，Job Scheduler集中处理收到的任务，选择合适的时间、合适的网络、再一起执行；

|使用Job Scheduler的场景|
|------|
|重要不紧急的任务，可以延迟执行，如定期数据库数据更新和数据上报|
|耗电量较大的任务，比如充电时才希望执行的备份数据操作|
|不紧急可以不执行的网络任务，如在Wifi环境预加载数据|
|可以批量执行的任务|

```
private JobScheduler jobScheduler = null;
/**
 * 创建JobScheduler
 *
 * @param context
 */
public void JobSchedulerManager(Context context) {
    jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
}

/**
 * 创建一个JobInfo，描述一个任务的执行ID，以及触发这个任务的条件
 * JobInfo参数说明：任务ID，添加任务时，对不同的ID做不同的触发条件，即用switch代码实现；在执行时需要根据任务ID执行具体的任务；ComponentName是具体执行JobScheduler任务的服务
 * JobInfo支持的触发条件： setMinimumLatency 设置任务的延迟执行时间与setPeriodic方法不兼容，同时调用会发生异常
 *                      setOverrideDeadline 设置任务最晚的延迟时间与setPeriodic方法不兼容，同时调用会发生异常
 *                      setPersisted 设置重启之后，任务是否还需要继续执行
 *                      setRequiredNetworkType 只有满足指定的网络条件下，才会被执行： NETWORK_TYPE_NONE不管是否有网络，这个任务都会被执行，是默认值
 *                                                                              NETWORK_TYPE_ANY 只有在网络的情况下，任务才可以执行，和网络类型无关
 *                                                                              NETWORK_TYPE_UNMETERED 非运营商网络时任务才会被执行 如wifi
 *                      setRequiresCharging 只有在设备充电时，这个任务才会被执行
 *                      setRequiresDeviceIdle 只有当用户没有在使用该设备且有一段时间没有使用时，才会启动该任务
 * @param task_id
 * @return
 */
public boolean addJobScheduleTask(int task_id) {
    JobInfo.Builder builder = new JobInfo.Builder(task_id, new ComponentName("packagebane", JobSchedulerService.class.getName()));
    switch (task_id) {
        case 1:
            builder.setPeriodic(1000);
            break;
        case 2:
            builder.setPersisted(false);
            break;
        default:
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
            break;
    }
    if (jobScheduler != null) {
        return jobScheduler.schedule(builder.build()) > 0;
    } else {
        return false;
    }
}
```
```
<service android:name=".JobSchedulerService"
            android:permission="android.permission.BIND_JOB_SERVICE">
            .....
</service>
```
```
/**
 * 具体的执行任务类，默认运行在主线程
 */
public class JobSchedulerService extends JobService {

    /**
     * 任务开始时，执行该方法，系统用来触发已经被执行的任务，任务执行完毕，需要调用jobFinished()来通知系统
     *
     * @param params
     * @return
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        //执行具体的任务，建议在异步线程
        return false;
    }

    /**
     * 系统接收到一个取消请求时，调用该方法取消正在等待执行的任务，如果系统在接收一个取消请求时，实际任务队列中已经没有正在运行的任务，该方法不会被调用
     *
     * @param params
     * @return
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        //取消一个任务
        return false;
    }
}
```
#### [Doze模式]()
+ Doze模式是通过限制应用访问网络以及其他一些操作的频率，来减少CPU的开销，达到省电的目的，在Android6.0系统上，只满足两个条件就会进入Doze模式：未连接电源；屏幕关闭；

|Doze模式下维护的状态机|说明|
|------|------|
|ACTIVE|手机设备处于激活活动状态，设备在使用或者在充电中|
|INACTIVE|刚脱离ACTIVE状态，进入非活动状态，关闭屏幕并没有充电|
|IDLE_PENDING|IDLE预备状态，准备进入IDLE状态，在每隔30min让应用进入等待空闲预备状态|
|IDLE|设备进入空闲状态|
|IDLE_MAINTENANCE|处理挂起任务，在此状态可以执行在INACTIVE态挂起的任务(如闹钟或JobSchedule任务)|

|在Doze模式中的状态进入IDLE时会有以下限制|
|------|
|断开网络连接|
|系统忽略WakeLock|
|标准闹钟AlarmManager定时任务延迟到下一个maintenance window进行处理，如果应用仍需要在Doze时使用闹钟事件生效，可以使用setAndAllowWhileIdle()或setExactAndAllowWhileIdle()|
|系统不会扫描热点wifi|
|禁止同步工作|
|停止JobScheduler任务调度|

+ 一旦点亮了屏幕或插上电源充电，就会退出Doze模式；
