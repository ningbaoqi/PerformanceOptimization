package com.shop.ningbaoqi.performanceoptimization;

/**
 * Cpu信息类
 */
public class CpuInfo {
    public long mId = 0;//一个CPU信息的ID
    public long mCpuRate = 0;//总的CPU使用率
    public long mAppRate = 0;//当前app CPU使用率
    public long mSystemRate = 0;//系统进程
    public long mIoWait = 0;//等待时间
}
