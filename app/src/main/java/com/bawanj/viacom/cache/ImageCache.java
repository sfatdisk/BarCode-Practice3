package com.bawanj.viacom.cache;


import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;


public class ImageCache implements ImageLoader.ImageCache  {

    LruCache<String, Bitmap> mImageCache;

    public ImageCache() {
       initImageCache();
    }

    private void initImageCache() {

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mImageCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }
}