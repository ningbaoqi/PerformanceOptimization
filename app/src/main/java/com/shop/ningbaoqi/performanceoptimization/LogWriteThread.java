package com.shop.ningbaoqi.performanceoptimization;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * 存储与上报
 */
public class LogWriteThread implements UiPerfMonitorConfig {
    private Handler mWriteHandler = null;
    private final Object FILE_LOCK = new Object();
    private final SimpleDateFormat FILE_NAME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final String TAG = "LogWriteThread";

    public void saveLog(final String loginInfo) {
        getmControlHandler().post(new Runnable() {
            @Override
            public void run() {
                synchronized (FILE_LOCK) {
                    saveLog2Local(loginInfo);
                }
            }
        });
    }

    private void saveLog2Local(String loginInfo) {
        long time = System.currentTimeMillis();
        File logFile = new File(LOG_PATH + "/" + FILENAME + "-" + FILE_NAME_FORMATTER.format(time) + ".txt");
        StringBuffer mSb = new StringBuffer("/**********************************************/");
        mSb.append(TIME_FORMATTER.format(time));
        mSb.append("/******************************/");
        mSb.append(loginInfo + "\r\n");
        Log.d(TAG, "saveLogToSdCard" + mSb.toString());
        if (logFile.exists()) {
            writeLog4SameFile(logFile.getPath(), mSb.toString());
        } else {
            BufferedWriter writer = null;
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(logFile.getPath(), true), "UTF-8");
                writer = new BufferedWriter(outputStreamWriter);
                writer.write(mSb.toString());
                writer.flush();
                writer.close();
                writer = null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                        writer = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeLog4SameFile(String path, String string) {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(path, "rw");
            long fileLength = randomAccessFile.length();
            randomAccessFile.seek(fileLength);
            randomAccessFile.writeBytes(string);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void send2Server() {
        getmControlHandler().post(new Runnable() {
            @Override
            public void run() {
                // TODO 上传到服务器
            }
        });
    }

    public Handler getmControlHandler() {
        if (mWriteHandler == null) {
            HandlerThread handlerThread = new HandlerThread("SamplerThread");
            handlerThread.start();
            mWriteHandler = new Handler(handlerThread.getLooper());
        }
        return mWriteHandler;
    }
}
