package com.longthph30891.ph30891_mob2041_asm.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longthph30891.ph30891_mob2041_asm.Database.DbHelper;
import com.longthph30891.ph30891_mob2041_asm.Model.ThuThu;

import java.util.ArrayList;

public class thuThuDAO {
    private final DbHelper dbHelper;

    public thuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    // selectAll
    public ArrayList<ThuThu> selectAll(){
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM THUTHU",null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    ThuThu tt = new ThuThu();
                    tt.setMaTT(cursor.getString(0));
                    tt.setHoTenTT(cursor.getString(1));
                    tt.setMatKhau(cursor.getString(2));
                    list.add(tt);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    // add new
    public boolean insert(ThuThu tt){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTT",tt.getMaTT());
        values.put("HoTenTT",tt.getHoTenTT());
        values.put("MatKhau",tt.getMatKhau());
        long row = db.insert("THUTHU",null,values);
        return (row > 0);
    }
    // update
    public boolean update(ThuThu tt){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MatKhau",tt.getMatKhau());
        long row = db.update("THUTHU",values,"MaTT = ?",new String[]{String.valueOf(tt.getMaTT())});
        return row > 0;
    }
    // delete
    public boolean delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("THUTHU","MaTT=?",new String[]{String.valueOf(id)});
        return (row > 0);
    }

    // check Account
    public boolean checkThuThuAc(String username, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM THUTHU WHERE MaTT = ? AND MatKhau = ?";
        String[] selectionArgs ={username,password};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        boolean result = cursor.getCount()>0;
        cursor.close();
        db.close();
        return result;
    }
    public boolean checkUserNameExist(String username){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM THUTHU WHERE MaTT = ?";
        String[] selectionArgs ={username};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        boolean result = cursor.getCount()>0;
        cursor.close();
        db.close();
        return result;
    }
    public boolean checkOldPassword(String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM THUTHU WHERE MatKhau =?";
        String[] selectionArgs ={password};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        boolean result = cursor.getCount()>0;
        cursor.close();
        db.close();
        return result;
    }
    public ThuThu getUsn(String username){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ThuThu tt = null;
        String query = "SELECT * FROM THUTHU WHERE MaTT =?";
        String[] selectionArgs ={username};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        if (cursor.moveToFirst()){
            // Lấy thông tin từ cột cần thiết (thay thế phần này bằng cột thật của bảng THUTHU)
            @SuppressLint("Range") String maTT = cursor.getString(cursor.getColumnIndex("MaTT"));
            @SuppressLint("Range") String hoTenTT = cursor.getString(cursor.getColumnIndex("HoTenTT"));
            @SuppressLint("Range") String matkhau = cursor.getString(cursor.getColumnIndex("MatKhau"));
            // Tạo đối tượng ThanhVien với thông tin lấy từ cơ sở dữ liệu
            tt = new ThuThu();
            tt.setMaTT(maTT);
            tt.setHoTenTT(hoTenTT);
            tt.setMatKhau(matkhau);
        }
        cursor.close();
        db.close();
        return tt;
    }
}
