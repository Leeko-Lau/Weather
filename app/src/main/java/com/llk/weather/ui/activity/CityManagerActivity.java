package com.llk.weather.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.llk.weather.R;
import com.llk.weather.dao.FutureDao;
import com.llk.weather.dao.SkDao;
import com.llk.weather.dao.TodayDao;
import com.llk.weather.model.Future;
import com.llk.weather.model.Sk;
import com.llk.weather.model.Today;
import com.llk.weather.ui.adapter.Callback;
import com.llk.weather.ui.adapter.CityManagerAdapter;

import java.util.ArrayList;

public class CityManagerActivity extends BaseActivity implements Callback{

    private ImageButton addCity;

    private ListView listView;
    private ArrayList<Today> todayData = new ArrayList<>();
    private ArrayList<Sk> skData = new ArrayList<>();
    private ArrayList<Future> futureData = new ArrayList<>();
    private CityManagerAdapter managerAdapter;

    private TodayDao todayDao;
    private SkDao skDao;
    private FutureDao futureDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView_manager);

        todayDao = new TodayDao(this);
        todayData = todayDao.queryCity();

        skDao = new SkDao(this);
        skData = skDao.query();

        futureDao = new FutureDao(this);
        futureData = futureDao.query();

        managerAdapter = new CityManagerAdapter(this,todayData,this);
        listView.setAdapter(managerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {

                String cityName = todayData.get(position).getCity();

                Intent intent = new Intent(CityManagerActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cityName",cityName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        addCity = (ImageButton) findViewById(R.id.imageButton_add);
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        CityManagerActivity.this,
                        AddCityActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });
    }

    @Override
    public void click(View v) {
        TextView text = (TextView)v.findViewById(R.id.textView_city);
        String name = text.getText().toString();
        //todayData.remove(listView.getSelectedItemPosition());
        Today today = todayData.remove(listView.getSelectedItemPosition());
        managerAdapter.notifyDataSetChanged();

        int id = today.get_id();
        todayDao.delete(today);
        skDao.deletFromId(id);
        for (int i=0;i<5;i++) {
            futureDao.deletFromId(id+i);
        }

        Intent intent = new Intent();
        intent.setAction("delete");
        intent.putExtra("deleteName", name);
        sendBroadcast(intent);
    }
}
