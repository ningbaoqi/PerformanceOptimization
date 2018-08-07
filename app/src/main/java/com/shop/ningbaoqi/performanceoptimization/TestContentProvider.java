package com.shop.ningbaoqi.performanceoptimization;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/***
 * 一、创建ContentProvider实现数据同步
 */
public class TestContentProvider extends ContentProvider {
    public static final String AUTHKEY = "com.android.androidtech.androidsyncadapter";
    public static final String CONTENT_URI_BASE = "content://" + AUTHKEY;
    public static final String TABLE_NAME = "data";
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_BASE + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
