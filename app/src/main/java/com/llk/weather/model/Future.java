package com.llk.weather.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 未来几天的天气
 */
public class Future implements Serializable{

    /**
     * _id : 插入数据库的ID
     * date : 日期
     * weather_id : 天气组合
     * week : 星期
     * temperature : 温度
     * weather : 天气情况
     * wind : 风度等级
     */
    private int _id;
    private String date;
    private ArrayList<Weather_id> weather_id;
    private String week;
    private String temperature;
    private String weather;
    private String wind;

    public Future() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Future(String temperature, String week, String weather) {
        this.temperature = temperature;
        this.week = week;
        this.weather = weather;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeather_id(ArrayList<Weather_id> weather_id) {
        this.weather_id = weather_id;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Weather_id> getWeather_id() {
        return weather_id;
    }

    public String getWeek() {
        return week;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }

    public String getWind() {
        return wind;
    }


    @Override
    public String toString() {
        return "Future{" +
                "date='" + date + '\'' +
                ", weather_id=" + weather_id +
                ", week='" + week + '\'' +
                ", temperature='" + temperature + '\'' +
                ", weather='" + weather + '\'' +
                ", wind='" + wind + '\'' +
                '}';
    }
}
