package com.shop.ningbaoqi.performanceoptimization.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.swing.ImageCache;


public class MiniImageLoader extends ImageLoader {
    private volatile static MiniImageLoader miniImageLoader = null;
    private ImageCache mImageCache = null;

    public static MiniImageLoader getInstance() {
        if (null == miniImageLoader) {
            synchronized (MiniImageLoader.class) {
                miniImageLoader = new MiniImageLoader();
            }
        }
        return miniImageLoader;
    }

    public MiniImageLoader() {
        mImageCache = new ImageCache();
    }

    @Override
    protected Bitmap downLoadBitmap(String urlString) {
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = urlConnection.getInputStream();
            final BitmapFactory.Options options = new BitmapConfig(100, 100).getBitmapOptions(in);
            in.close();
            urlConnection.disconnect();
            urlConnection = (HttpURLConnection) url.openConnection();
            in = urlConnection.getInputStream();
            Bitmap bitmap = decodeSampledBitmapFromStream(in, options);
            return bitmap;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            CloseUtil.closeQuietly(in);
        }
        return null;
    }

    private Bitmap decodeSampledBitmapFromStream(InputStream is, BitmapFactory.Options options) {
        return BitmapFactory.decodeStream(is, null, options);
    }
}

class CloseUtil {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
