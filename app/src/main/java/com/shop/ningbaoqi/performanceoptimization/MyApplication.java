package com.shop.ningbaoqi.performanceoptimization;

import android.app.Application;
import android.content.Context;

/**
 * Crashhandler最好时在Application中注册
 */
public class MyApplication extends Application {
    private static Context context = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        initModule();
    }

    private void initModule() {
        CrashHandler crashHandler = new CrashHandler();
        crashHandler.init(this);
    }
}
