### ANR剖析
#### [ANR介绍]()
+ ANR即应用无响应，在默认情况下在一个`Activity`当中最长的执行时间是`5秒`，如果超过了`5秒`没有做出响应，就会报出`ANR异常`；在`BroadcastReceiver`中最长的执行时间是`10秒`;在主线程中进行了耗时操作，就会导致`ANR`弹框；应用发生ANR有几种类型；

|发生ANR的集中类型|说明|
|-------|-------|
|KeyDispatchTimeout|最常见的ANR类型是对输入事件5s内无响应，比如按键或者触摸事件在此时间内无响应|
|BroadcastTimeout|BroadcastReceiver在指定时间(原生系统默认是10s)内无法处理完成，并且没有结束执行onReceive|
|ServiceTimeout|这种类型在Android应用中出现的概率很小，是指Service在特定的时间(原生系统是20s)内无法处理完成|

|引起ANR的根本原因总体来说有以下两种|
|------|
|应用程序自身逻辑有缺陷，或者在某种异常场景触发了缺陷，如主线程阻塞，死循环等导致|
|由于Android设备其他进程的CPU占用高，导致当前应用进程无法抢占到CPU时间片|

#### [ANR分析]()

|ANR日志标志|含义|
|------|------|
|ANR IN|发生ANR的具体类|
|PID|发生ANR的进程，系统在此时会生成trace文件，当前的时间点也是发生ANR的具体时间，以及生成trace文件的时间|
|Reason|当前ANR的类型以及导致ANRs的原因|
|CPU usage|CPU的使用情况，在日志中CPU usage有两个时间点，第一个是发生ANR前的CPU使用情况，第二个是发生ANR后的CPU使用情况；如果CPU使用量很少，说明主线程可能阻塞，如果IOWwait很高，说明ANR有可能是由于主线程进行耗时的IO操作造成的|

+ ANR日志文件/data/anr/traces.txt;在AndroidStudio上提供了一个分析trace文件的工具，AnalyzeStacktrace,AnalyzeStacktrace可以更直观的分析导致ANR的原因，在AndroidStudio的工具栏中，选择Analyze->Analyze Stacktrace，打开Analyze Stacktrace工具窗口，将trace.txt中的内容复制到窗口，单击Normalize按钮，生成Thread Dump列表，左边为所有线程列表，右边为选中线程的具体信息；如果某个线程被标红，说明此线程被阻塞了，然后在右边的详细信息中查看阻塞的具体原因；
#### [ANR监控]()
```
    /**
     * 因为导致ANR发送消息不能得到执行，所有根据这一特点就可以收集ANR
     * @param x
     */
    public void println(String x) {
        if (startTime <= 0) {
            startTime = System.currentTimeMillis();//发送消息，同时启动线程保存状态
            mLogPrinter.onStartLoop();
        } else {
            long endTime = System.currentTimeMillis();//执行消息，同时复位ANR线程状态
            execuTime(x, startTime, endTime);
            startTime = 0;
        }
    }
```
#### [造成ANR的主要原因]()
+ 应用程序的`响应性`是由`Activity Manager`和`Window Manager`系统服务监视的；当监视到太长的耗时操作，将会弹出`ANR`的对话框；造成`ANR`的主要原因：一：`主线程被IO操作(从Android4.0以后网络IO不允许在主线程中阻塞)`；二：`主线程中存在耗时的计算`；`Activity`的所有生命周期回调都是执行在`主线程中`的；`Service`默认是执行在`主线程`的；`BroadcastReceiver`的`onReceive()`回调时执行在`主线程`中的；没有使用`子线程`的`Looper`的`Handler`的`handleMessage()、post(Runnable)`是执行在`主线程`中的；`AsyncTask`的回调中除了`doInBackground()`其他都是执行在`主线程`中的；
#### [如何解决ANR]()
+ 一：可以在`AsyncTask`中处理`耗时操作`；二：使用`Thread`或者`HandlerThread`提高优先级；三、使用`Handler`来处理工作线程的`耗时任务`；四：在`Activity`的`onCreate()`和`onResume()`回调中`尽量避免耗时`的代码；
