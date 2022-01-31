package com.multimed.listation.controllers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.utilities.Utilities;

import java.security.Policy;

public class ListController {


    public static Cursor getAllLists(@NonNull SQLiteConnectionHelper conn){
        SQLiteDatabase db = conn.getReadableDatabase();

        return db.rawQuery("SELECT * FROM " + Utilities.TABLE_LIST, null);
    }

    public static Cursor getListById(@NonNull SQLiteConnectionHelper conn, @NonNull int id){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parameters = {(id+"")};

        return db.rawQuery("SELECT * FROM " + Utilities.TABLE_LIST + " WHERE " + Utilities.FIELD_ID + " = ?", parameters);
    }
}
