package com.shop.ningbaoqi.performanceoptimization.manager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public abstract class ImageLoader {
    private boolean mExitTasksEarly = false;//是否提前退出的标志
    protected boolean mPauseWork = false;
    private final Object mPauseWorkLock = new Object();

    protected ImageLoader() {

    }

    public void loadImage(String url, ImageView imageView) {
        if (url == null) {
            return;
        }
        BitmapDrawable bitmapDrawable = null;
        if (bitmapDrawable != null) {
            imageView.setImageDrawable(bitmapDrawable);
        } else {
            final BitmapLoadTask task = new BitmapLoadTask(url, imageView);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private class BitmapLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private String mUrl;
        private final WeakReference<ImageView> imageViewWeakReference;

        public BitmapLoadTask(String url, ImageView imageView) {
            mUrl = url;
            imageViewWeakReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            BitmapDrawable drawable = null;
            synchronized (mPauseWorkLock) {
                while (mPauseWork && !isCancelled()) {
                    try {
                        mPauseWorkLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (bitmap == null && !isCancelled() && imageViewWeakReference.get() != null && !mExitTasksEarly) {
                bitmap = downLoadBitmap(mUrl);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled() || mExitTasksEarly) {
                bitmap = null;
            }
            ImageView imageView = imageViewWeakReference.get();
            if (bitmap != null && imageView != null) {
                setImageBitmap(imageView, bitmap);
            }
        }

        @Override
        protected void onCancelled(Bitmap bitmap) {//任务取消后调用此方法
            super.onCancelled(bitmap);
            synchronized (mPauseWorkLock) {
                mPauseWorkLock.notifyAll();
            }
        }
    }

    private void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 图片加载线程控制接口，pauseWork控制图片模块的暂停和继续工作，一般用ListView等控件中，在滑动的时候，为了减少系统开销，可以暂停线程的工作来保证滑动的流畅度
     *
     * @param pauseWork
     */
    public void setPauseWork(boolean pauseWork) {
        synchronized (mPauseWorkLock) {
            mPauseWork = pauseWork;
            if (!mPauseWork) {
                mPauseWorkLock.notifyAll();
            }
        }
    }

    public void setExitTaskEarly(boolean exitTaskEarly) {
        mExitTasksEarly = exitTaskEarly;
        setPauseWork(false);
    }

    protected abstract Bitmap downLoadBitmap(String url);
}
