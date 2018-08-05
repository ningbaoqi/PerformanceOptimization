package com.shop.ningbaoqi.performanceoptimization;

import android.os.Looper;
import android.util.Log;

import java.io.File;

/**
 * 监控管理类
 */
public class UiPerfMoniter implements UiPerfMonitorConfig, LogPrinterListener {
    private static UiPerfMoniter mInstance = null;
    private final String TAG = "UiPerfMoniter";
    private LogPrinter mLogPrinter;
    private LogWriteThread mLogWriterThread;
    private int monitorState = UI_PERF_MONITOR_STOP;
    private CpuInfoSampler mCpuInfoSampler = null;

    public synchronized static UiPerfMoniter getmInstance() {
        if (mInstance == null) {
            mInstance = new UiPerfMoniter();
        }
        return mInstance;
    }

    public UiPerfMoniter() {
        mCpuInfoSampler = new CpuInfoSampler();
        mLogPrinter = new LogPrinter();
        mLogWriterThread = new LogWriteThread();
        initLogpath();
    }

    public void startMonitor() {
        Looper.getMainLooper().setMessageLogging(mLogPrinter);
        monitorState = UI_PERF_MONITOR_START;
    }

    public void stopMonitor() {
        Looper.getMainLooper().setMessageLogging(null);
        mCpuInfoSampler.stop();
        monitorState = UI_PERF_MONITOR_STOP;
    }

    public boolean isMonitoring() {
        return monitorState == UI_PERF_MONITOR_START;
    }

    private void initLogpath() {
        File logPath = new File(LOG_PATH);
        if (!logPath.exists()) {
            boolean mkdir = logPath.mkdir();
            Log.d(TAG, "mkdir:" + mkdir);
        }
    }

    public void onStartLoop() {
        mCpuInfoSampler.start();
    }

    public void onEndloop(long startTime, long endTime, String loginInfo, int level) {
        mCpuInfoSampler.stop();
        switch (level) {
            case UI_PERF_LEVEL_1:
                Log.d(TAG, "onEndLoop");
                if (mCpuInfoSampler.getStatCpuInfo().size() > 0) {
                    StringBuffer buffer = new StringBuffer("statrTime:");
                    buffer.append(startTime);
                    buffer.append("endTime:");
                    buffer.append(endTime);
                    buffer.append("handlerTime:");
                    buffer.append(endTime - startTime);
                    for (CpuInfo info : mCpuInfoSampler.getStatCpuInfo()) {
                        buffer.append("\r\n");
                        buffer.append(info.toString());
                    }
                    mLogWriterThread.saveLog(buffer.toString());
                }
                break;
            case UI_PERF_LEVEL_2:
                break;
            default:
                break;
        }
    }
}
