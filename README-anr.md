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
