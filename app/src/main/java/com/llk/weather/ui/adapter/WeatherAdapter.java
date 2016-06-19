package com.llk.weather.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.llk.weather.R;
import com.llk.weather.model.Future;

import java.util.ArrayList;

/**
 * Created by King on 2016/6/12.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Future> data;

    private LayoutInflater layoutInflater;

    public WeatherAdapter(Context context, ArrayList<Future> data) {

        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.item_weather,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.week = (TextView) v.findViewById(R.id.textView_week);
        viewHolder.weather = (ImageView) v.findViewById(R.id.imageView_weather);
        viewHolder.temperature = (TextView) v.findViewById(R.id.textView_temperature);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.week.setText(data.get(position).getWeek());

        String weather=null;
        weather = data.get(position).getWeather();
        int intId = stringToInt(weather);
        holder.weather.setImageResource(intId);

        holder.temperature.setText(data.get(position).getTemperature());

    }

    //把中文字符串改成对应的英文
    public int stringToInt(String weather2){

        String str = null;
        if(weather2.contains("转")){
            str = (String) weather2.subSequence(0,weather2.indexOf("转"));
        }else{
            str = weather2;
        }

        switch (str){
            case "晴":
                return R.drawable.sunshine;

            case "多云":
                return R.drawable.cloudy;

            case "阴":
                return R.drawable.cloudysky;

            case "小雪":
                return R.drawable.lsnow;

            case "中雪":
                return R.drawable.msnow;

            case "大雪":
                return R.drawable.hsnow;

            case "暴雪":
                return R.drawable.hsnow;

            case "阵雪":
                return R.drawable.lsnow;

            case "小雨":
                return R.drawable.lrain;

            case "中雨":
                return R.drawable.mrain;

            case "大雨":
                return R.drawable.hrain;

            case "大暴雨":
                return R.drawable.mhrain;

            case "特大暴雨":
                return R.drawable.mhrain;

            case "雨夹雪":
                return R.drawable.sleet;

            case "雷阵雨":
                return R.drawable.thundershower;

            case "阵雨":
                return R.drawable.thundershower;

            default: return 0;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView week;
        ImageView weather;
        TextView temperature;
    }
}
