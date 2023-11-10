package com.longthph30891.ph30891_mob2041_asm.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.longthph30891.ph30891_mob2041_asm.Database.DbHelper;
import com.longthph30891.ph30891_mob2041_asm.Model.Sach;
import com.longthph30891.ph30891_mob2041_asm.Model.Top;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class thongKeDAO {
    private final DbHelper dbHelper;
    public thongKeDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    // thong ke top 10
    public  ArrayList<Top> getTop10(){
        ArrayList<Top> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT SACH.TenSach ,COUNT(PHIEUMUON.MaSach)  FROM SACH LEFT JOIN PHIEUMUON ON SACH.MaSach = PHIEUMUON. MaSach GROUP BY SACH.TenSach ORDER BY COUNT(PHIEUMUON.MaSach) DESC LIMIT 10";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) { // Bắt đầu từ dòng đầu tiên của kết quả
            do {
                Top top = new Top();
                top.tenSach = cursor.getString(0);
                top.soLuong = cursor.getInt(1);
                list.add(top);
            } while (cursor.moveToNext()); // Di chuyển đến dòng tiếp theo của kết quả
        }
        cursor.close();db.close();
        return list;
    }
    // thong ke doanh thu

    @SuppressLint("Range")
    public int getDoanhThu(String dayStart, String dayEnd) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT SUM(TienThue) AS TongTien FROM PHIEUMUON WHERE NgayMuon BETWEEN ? AND ?";
        String[] selectionArgs = {dayStart, dayEnd};
        int tongTien = 0;
        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            tongTien = cursor.getInt(cursor.getColumnIndex("TongTien"));
            Log.d("DAO", "doanh thu: " + tongTien);
        }
        cursor.close();
        db.close();
        return tongTien;
    }
}
