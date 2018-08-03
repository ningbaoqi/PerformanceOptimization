### 启动耗时监控
#### adb shell am
+ `adb shell am start -W [packageName]/[packageName.AppStartActivity]`；将会得到三个时间；

|adb shell am得到的时间|说明|
|-----|------|
|ThisTime|一般和TotalTime时间一样，如果在启动时开启一个过渡的全透明的页面(Activity)预先处理一些事，再显示出主界面(Activity)，这样将比TotalTime小|
|ToTalTime|应用的启动时间，包括创建线程+Application初始化+Activity初始化到界面显示|
|WaitTime|一般比TotalTime大些，包括系统影响的耗时|
