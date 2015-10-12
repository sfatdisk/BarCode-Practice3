package com.bawanj.viacom;


import android.app.Application;
import android.content.Context;

public class ViaComApplication extends Application {

    private static Context sContext =null ;

    @Override
    public void onCreate(){
        super.onCreate();
        sContext= getApplicationContext();
    }

    public static Context getGlobalContext(){
        return sContext;
    }

}
