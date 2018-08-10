package com.shop.ningbaoqi.performanceoptimization.manager;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.util.LruCache;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 内存缓存；可以使用多个LruCache缓存，根据不同的保存级别进行分类
 */
public class MemoryCache {
    private final int DEFAULT_MEM_CACHE_SIZE = 1024 * 12;
    private LruCache<String, Bitmap> mMemoryCache;
    private final String TAG = "MemoryCache";
    private Set<SoftReference<Bitmap>> mReusableBitmaps;//存放可重用Bitmap的软引用的集合

    public MemoryCache(float sizePer) {
        Init(sizePer);
    }

    private void Init(float sizePer) {//初始化LruCache
        int cacheSize = DEFAULT_MEM_CACHE_SIZE;
        if (sizePer > 0) {
            cacheSize = Math.round(sizePer * Runtime.getRuntime().maxMemory() / 1024);
        }
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                final int bitmapSize = getBitmapSize(value) / 1024;
                return bitmapSize == 0 ? 1 : bitmapSize;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    mReusableBitmaps.add(new SoftReference<Bitmap>(oldValue));//将将要移除的Bitmap加入到软引用集合中，作为内存复用的源对象
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mReusableBitmaps = Collections.synchronizedSet(new HashSet<SoftReference<Bitmap>>());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private int getBitmapSize(Bitmap value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return value.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return value.getByteCount();
        }
        return value.getRowBytes() * value.getHeight();
    }

    public Bitmap getBitmap(String url) {//通过url获取内存中的Bitmap对象，如果没有返回null，需要进行下一步的加载图片逻辑
        Bitmap bitmap = null;
        if (mMemoryCache != null) {
            bitmap = mMemoryCache.get(url);
        }
        if (bitmap != null) {
            return bitmap;
        }
        return null;
    }

    public void addBitmapToCache(String url, Bitmap bitmap) {//增加一个新的Bitmap到内存池中，url为key
        if (url == null || bitmap == null) {
            return;
        }
        mMemoryCache.put(url, bitmap);
    }

    public void clearCache() {
        if (mMemoryCache != null) {
            mMemoryCache.evictAll();
        }
    }


    public static Bitmap decodeSampledBitmapFromStream(InputStream is, BitmapFactory.Options options, ImageCache cache) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            addInBitmapOptions(options, cache);
        }
        return BitmapFactory.decodeStream(is, null, options);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void addInBitmapOptions(BitmapFactory.Options options, ImageCache cache) {
        options.inMutable = true;
        if (cache != null) {
            Bitmap inBitmap = cache.getBitmapFromReusableSet(options);
            if (inBitmap != null) {
                options.inBitmap = inBitmap;
            }
        }
    }

    public Bitmap getBitmapFromReusableSet(BitmapFactory.Options options) {
        Bitmap bitmap = null;
        if (mReusableBitmaps != null && !mReusableBitmaps.isEmpty()) {
            final Iterator<SoftReference<Bitmap>> iterator = mReusableBitmaps.iterator();
            Bitmap item;
            while (iterator.hasNext()) {
                item = iterator.next().get();
                if (null != item && item.isMutable()) {
                    if (canUseForInBitmap(item, options)) {
                        bitmap = item;
                        iterator.remove();
                        break;
                    }
                } else {
                    iterator.remove();
                }

            }
        }
        return bitmap;
    }

    /**
     * 因为使用inBitmap有一些限制，在Android4.4之前，只支持同等大小的位图，因此通过该方法来判断该Bitmap是否可以复用
     *
     * @param item
     * @param options
     * @return
     */
    private boolean canUseForInBitmap(Bitmap item, BitmapFactory.Options options) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return item.getWidth() == options.outWidth && item.getHeight() == options.outHeight && options.inSampleSize == 1;
        }
        int width = options.outWidth / options.inSampleSize;
        int height = options.outHeight / options.inSampleSize;
        int byteCount = width * height * getBytesPerPixel(item.getConfig());
        return byteCount <= item.getAllocationByteCount();
    }
}
