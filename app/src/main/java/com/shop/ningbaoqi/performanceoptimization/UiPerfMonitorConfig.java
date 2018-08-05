package com.shop.ningbaoqi.performanceoptimization;

import android.os.Environment;

public interface UiPerfMonitorConfig {
    int TIME_WARNING_LEVEL_1 = 100;//定义卡顿靠警域值
    int TIME_WARNING_LEVEL_2 = 300;//需要上报线程信息的域值
    String LOG_PATH = Environment.getExternalStorageDirectory().getPath() + "androidtech/uiperf";
}
