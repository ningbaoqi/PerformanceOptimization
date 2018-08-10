package com.shop.ningbaoqi.performanceoptimization.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

public class DiskCache {

    /**
     * DiskLruCache初始化是调用它的open静态方法
     *
     * @param directory  缓存图片数据的文件夹，一般在sdcard上
     * @param appVersion 应用/引擎版本号，如果该属性发生变化，会自动删除钱一个版本的数据
     * @param valueCount 指key value 的对应关系一般情况下是1对1关系
     * @param maxSize    缓存图片的最大缓存数据大小，根据业务逻辑自身需求设置
     * @return
     */
    public static DiskLruCache open(File directory, int appVersion, int valueCount, long maxSize);

    /**
     * 初始化 DiskLruCache
     *
     * @param cacheSize
     * @param cacheFile
     */
    protected void init(final long cacheSize, final File cacheFile) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mDiskLruLock) {
                    if (!cacheFile.exists()) {
                        cacheFile.mkdir();
                    }
                    try {
                        mDiskLruCache = DiskLruCache.open(cacheFile, MiniImageLoaderConfig.VESION_IMAGELOADER, 1, cacheSize);
                        mDiskLruCacheStarting = false;
                        mDiskLruLock.notifyAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 通过URL获取MD5值
     *
     * @param key
     * @return
     */
    private String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (Exception e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            String hex = Integer.toHexString(0xFF & digest[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 图片数据写入本地缓存
     *
     * @param imageUrl
     * @param in
     */
    public void saveToDisk(String imageUrl, InputStream in) {
        synchronized (mDiskLruLock) {
            try {
                String key = hashKeyForDisk(imageUrl);
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (in != null && editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (FileUitls.copyStream(in, outputStream)) {
                        editor.commit();
                    } else {
                        editor.abort();//放弃此次提交
                    }
                }
                mDiskLruCache.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取图片缓存
     *
     * @param imageUrl
     * @param bitmapConfig
     * @return
     */
    public Bitmap getBitmapFromDiskCache(String imageUrl, BitmapConfig bitmapConfig) {
        synchronized (mDiskLruLock) {
            if (mDiskLruCache != null) {
                try {
                    String key = hashKeyForDisk(imageUrl);
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                    if (snapshot == null) {
                        return null;
                    }
                    InputStream is = snapshot.getInputStream(0);
                    if (is != null) {
                        final BitmapFactory.Options options = bitmapConfig.getBitmapOptions();
                        return BitmapUtils.decodeSampledBitmapFromStream(is, options);
                    } else {
                        //报告错误信息
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
