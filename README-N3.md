### 网络分析器 Network Profiler
+ 网络分析器在时间轴上显示实时网络活动，显示发送和接收的数据，以及当前连接的数量;在窗口的顶部，您可以看到事件时间线和①无线电电源状态(high/low)和wi-fi;在时间轴上，您可以单击和拖动来选择②时间轴的一部分来检查流量;③窗口显示在时间轴的选定部分中发送和接收的文件，包括文件名、大小、类型、状态和时间;可以通过单击任何列标题来对列表进行排序;还可以看到时间线所选部分的详细分解，显示每个文件被发送或接收的时间;单击连接的名称，查看所选文件发送或接收的详细信息。单击④选项卡查看响应数据、头信息或调用堆栈;

![image](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/gif/a12.jpg)

+ 如果网络分析器检测到流量值，但无法识别任何支持的网络请求。将收到以下错误消息：”Network Profiling Data Unavailable: There is no information for the network traffic you’ve selected.;目前，网络分析器只支持HttpURLConnection和OkHttp库。如果您的应用程序使用另一个网络连接库，那么您可能无法在网络分析器中查看您的网络活动;
