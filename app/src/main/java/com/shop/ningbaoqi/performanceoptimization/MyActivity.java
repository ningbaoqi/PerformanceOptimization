package com.shop.ningbaoqi.performanceoptimization;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 使用Sync服务;关联SyncAdapter后，进程的优先级变为1
 */
public class MyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(account.CONTENT_AUTHORITY, b);
    }

    /**
     * 添加账号
     */
    public void addAccount() {
        Account account = AccountService.getAccount();
        AccountManager manager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
        manager.addAccountExplicitly();
    }

    /**
     * 设置同步周期
     */
    public void setSyncDetail() {
        ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
        ContentResolver.addPeriodicSync(account, CONTENT_AUTHORITY, new Bundle(), SYNC_FREQUENCY);
    }
}
