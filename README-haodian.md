### 耗电优化
#### [耗电检测工具]()

|Battery Historian使用步骤|
|-------|
|初始化Battery Historian，使用如下两个adb命令`adb shell dumpsys batterystats --enable full-wake-history ;adb shell dumpsys batterystats --reset`|
|初始化完成后，操作需要测试电量的一些场景|
|保存数据，运行命令`adb bugreport > bugreport.txt`将bugreport信息保存到bugreport.txt文件|
|生成HTML报告；将historian.py脚本转换成HTML`python histroian.py -a bugreport.txt > battery.html`;因为histroian.py脚本是用python写的，所以需要python环境，histroian.py脚本可以从[github](https://github.com/google/battery-histroian)上下载|
|使用chrome打开生成的HTML文件，即可查看详细的报告|

|HTML报告的字段|说明|
|------|------|
|battery_level|电量，显示出电量的变化|
|plugged|充电状态，是否进行了充电，以及充电的时间范围|
|screen|屏幕状态，屏幕是否点亮|
|top|处于最上层的应用，可以通过此栏信息来判断某是哪个应用程序对手机电量的影响，同时也得到该应用的耗电量信息，同时也记录了应用启动和运行的时间|
|||
|||
|||
|||
|||
|||
