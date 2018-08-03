### 分析函数调用过程工具TraceView
+ 在使用TraceView分析问题之前需要得到一个`*.trace`文件，然后通过TraceView来分析trace文件的信息；

|获取trace文件的方法|说明|
|------|------|
|DDMS中使用|连接设备->打开应用->打开DDMS若在AndroidStudio中则打开Android Device Monitor->单击start Method Profiling按钮->在应用中操作需要监控的点，比如进入一个Activity或者滑动一个列表，完成后单击Stop Method Profiling->结束会自动跳转到TraceView视图|
|代码中加入调试语句保存trace文件|在需要开始监控的地方调用startMethodTracing("ningbaoqi");在需要监控结束的地方调用stopMethodTracing();系统会在SD卡中创建`<trace-name>.trace`文件；使用traceView打开该文件进行分析|

```
startMethodTracing("ningbaoqi");
textView.setText("dasdadasdasdasdasdas");
stopMethodTracing();
```

|TraceView参数面板字段含义|说明|
|------|------|
|Name|所有的调用项，展开可以看到有的有Parent和Children子项，指被调用和调用|
|Inclusive|统计函数本身运行的时间 + 调用子函数运行的时间|
|incl|inclusive时间占总时间的百分比|
|Exclusive|同级函数本身运行的时间|
|Excl|执行占总时间的百分比|
|Calls+Recur Calls/Total|该方法调用次数+递归次数|
|Cpu Time/Call|该方法耗时|
|Real Time/Call|实际时长|
