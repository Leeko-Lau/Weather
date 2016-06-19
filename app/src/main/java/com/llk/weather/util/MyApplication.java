package com.llk.weather.util;

import android.app.Application;

import com.thinkland.sdk.android.JuheSDKInitializer;

/**
 * Created by King on 2016/6/5.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        JuheSDKInitializer.initialize(getApplicationContext());
    }
}
