package com.llk.weather.model;

import android.support.v7.app.AppCompatActivity;

import com.llk.weather.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2016/6/19.
 */
public class ActivityCollector {

    public static List<BaseActivity> activities = new ArrayList<>();

    public static void addActivity(BaseActivity activity){
        activities.add(activity);
    }

    public static void removeActivity(BaseActivity activity){
        activities.remove(activity);
    }

    public static void finishAll() {
        for (BaseActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
