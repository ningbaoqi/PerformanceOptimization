package com.shop.ningbaoqi.performanceoptimization;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 定时采样的类
 */
public abstract class BaseSampler {
    private final String TAG = "BaseSampler";
    private Handler mControlHandler = null;
    private int intervalTime = 500;//ms采样间隔
    private AtomicBoolean mIsSampling = new AtomicBoolean(false);

    public BaseSampler() {
        Log.d(TAG, "Init BaseSampler");
    }

    public void start() {
        if (!mIsSampling.get()) {
            Log.d(TAG, "start Sampler");
            getmControlHandler().removeCallbacks(mRunnable);
            getmControlHandler().post(mRunnable);
            mIsSampling.set(true);
        }
    }

    public void stop() {
        if (mIsSampling.get()) {
            Log.d(TAG, "stop Sampler");
            getmControlHandler().removeCallbacks(mRunnable);
            mIsSampling.set(false);
        }
    }

    /**
     * 创建一个HandlerThread定时采样
     *
     * @return
     */
    private Handler getmControlHandler() {
        if (null == mControlHandler) {
            HandlerThread handlerThread = new HandlerThread("SamplerThread");
            handlerThread.start();
            mControlHandler = new Handler(handlerThread.getLooper());
        }
        return mControlHandler;
    }

    abstract void doSample();//采样方法

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doSample();
            if (mIsSampling.get()) {
                getmControlHandler().postDelayed(mRunnable, intervalTime);
            }
        }
    };
}
