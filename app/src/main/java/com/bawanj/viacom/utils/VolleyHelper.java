package com.bawanj.viacom.utils;


import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bawanj.viacom.ViaComApplication;

public class VolleyHelper {

    private static VolleyHelper mInstance ;//= null;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    private VolleyHelper(){
        // setup RequestQueue
        mRequestQueue = Volley.newRequestQueue( ViaComApplication.getGlobalContext() );
        // setup ImageLoader
        mImageLoader = new ImageLoader( mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> mCache = new LruCache<>(getMaxCacheSize());

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    private static int getMaxCacheSize() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //Log.i("TAG", "The APP's Max memory is " + maxMemory + "KB");
        return maxMemory / 8;
    }

    public static synchronized VolleyHelper getInstance( ){
        if(mInstance == null){
            mInstance = new VolleyHelper();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }

}

