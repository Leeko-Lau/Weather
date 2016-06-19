package com.llk.weather.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llk.weather.R;
import com.llk.weather.model.Future;
import com.llk.weather.model.Sk;
import com.llk.weather.model.Today;
import com.llk.weather.model.WeatherInfo;
import com.llk.weather.ui.adapter.WeatherAdapter;
import com.llk.weather.ui.view.ShowWeatherView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private static final String EXTRA_FUTURE = "future";
    private static final String EXTRA_TODAY = "today";
    private static final String EXTRA_SK = "sk";
    private ArrayList<Future> futureData = null;
    private Today todayData = null;
    private Sk skData = null;
    private Today today;
    public TextView city;
    private TextView temp;
    private TextView weather;
    private TextView temperature;

    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;

    private View v;
    private ShowWeatherView showView;

    public String[] higth = new String[6];
    public String[] low = new String[6];

    public WeatherFragment() {
    }

    public static WeatherFragment getInstance(WeatherInfo weatherInfo) {
        WeatherFragment weatherFragment = new WeatherFragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable(EXTRA_FUTURE, weatherInfo.getFuture());
        bundle.putSerializable(EXTRA_TODAY, weatherInfo.getToday());
        bundle.putSerializable(EXTRA_SK, weatherInfo.getSk());
        weatherFragment.setArguments(bundle);

        return weatherFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        futureData = (ArrayList<Future>) getArguments().getSerializable(EXTRA_FUTURE);
        todayData = (Today) getArguments().getSerializable(EXTRA_TODAY);
        skData = (Sk) getArguments().getSerializable(EXTRA_SK);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_weather, container, false);

        initView();
        initRecyclerView();

        initWeatherView();

        return v;
    }

    private void initWeatherView() {
        showView = (ShowWeatherView) v.findViewById(R.id.showView);
        for(int i=0;i<6;i++){
            String temp = futureData.get(i).getTemperature();
            Log.d("MMM",temp);
            low[i] = temp.substring(0,2);
            higth[i] = temp.substring(4,6);

        }

        showView.setInfo(
                new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期日"},
                new String[]{"0","5","10","15","20","25","30","35","40"},
                higth,
                low);

    }

    private void initRecyclerView() {

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_weather);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        weatherAdapter = new WeatherAdapter(getActivity(), futureData);
        recyclerView.setAdapter(weatherAdapter);
    }

    private void initView() {

        city = (TextView) v.findViewById(R.id.city);
        city.setText(todayData.getCity());

        temp = (TextView) v.findViewById(R.id.temp);
        temp.setText(skData.getTemp());

        weather = (TextView) v.findViewById(R.id.weather);
        weather.setText(todayData.getWeather());

        temperature = (TextView) v.findViewById(R.id.temperature);
        temperature.setText(todayData.getTemperature());

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public String getTextViewA() {

        Log.d("text1",city.getText().toString());
        String text = city.getText().toString();
        Log.d("text2",text );
        return text;
    }
}
