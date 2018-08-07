### 提高后台进程存活率
#### [应用进程优先级]()

|应用进程的优先级|说明|
|------|------|
|NATIVE_ADJ=-17|系统创建的Native进程|
|SYSTEM_ADJ=-16|系统进程，在运行的过程中永远不会杀掉，如果杀掉可能会导致严重问题|
|PERSISTENT_PROC_ADJ=-12|核心进程，系统不会杀掉该类进程，但即使杀掉了，影响面也没有SYSTEM_ADJ进程那样严重|
|PERSISTENT_SERVICE_ADJ=-11|正在运行的服务进程，一般不会被杀掉|
|FOREGROUND_APP_ADJ=0|前台进程，是指当前正在前台运行的应用，被杀掉概率不大|
|VISIBLE_APP_ADJ=1|可见进程，用户正在使用，或者界面在显示，除非出现异常，否则系统不会杀掉这类进程|
|PERCEPTIBLE_APP_ADJ=2|可感知的进程，虽然不再前台，但应用程序还在状态，系统除非到内存非常紧张才会杀掉该类进程，比如播放音乐的应用|
|BACKUP_APP_ADJ=3|正在备份的进程|
|HEAVY_WEIGHT_APP_ADJ=4|高权重进程|
|SERVICE_ADJ=5|有Service的进程|
|HOME_APP_ADJ=6|与home有交互的进程，比如有桌面部件和应用正在通信，widget小部件之类，一般尽量避免杀掉此类进程|
|PERVIOUS_APP_ADJ=7|切换进程，可以理解为从可见进程切换过来的进程的状态|
|CACHED_APP_MIN_ADJ=8|缓存进程，也就是空进程|
|SERVICE_B_ADJ=9|不活跃的进程|
|HIDDEN_APP_MIN_ADJ=15|缓存进程，空进程，在内存不足的情况下会被优先杀掉|
|UNKNOWN_ADJ=16|最低级别进程，只有缓存的进程，才有可能设置成这个级别|

+ 系统杀进程的规则如下：进程优先级设置为PERSISTENT_PROC_ADJ被杀概率较低，进程优先级HEAVY_WEIGHT_APP_ADJ这种是Activity仅次于主进程，系统认为是高权重进程；前台进程FOREGROUND_APP_ADJ不会被杀掉；当Activity、Service的生命周期发生变化时都会调整进程的优先级；进程中没有任何Activityd存在会优先被杀；空进程最容易被杀；
