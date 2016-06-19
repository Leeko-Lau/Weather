package com.llk.weather.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llk.weather.model.Sk;
import com.llk.weather.db.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2016/6/6.
 */
public class SkDao {

    private SQLiteHelper helper;
    private SQLiteDatabase db;

    public SkDao(Context context){
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    //添加信息
    public void add(Sk sk){
        db.execSQL(
                "INSERT INTO sk VALUES(null, ?)",
                new Object[]{sk.getTemp()}
        );
    }

    //删除信息
    public void delete(Sk sk){
        db.execSQL(
                "DELETE FROM sk WHERE _id=?",
                new Object[]{sk.get_id()}
        );
    }

    //根据id删除信息
    public void deletFromId(int id){
        db.execSQL(
                "DELETE FROM future WHERE _id=?",
                new Object[]{id}
        );
    }

    public Cursor queryTheCursor(){
        Cursor c = db.rawQuery("SELECT * FROM sk",null);
        return c;
    }

    //查询数据
    public ArrayList<Sk> query(){
        ArrayList<Sk> sks = new ArrayList<>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()){

            Sk sk = new Sk();
            sk.set_id(c.getInt(c.getColumnIndex("_id")));
            sk.setTemp(c.getString(c.getColumnIndex("S_temp")));
            sks.add(sk);
        }
        c.close();
        return sks;
    }

}
