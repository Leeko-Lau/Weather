package com.llk.weather.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llk.weather.db.SQLiteHelper;
import com.llk.weather.model.Today;

import java.util.ArrayList;

/**
 * Created by King on 2016/6/14.
 */
public class QualityDao {
    private SQLiteHelper helper;
    private SQLiteDatabase db;

    public QualityDao(Context context){
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    //添加信息
    public void add(Today today){
        db.execSQL(
                "INSERT INTO quality VALUES(null, ?, ?, ?,?,?,?,?,?,?)",
                new Object[]{
                        today.getCity(),
                        today.getDressing_index(),
                        today.getTravel_index(),
                        today.getWash_index(),
                        today.getComfort_index(),
                        today.getExercise_index(),
                        today.getDressing_advice(),
                        today.getUv_index(),
                        today.getDrying_index()}
        );
    }

    //删除信息
    public void delete(Today today){
        db.execSQL(
                "DELETE FROM quality WHERE _id=?",
                new Object[]{today.get_id()}
        );
    }

    //根据id删除信息
    public void deletFromId(int id){
        db.execSQL(
                "DELETE FROM quality WHERE _id=?",
                new Object[]{id}
        );
    }

    public Cursor queryTheCursor(){
        Cursor c = db.rawQuery("SELECT * FROM quality",null);
        return c;
    }

    //查询所有数据
    public ArrayList<Today> query(){
        ArrayList<Today> todays = new ArrayList<>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()){
            Today today = new Today();
            today.set_id(c.getInt(c.getColumnIndex("_id")));
            today.setCity(c.getString(c.getColumnIndex("Q_city")));
            today.setDressing_index(c.getString(c.getColumnIndex("Q_dressing")));
            today.setTravel_index(c.getString(c.getColumnIndex("Q_travel")));
            today.setWash_index(c.getString(c.getColumnIndex("Q_wash")));
            today.setComfort_index(c.getString(c.getColumnIndex("Q_comfort")));
            today.setExercise_index(c.getString(c.getColumnIndex("Q_exercise")));
            today.setDressing_advice(c.getString(c.getColumnIndex("Q_dressingA")));
            today.setUv_index(c.getString(c.getColumnIndex("Q_uv")));
            today.setDrying_index(c.getString(c.getColumnIndex("Q_drying")));
            todays.add(today);
        }
        c.close();
        return todays;
    }
}
