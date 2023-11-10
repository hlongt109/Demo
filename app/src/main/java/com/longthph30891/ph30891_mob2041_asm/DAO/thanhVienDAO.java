package com.longthph30891.ph30891_mob2041_asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longthph30891.ph30891_mob2041_asm.Database.DbHelper;
import com.longthph30891.ph30891_mob2041_asm.Model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class thanhVienDAO {
    private final DbHelper dbHelper;
    public thanhVienDAO(Context context) {
         dbHelper = new DbHelper(context);
    }
    // selectAll
    public ArrayList<ThanhVien> selectAll(){
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM THANHVIEN",null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    ThanhVien tv = new ThanhVien();
                    tv.setMaTV(cursor.getInt(0));
                    tv.setHoTenTV(cursor.getString(1));
                    tv.setNamSinh(cursor.getString(2));
                    list.add(tv);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    // add new
    public boolean insert(ThanhVien tv){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTenTV",tv.getHoTenTV());
        values.put("NamSinh",tv.getNamSinh());
        long row = db.insert("THANHVIEN",null,values);
        return (row > 0);
    }
    // update
    public boolean update(ThanhVien tv){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTV",tv.getMaTV());
        values.put("HoTenTV",tv.getHoTenTV());
        values.put("NamSinh",tv.getNamSinh());
        long row = db.update("THANHVIEN",values,"MaTV=?",new String[]{String.valueOf(tv.getMaTV())});
        return (row > 0);
    }
    // delete
    public boolean delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("THANHVIEN","MaTV=?",new String[]{String.valueOf(id)});
        return (row > 0);
    }
}
