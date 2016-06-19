package com.llk.weather.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.llk.weather.R;
import com.llk.weather.model.WeatherInfo;
import com.llk.weather.ui.adapter.TabAdapter;
import com.llk.weather.ui.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;

    private TabAdapter tabAdapter;

    private List<Fragment> fragmentList;
    private ImageButton imageButton_manager;
    private ImageButton imageButton_quality;
    private ArrayList<WeatherInfo> weatherInfos;
    private WeatherInfo weatherInfo ;

    private String cityName;
    private String deleteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initBroad();
        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag2",0);
        if(flag == 3){
            //如果flag等于3说明是从欢迎界面跳转过来的
            initViewPager();
        }else if (flag == 4){
            //flag等于4说明是从城市添加界面传过来的
            initViewPagerNull();
        }
    }


    private void initView() {
        imageButton_manager = (ImageButton) findViewById(R.id.button_cityManager);
        imageButton_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityManagerActivity.class);
                startActivity(intent);
            }
        });

        imageButton_quality = (ImageButton) findViewById(R.id.button_quality);
        imageButton_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityQualityActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initViewPagerNull(){
        Intent intent = getIntent();

        weatherInfo = (WeatherInfo) intent.getExtras().getSerializable("weatherInfo1");

        fragmentList.add(WeatherFragment.getInstance(weatherInfo));
        tabAdapter = new TabAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(tabAdapter);

    }

    //初始化ViewPager，根据数据来创建相对应的fragment
    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        weatherInfos = getData();
        fragmentList = new ArrayList<>();
        for(int i=0;i<weatherInfos.size();i++){
            weatherInfo = weatherInfos.get(i);
            //动态创建fragment
            fragmentList.add(WeatherFragment.getInstance(weatherInfo));
        }
        tabAdapter = new TabAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(tabAdapter);
    }

    private ArrayList<WeatherInfo> getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        weatherInfos = (ArrayList<WeatherInfo>) bundle.getSerializable("weatherInfos");
        return weatherInfos;
    }

    public String getCityName() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cityName = bundle.getString("cityName");
        return cityName;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        weatherInfo = (WeatherInfo) intent.getExtras().getSerializable("weatherInfo");

        fragmentList.add(WeatherFragment.getInstance(weatherInfo));

        tabAdapter.notifyDataSetChanged();

    }

    public String getDaleteName(){
        Intent intent = getIntent();
        deleteName = intent.getStringExtra("deleteName");
        return deleteName;
    }
    public Fragment nameToFragment(String name){

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for(int i=0;i<fragmentList.size();i++){
            Fragment fragment = fragmentList.get(i);
            WeatherFragment weatherFragment = (WeatherFragment) fragment;
            Log.d("FFEW",fragment.toString());
            if(name.equals(weatherFragment.getTextViewA())){
                return fragment;
            }
        }
        return null;
    }

    public void addFraggment(Fragment fragment,String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragment,tag);
        transaction.commit();
    }
    public void deleteFraggment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public void add(){
        String name = getCityName();
        Fragment fragment = nameToFragment(name);
        addFraggment(fragment, name);
    }
    public void delete(){
        String name = getDaleteName();
        Fragment fragment = nameToFragment(name);
        deleteFraggment(fragment);
    }

    public void initBroad() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("delete");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("delete")){
                delete();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
       // add();
    }
}
