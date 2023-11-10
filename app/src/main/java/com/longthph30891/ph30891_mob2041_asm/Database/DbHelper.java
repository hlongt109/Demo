package com.longthph30891.ph30891_mob2041_asm.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String nameDb="PNLIB";
    static final int versionDb = 11;
    public DbHelper(@Nullable Context context) {
        super(context, nameDb, null, versionDb);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // bảng thành viên
        String tableTv = "CREATE TABLE THANHVIEN (MaTV INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1," +
                "HoTenTV TEXT NOT NULL," +
                "NamSinh TEXT NOT NULL)";
        db.execSQL(tableTv);
        // bảng thu thu
        String tableThuthu = "CREATE TABLE THUTHU (MaTT TEXT PRIMARY KEY," +
                "HoTenTT TEXT NOT NULL," +
                "MatKhau TEXT NOT NULL)";
        db.execSQL(tableThuthu);
        //
        String dataTT = "INSERT INTO THUTHU VALUES('TT01','Tran Hoang Long','TT01')";
        db.execSQL(dataTT);
        // bang loai sach
        String tableTLoaiSach = "CREATE TABLE LOAISACH (MaTheLoai INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1," +
                "TenTheLoai TEXT NOT NULL)";
        db.execSQL(tableTLoaiSach);
        // bang sach
        String tableSach = "CREATE TABLE SACH (MaSach INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1," +
                "TenSach TEXT NOT NULL," +
                "GiaThue INTEGER NOT NULL," +
                "MaTheLoai INTEGER REFERENCES LOAISACH (MaTheLoai))";
        db.execSQL(tableSach);
        // bang phieu muon
        String tablePMuon = "CREATE TABLE PHIEUMUON (MaPhieu INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1," +
                "MaSach INTEGER REFERENCES SACH (MaSach)," +
                "MaTT TEXT REFERENCES THUTHU (MaTT)," +
                "MaTV INTEGER REFERENCES THANHVIEN (MaTV)," +
                "TienThue INTEGER NOT NULL," +
                "NgayMuon TEXT NOT NULL," +
                "TrangThai INTEGER NOT NULL)";
        db.execSQL(tablePMuon);
        // admin
        String admin = "CREATE TABLE ADMIN (USERNAME TEXT PRIMARY KEY, PASSWORD TEXT)";
        db.execSQL(admin);
        String data = "INSERT INTO ADMIN VALUES ('admin','ph30891')";
        db.execSQL(data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            db.execSQL("DROP TABLE IF EXISTS ADMIN");
        }
      onCreate(db);
    }

}
