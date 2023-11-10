package com.longthph30891.ph30891_mob2041_asm.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longthph30891.ph30891_mob2041_asm.Database.DbHelper;
import com.longthph30891.ph30891_mob2041_asm.Model.LoaiSach;
import com.longthph30891.ph30891_mob2041_asm.Model.Sach;

import java.util.ArrayList;

public class loaiSachDAO {
    private final DbHelper dbHelper;
    public loaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    // selectAll
    public ArrayList<LoaiSach> selectAll(){
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM LOAISACH",null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    LoaiSach ls = new LoaiSach();
                    ls.setMaTheLoai(cursor.getInt(0));
                    ls.setTenTheLoai(cursor.getString(1));
                    list.add(ls);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    // add new
    public boolean insert(LoaiSach ls){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenTheLoai",ls.getTenTheLoai());
        long row = db.insert("LOAISACH",null,values);
        return (row > 0);
    }
    // update
    public boolean update(LoaiSach ls){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTheLoai",ls.getMaTheLoai());
        values.put("TenTheLoai",ls.getTenTheLoai());
        long row = db.update("LOAISACH",values,"MaTheLoai=?",new String[]{String.valueOf(ls.getMaTheLoai())});
        return (row > 0);
    }
    // delete
    public boolean delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("LOAISACH","MaTheLoai=?",new String[]{String.valueOf(id)});
        return (row > 0);
    }
}
