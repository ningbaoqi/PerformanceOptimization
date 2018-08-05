package com.shop.ningbaoqi.performanceoptimization;

import android.os.Looper;
import android.util.Log;
import android.util.Printer;

/**
 * 卡顿监控
 */
public class LogPrinter implements Printer, UiPerfMonitorConfig {
    private final String TAG = "LogPrinter";
    private LogPrinterlistener mLogPrinter = null;
    private long startTime = 0;

    @Override
    public void println(String s) {
        if (startTime <= 0) {
            startTime = System.currentTimeMillis();
            mLogPrinter.onStartLoop();
        } else {
            long time = System.currentTimeMillis() - startTime;
            Log.d(TAG, "dispatch handler time" + time);
            execuTime(s, time);
            startTime = 0;
        }
    }

    /**
     * 根据需要定义更多级别
     *
     * @param logInfo
     * @param time
     */
    private void execuTime(String logInfo, long time) {
        int level = 0;
        if (time > TIME_WARNING_LEVEL_2) {
            Log.d(TAG, "Warning_level_2:\r\n" + "println" + logInfo);
            level = UI_PERF_LEVEL_2;
        } else if (time > TIME_WARNING_LEVEL_1) {
            Log.d(TAG, "Warning_level_1:\r\n" + "println" + logInfo);
            level = UI_PERF_LEVEL_1;
        }
        mLogPrinter.onEndLoop(logInfo, level);
    }

    public void startMonitor() {
        Looper.getMainLooper().setMessageLogging(mLogPrinter);
    }

    public void stopMonitor() {
        Looper.getMainLooper().setMessageLogging(null);
    }
}
