package com.llk.weather.model;

import java.io.Serializable;

/**
 * 当前实况天气
 */
public class Sk implements Serializable{

    /**
     * _id : 插入数据库后的ID
     * temp : 当前温度
     * humidity : 当前湿度
     * wind_direction : 当前风向
     * time : 更新时间
     * wind_strength : 当前风力
     */

    private int _id;
    private String temp;
    private String humidity;
    private String wind_direction;
    private String time;
    private String wind_strength;

    public Sk() {
    }

    public Sk(String temp) {
        this.temp = temp;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWind_strength(String wind_strength) {
        this.wind_strength = wind_strength;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public String getTime() {
        return time;
    }

    public String getWind_strength() {
        return wind_strength;
    }


}
