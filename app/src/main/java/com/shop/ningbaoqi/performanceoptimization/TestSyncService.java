package com.shop.ningbaoqi.performanceoptimization;

import android.accounts.Account;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;

/**
 * 关联SyncAdapter通信服务；SyncAdapter是独立进程，所以应用实现自己的Binder接口
 */
public class TestSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static TestSyncAdapter adapter = null;

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock) {
            if (adapter == null) {
                adapter = new TestSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return adapter.getSyncAdapterBinder();
    }

    static class TestSyncAdapter extends AbstractThreadedSyncAdapter {
        public TestSyncAdapter(Context context, boolean autoInitialize) {
            super(context, autoInitialize);
        }

        @Override
        public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
            //具体的同步操作，这里主要是为了提高进程优先级
        }
    }
}
