package com.longthph30891.ph30891_mob2041_asm.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longthph30891.ph30891_mob2041_asm.Database.DbHelper;
import com.longthph30891.ph30891_mob2041_asm.Model.PhieuMuon;

import java.util.ArrayList;

public class phieuMuonDAO {
    private final DbHelper dbHelper;
    public phieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    // selectAll
    public ArrayList<PhieuMuon> selectAll(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM PHIEUMUON",null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    PhieuMuon pm = new PhieuMuon();
                    pm.setMaPhieu(cursor.getInt(0));
                    pm.setMaSach(cursor.getInt(1));
                    pm.setMaTT(cursor.getString(2));
                    pm.setMaTV(cursor.getInt(3));
                    pm.setTienThue(cursor.getInt(4));
                    pm.setNgayMuon(cursor.getString(5));
                    pm.setTrangThai(cursor.getInt(6));
                    list.add(pm);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    // add new
    public boolean insert(PhieuMuon pm){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaSach",pm.getMaSach());
        values.put("MaTT",pm.getMaTT());
        values.put("MaTV",pm.getMaTV());
        values.put("NgayMuon",pm.getNgayMuon());
        values.put("TienThue",pm.getTienThue());
        values.put("TrangThai",pm.getTrangThai());
        long row = db.insert("PHIEUMUON",null,values);
        return (row > 0);
    }
    public boolean update (PhieuMuon pm){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaPhieu",pm.getMaPhieu());
        values.put("MaSach",pm.getMaSach());
        values.put("MaTT",pm.getMaTT());
        values.put("MaTV",pm.getMaTV());
        values.put("TienThue",pm.getTienThue());
        values.put("NgayMuon",pm.getNgayMuon());
        values.put("TrangThai",pm.getTrangThai());
        long row = db.update("PHIEUMUON",values,"MaPhieu =?",new String[]{String.valueOf(pm.getMaPhieu())});
        return (row > 0);
    }
    // delete
    public boolean delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("PHIEUMUON","MaPhieu =?",new String[]{String.valueOf(id)});
        return (row > 0);
    }
    @SuppressLint("Range")
    public String getTenSach(int maSach){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tenSach = "";
        String query = "SELECT TenSach FROM SACH WHERE MaSach = ?";
        String[] selctionArgs = {String.valueOf(maSach)};
        Cursor cursor = db.rawQuery(query,selctionArgs);
        if(cursor.moveToFirst()){
            tenSach = cursor.getString(cursor.getColumnIndex("TenSach"));
        }
        cursor.close();db.close();
        return  tenSach;
    }
    @SuppressLint("Range")
    public int getGiaTien(int maSach){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int giaTien = 0;
        String query = "SELECT GiaThue FROM SACH WHERE MaSach = ?";
        String[] selctionArgs = {String.valueOf(maSach)};
        Cursor cursor = db.rawQuery(query,selctionArgs);
        if(cursor.moveToFirst()){
            giaTien = Integer.parseInt(cursor.getString(cursor.getColumnIndex("GiaThue")));
        }
        cursor.close();db.close();
        return  giaTien;
    }
    @SuppressLint("Range")
    public String getTenThanhVien(int maTvien){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String TenTv = "";
        String query = "SELECT HoTenTV FROM THANHVIEN WHERE MaTV = ?";
        String[] selctionArgs = {String.valueOf(maTvien)};
        Cursor cursor = db.rawQuery(query,selctionArgs);
        if(cursor.moveToFirst()){
            TenTv = cursor.getString(cursor.getColumnIndex("HoTenTV"));
        }
        cursor.close();db.close();
        return  TenTv;
    }
    @SuppressLint("Range")
    public String getTenThuThu(String maThuthu){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tenTT = "";
        if(maThuthu.equals("admin")){
            tenTT = "admin";
        }else {
            String query = "SELECT HoTenTT FROM THUTHU WHERE MaTT = ?";
            String[] selctionArgs = {maThuthu};
            Cursor cursor = db.rawQuery(query,selctionArgs);
            if(cursor.moveToFirst()){
                tenTT = cursor.getString(cursor.getColumnIndex("HoTenTT"));
            }
            cursor.close();
        }
        db.close();
        return  tenTT;
    }
}
