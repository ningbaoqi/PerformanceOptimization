package com.shop.ningbaoqi.performanceoptimization.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class BitmapConfig {
    private int mWidth, mHeight;
    private Bitmap.Config mPreferred;

    public BitmapConfig(int widht, int height) {
        this(widht, height, Bitmap.Config.RGB_565);
    }

    public BitmapConfig(int widht, int height, Bitmap.Config preferred) {
        this.mWidth = widht;
        this.mHeight = height;
        this.mPreferred = preferred;
    }

    public BitmapFactory.Options getBitmapOptions() {
        return getBitmapOptions(null);
    }

    /**
     * 精确计算，需要图片is流先解码，再计算宽高比
     *
     * @param is
     * @return
     */
    public BitmapFactory.Options getBitmapOptions(InputStream is) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        if (is != null) {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            options.inSampleSize = calculateInSampleSize(options, mWidth, mHeight);
        }
        options.inJustDecodeBounds = false;
        return options;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int mWidth, int mHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidht) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidht) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
