package com.bawanj.viacom.utils;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bawanj.viacom.ViaComApplication;
import com.bawanj.viacom.cache.ImageCache;

public class VolleyHelper {

    private static VolleyHelper mInstance ;//= null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleyHelper(){
        // setup RequestQueue
        mRequestQueue = Volley.newRequestQueue( ViaComApplication.getGlobalContext() );
        // setup ImageLoader
        mImageLoader = new ImageLoader( mRequestQueue, new ImageCache() );
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

