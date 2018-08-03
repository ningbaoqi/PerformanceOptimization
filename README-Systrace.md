### Systrace UI性能分析
+ Systrace提供的性能数据采集和分析工具，收集Android关键子系统(如surfaceFlinger、WindowManagerService、Framework关键模块，服务，View系统等)的运行信息；

|Systrace的使用方法|说明|
|------|------|
|在DDMS上使用|在Eclipse和AndroidStudio中都可以使用DDMS直接使用Systrace，打开AndroidDeviceMonitor连接手机并准备需要抓取的界面；单击Systrace按钮进行抓取前的设置，选择需要跟踪的内容，手机上开始操作需要跟踪的过程；到了设定好的时间后，生成trace文件，使用Chrome打开文件即可分析|
|代码获取|在trace被嵌套另外一个trace中时，endSection()方法只会结束离它最近的一个beginSection();trace的begin和end必须在同一个线程中执行|

```
Trace.beginSection("ningbaoqi");
setContentView(R.layout.activity_main);
textView = findViewById(R.id.text);
Trace.endSection();
```

+ Systrace分析，Alerts一栏标记了性能有问题的点，单击该点可以查看详细信息，Frame：每个应用都有一行专门显示frame，每一帧就显示为一个绿色的圆圈，当显示为黄色或者红色时，它的渲染时间超过了16.6ms；
