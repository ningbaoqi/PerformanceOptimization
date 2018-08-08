### 三大模块省电优化
#### [显示]()
+ 在OLED上，深色会比浅色更省电，而在LED上影响耗电的只有亮度；在不影响美观的情况下优先使用深色；
#### [网络]()
+ 使用Wifi连接网络时的功耗要低于使用移动通信网络的功耗；

|优化网络连接降低电量消耗的几种方案|
|------|
|使用Wifi传输数据时，应尽量增大每个包的大小(不超过MTU)并降低发包的频率|
|在蜂窝移动网络下，最好做到批量执行网络请求，尽可能避免频繁的间隔网络请求，尽量多的保持在Radio Standby状态|
|尽量在Wifi环境下使用数据传输|
|使用效率高的数据格式和解析方法，在数据格式方面，JSON和Protobuf效率明显比XML好很多，Protobuf是一种语言无关、平台无关、扩展性好的用于通信协议、数据存储的结构化数据串行化方法|
|压缩数据格式，比如采用GZIP压缩，虽然解压需要消耗更多的CPU，但可以明显提高下载速度，使网络传输更快结束，可以节省更多的电量，并提高了数据的获取速度|

#### [变频]()

|Linux内核中提供了五种变频模式|说明|
|------|------|
|ondemand|最常见的CPU调速器，默认使用的调速器，有高需求时，迅速跳到最大频率，有低需求时，迅速降到最小频率|
|conservative|很常见的调速器，规则是慢升快降；注重省电，有高需求时，逐渐提高频率，有低需求时，迅速跳到低频率|
|interactive|很常见的调速器，规则和conservative相反，是快升慢降，注重响应速度，有高需求时，迅速跳到高频率，有低需求时，逐渐降低频率|
|Lulzactive|较新的调速器，根据负载逐渐升高或降低频率|
|powersave|很常见的调速器，很省电，因为它的作用就是把频率锁定在设定范围的最小值，负载再高也不升高频率|
|performance|很常见的调速器，性能最好也最省电，因为它将CPU频率锁定在设定范围的最大值，无论有多少负载，CPU都全速运行|

+ CPU频率高才更省电；

```
    /**
     * 在应用程序中调频需要root权限，有root权限的情况下，可以使用以下代码实现调频
     * 获取当前CPU调度模式
     */
    public void getCpuCurGovernor() {
        try {
            DataInputStream is = null;
            Process process = Runtime.getRuntime().exec("cat" + cpuFreqPath + "/scaling_governor");
            is = new DataInputStream(process.getInputStream());
            String line = is.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置CPU调度模式
     *
     * @param governor
     * @return
     */
    protected boolean writeCpuGovernor(String governor) {
        DataOutputStream os = null;
        byte[] buffer = new byte[256];
        String command = "echo" + governor + ">" + cpuFreqPath + "/scaling_governor";
        try {
            Process process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
```
