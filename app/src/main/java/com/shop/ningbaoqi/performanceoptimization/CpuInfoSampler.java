package com.shop.ningbaoqi.performanceoptimization;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CpuInfoSampler extends BaseSampler {
    private final String TAG = "CpuInfoSampler";
    private int mPid = -1;
    private ArrayList<CpuInfo> mCpuInfoList = new ArrayList<>();
    private long mUserPre = 0;
    private long mSystemPre = 0;
    private long mIdlePre = 0;
    private long mIoWaitPre = 0;
    private long mTotalPre = 0;
    private long mAppCpuTimePre = 0;

    public CpuInfoSampler() {

    }

    @Override
    void doSample() {
        Log.d(TAG, "doSample");
        dumpCpuInfo();
    }

    @Override
    public void start() {
        super.start();
        mUserPre = 0;
        mSystemPre = 0;
        mIdlePre = 0;
        mIoWaitPre = 0;
        mTotalPre = 0;
        mAppCpuTimePre = 0;
        mCpuInfoList.clear();
    }

    public ArrayList<CpuInfo> getStatCpuInfo() {
        return mCpuInfoList;
    }

    private void dumpCpuInfo() {
        BufferedReader cpuReader = null;
        BufferedReader pidReader = null;
        try {
            cpuReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1024);
            String cpuRate = cpuReader.readLine();
            if (cpuRate == null) {
                cpuRate = "";
            }
            if (mPid < 0) {
                mPid = android.os.Process.myPid();
            }
            pidReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + mPid + "/stat")), 1024);
            String pidCpuRate = pidReader.readLine();
            if (pidCpuRate == null) {
                pidCpuRate = "";
            }
            parseCpuRate(cpuRate, pidCpuRate);
        } catch (Throwable throwable) {
            Log.d(TAG, "doSample:" + throwable);
        } finally {
            try {
                if (cpuReader != null) {
                    cpuReader.close();
                }
                if (pidReader != null) {
                    pidReader.close();
                }
            } catch (IOException e) {
                Log.d(TAG, "Sampledo:" + e);
            }
        }
    }

    private void parseCpuRate(String cpuRate, String pidCpuRate) {
        String[] cpuInfoArray = cpuRate.split("  ");
        if (cpuInfoArray.length < 9) {
            return;
        }
        long user_tiem = Long.parseLong(cpuInfoArray[2]);
        long nice_time = Long.parseLong(cpuInfoArray[3]);
        long system_time = Long.parseLong(cpuInfoArray[4]);
        long idle_time = Long.parseLong(cpuInfoArray[5]);
        long ioWait_time = Long.parseLong(cpuInfoArray[6]);
        long total_time = user_tiem + nice_time + system_time + idle_time + ioWait_time + Long.parseLong(cpuInfoArray[7]) + Long.parseLong(cpuInfoArray[8]);
        String[] pidCpuInfos = pidCpuRate.split(" ");
        if (pidCpuInfos.length < 17) {
            return;
        }
        long appCpu_time = Long.parseLong(pidCpuInfos[13]) + Long.parseLong(pidCpuInfos[14]) + Long.parseLong(pidCpuInfos[15]) + Long.parseLong(pidCpuInfos[16]);
        if (mAppCpuTimePre > 0) {
            CpuInfo mCi = new CpuInfo();
            long idleTime = idle_time - mIdlePre;
            long totalTime = total_time - mTotalPre;
            mCi.mCpuRate = (totalTime - idleTime) * 100L / totalTime;
            mCi.mAppRate = (appCpu_time - mAppCpuTimePre) * 100L / totalTime;
            mCi.mAppRate = (appCpu_time - mAppCpuTimePre) * 100L / totalTime;
            mCi.mSystemRate = (system_time - mSystemPre) * 100L / total_time;
            mCi.mIoWait = (ioWait_time - mIoWaitPre) * 100L / total_time;
            synchronized (mCpuInfoList) {
                mCpuInfoList.add(mCi);
                Log.d(TAG, "cpuInfo : " + mCi.toString());
            }
            mUserPre = user_tiem;
            mSystemPre = system_time;
            mIdlePre = idle_time;
            mIoWaitPre = ioWait_time;
            mTotalPre = total_time;
            mAppCpuTimePre = appCpu_time;
        }
    }
}
