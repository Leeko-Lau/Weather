package com.llk.weather.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.llk.weather.R;
import com.llk.weather.model.ActivityCollector;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
