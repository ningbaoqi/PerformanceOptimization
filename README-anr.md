### ANR剖析
#### [ANR介绍]()
+ ANR即应用无响应，应用发生ANR有几种类型；

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
