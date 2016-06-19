package com.llk.weather.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.llk.weather.model.Today;
import com.llk.weather.db.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2016/6/5.
 */
public class TodayDao {

    private SQLiteHelper helper;
    private SQLiteDatabase db;

    public TodayDao(Context context){
        helper = new SQLiteHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    //添加信息
    public void add(Today today){
        db.execSQL(
                "INSERT INTO today VALUES(null, ?, ?, ?)",
                new Object[]{today.getCity(),today.getTemperature(),today.getWeather()}
        );
    }

    //删除信息
    public void delete(Today today){
        db.execSQL(
                "DELETE FROM today WHERE _id=?",
                new Object[]{today.get_id()}
        );
    }

    //根据id删除信息
    public void deletFromId(int id){
        db.execSQL(
                "DELETE FROM today WHERE _id=?",
                new Object[]{id}
        );
    }

    public Cursor queryTheCursor(){
        Cursor c = db.rawQuery("SELECT * FROM today",null);
        return c;
    }

    //查询所有数据
    public ArrayList<Today> query(){
        ArrayList<Today> todays = new ArrayList<>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()){
            Today today = new Today();
            today.set_id(c.getInt(c.getColumnIndex("_id")));
            today.setCity(c.getString(c.getColumnIndex("T_city")));
            today.setTemperature(c.getString(c.getColumnIndex("T_temperature")));
            today.setWeather(c.getString(c.getColumnIndex("T_weather")));
            todays.add(today);
        }
        c.close();
        return todays;
    }

    //查询所有城市
    public ArrayList<Today> queryCity(){
        ArrayList<Today> citys = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT T_city FROM today",null);
        while(c.moveToNext()){
            Today today = new Today();
            today.setCity(c.getString(c.getColumnIndex("T_city")));
            citys.add(today);
        }
        return citys;
    }

}
