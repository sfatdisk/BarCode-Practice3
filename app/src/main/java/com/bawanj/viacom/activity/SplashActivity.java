package com.bawanj.viacom.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bawanj.viacom.R;
import com.bawanj.viacom.utils.VolleyHelper;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG= "SplashActivity";

    private LoadApplicationTask mLoadApplicationTask;
    private View mSplashView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().setTitle(TAG);
        // setup important tools
        VolleyHelper.getInstance();

        mSplashView = findViewById(R.id.splash_image);

        scheduleApplicationTask();
    }

    private void scheduleApplicationTask() {
        if (mLoadApplicationTask == null) {
            mLoadApplicationTask = new LoadApplicationTask();
        }

        mLoadApplicationTask.cancel(); // Pre cautionary check.
        mLoadApplicationTask.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        cancelLoadApplicationTask();
        loadNewsActivity();
    }

    private void cancelLoadApplicationTask() {
        if (mLoadApplicationTask != null) {
            mLoadApplicationTask.cancel();
            mLoadApplicationTask = null;
        }
    }

    private class LoadApplicationTask extends CountDownTimer {

        static final long DEFAULT_TIME_MS = 2000; // 2 seconds

        public LoadApplicationTask() {

            super( DEFAULT_TIME_MS, DEFAULT_TIME_MS);
        }

        @Override
        public void onTick(long l) {
            // Do Nothing
        }

        @Override
        public void onFinish() {
            loadNewsActivity();
        }
    }

    private void loadNewsActivity(){
        Intent loadNewsActivityIntent
                = new Intent(SplashActivity.this, NewsActivity.class);
        startActivity(loadNewsActivityIntent);
        finish();
    }

}
