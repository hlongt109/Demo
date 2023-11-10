package com.longthph30891.ph30891_mob2041_asm.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.longthph30891.ph30891_mob2041_asm.Database.DbHelper;
import com.longthph30891.ph30891_mob2041_asm.Model.LoaiSach;
import com.longthph30891.ph30891_mob2041_asm.Model.Sach;

import java.util.ArrayList;

public class sachDAO {
    private final DbHelper dbHelper;
    public sachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    // selectAll
    public ArrayList<Sach> selectAll(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM SACH",null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    Sach s = new Sach();
                    s.setMaSach(cursor.getInt(0));
                    s.setTenSach(cursor.getString(1));
                    s.setGiaThue(cursor.getInt(2));
                    s.setMaTheLoai(cursor.getInt(3));
                    list.add(s);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    // add new
    public boolean insert(Sach s){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenSach",s.getTenSach());
        values.put("GiaThue",s.getGiaThue());
        values.put("MaTheLoai",s.getMaTheLoai());
        long row = db.insert("SACH",null,values);
        return (row > 0);
    }
    // update
    public boolean update(Sach s){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaSach",s.getMaSach());
        values.put("TenSach",s.getTenSach());
        values.put("GiaThue",s.getGiaThue());
        values.put("MaTheLoai",s.getMaTheLoai());
        long row = db.update("SACH",values,"MaSach =?",new String[]{String.valueOf(s.getMaSach())});
        return (row > 0);
    }
    // delete
    public boolean delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("SACH","MaSach=?",new String[]{String.valueOf(id)});
        return (row > 0);
    }
    @SuppressLint("Range")
    public String getTenLoaiSach(int maLs){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tenLs = "";
        String query = "SELECT TenTheLoai FROM LOAISACH WHERE MaTheLoai = ?";
        String[] selctionArgs = {String.valueOf(maLs)};
        Cursor cursor = db.rawQuery(query,selctionArgs);
        if(cursor.moveToFirst()){
            tenLs = cursor.getString(cursor.getColumnIndex("TenTheLoai"));
        }
        cursor.close();db.close();
        return  tenLs;
    }
}
