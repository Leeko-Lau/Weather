package com.llk.weather.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.llk.weather.R;
import com.llk.weather.model.Future;
import com.llk.weather.model.Sk;
import com.llk.weather.model.Today;
import com.llk.weather.model.WeatherInfo;
import com.llk.weather.dao.FutureDao;
import com.llk.weather.dao.SkDao;
import com.llk.weather.dao.TodayDao;

import java.util.ArrayList;


public class WelcomeActivity extends AppCompatActivity {

    private TodayDao todayDao;
    private FutureDao futureDao;
    private SkDao skDao;
    private ArrayList<Today> todays = new ArrayList<>();
    private ArrayList<Future> futures = new ArrayList<>();
    private ArrayList<Sk> sks = new ArrayList<>();
    private Today today;
    private Future future;
    private Sk sk;
    private WeatherInfo weatherInfo;
    private int weatherID;
    private ArrayList<Future> futureList = new ArrayList<>();
    private ArrayList<WeatherInfo> weatherInfoList = new ArrayList<>();
    private boolean flag = true;
    private Boolean isJump=false;
    private Context context;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = null;
            if(isJump){
                //如果没有数据，跳转到城市添加界面
                intent = new Intent(WelcomeActivity.this,AddCityActivity.class);
                intent.putExtra("className","WelcomeActivity");
                intent.putExtra("flag",1);
            }else {
                //如果有数据，把数据加载带主界面显示
                intent = new Intent(WelcomeActivity.this, MainActivity.class);
                //intent.putExtra("flag",flag);
                intent.putExtra("flag2",3);
                todayDao = new TodayDao(context);
                futureDao = new FutureDao(context);
                skDao = new SkDao(context);

                todays = todayDao.query();
                futures = futureDao.query();
                sks = skDao.query();
                //数据从数据库中读取然后封装到weatherInfo里面
                int i=1;
                for( i=1;i<=todays.size();i++){
                    today = todays.get(i-1);
                    sk = sks.get(i-1);
                    for(int j=7*i-6;j<=i*7;j++){
                        future = futures.get(j-1);
                        futureList.add(future);
                    }
                    weatherInfo = new WeatherInfo(sk,today,futureList);
                    weatherInfoList.add(weatherInfo);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("weatherInfos", weatherInfoList);
                intent.putExtras(bundle);
            }
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        context = this;

        if(new TodayDao(context).query().isEmpty()){
           isJump = true;
        }
        handler.sendMessageDelayed(Message.obtain(),3000);

    }

}
