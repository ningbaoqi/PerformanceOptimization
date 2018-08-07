package com.shop.ningbaoqi.performanceoptimization;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    private static final String CRASH_FILE_NAME = "crash";
    private static final String CRASH_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/log/";
    private static final String CRASH_FILE_SUFFIX = ".txt";
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    /**
     * 完成初始化工作
     *
     * @param context
     */
    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();//获取系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);//将当前实例设置为系统默认的异常处理器
        mContext = context;//获取Context方便内部使用
        //可以在初始化后在异步线程中上报上次保存的Crash信息
    }

    /**
     * 这个是最关键的方法，当程序中有未被捕获的异常时，系统会自动调用该方法，thread为出现未捕获异常的线程，throwable为未捕获的异常，有了这个throwable，就可以得到异常信息
     *
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            dumpExceptionToSdCard(throwable);//导出异常信息到SD卡中
            //这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();//打印出当前调用栈信息
        if (mDefaultCrashHandler != null) {//如果系统提供了默认的异常处理器，则交给系统结束我们的程序，否则就由我们自己结束
            mDefaultCrashHandler.uncaughtException(thread, throwable);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * 导出异常信息到SD卡中
     *
     * @param throwable
     */
    private void dumpExceptionToSdCard(Throwable throwable) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "没有挂载SD卡");
            return;
        }
        File dir = new File(CRASH_FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs()
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        File file = new File(CRASH_FILE_PATH + CRASH_FILE_NAME + time + CRASH_FILE_SUFFIX);
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            writer.println(time);//导出发生异常的时间
            getPhoneInfo(writer);//获取手机信息
            writer.println();
            throwable.printStackTrace(writer);//导出异常的调用栈信息
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPhoneInfo(PrintWriter writer) {
        // TODO 上报一些辅助信息，如应用版本号，系统版本号，手机型号等，方便数据分析和归类
    }
}
