package com.llk.weather.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by King on 2016/6/5.
 */
public class WeatherInfo implements Serializable{

    private Sk sk;
    private Today today;
    private ArrayList<Future> future;

    public WeatherInfo() {
    }

    public WeatherInfo(Sk sk, Today today, ArrayList<Future> future) {
        this.sk = sk;
        this.today = today;
        this.future = future;
    }

    public Sk getSk() {
        return sk;
    }

    public void setSk(Sk sk) {
        this.sk = sk;
    }

    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }

    public ArrayList<Future> getFuture() {
        return future;
    }

    public void setFuture(ArrayList<Future> future) {
        this.future = future;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "sk=" + sk +
                ", today=" + today +
                ", future=" + future +
                '}';
    }
}
