package com.longthph30891.ph30891_mob2041_asm.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longthph30891.ph30891_mob2041_asm.Database.DbHelper;

public class AdminDAO {
    private final DbHelper dbHelper;

    public AdminDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    public boolean checkTKAdmin(String username, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM ADMIN WHERE USERNAME = ? AND PASSWORD = ?";
        String[] selectionArgs ={username,password};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        boolean result = cursor.getCount()>0;
        cursor.close();
        db.close();
        return result;
    }
    public boolean checkOldPassword(String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM ADMIN WHERE PASSWORD = ?";
        String[] selectionArgs ={password};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        boolean result = cursor.getCount()>0;
        cursor.close();
        db.close();
        return result;
    }
}
