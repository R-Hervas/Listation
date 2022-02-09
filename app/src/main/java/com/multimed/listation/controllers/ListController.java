package com.multimed.listation.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.utilities.Utilities;

import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class ListController {

    public static Cursor getAllLists(@NonNull SQLiteConnectionHelper conn){
        SQLiteDatabase db = conn.getReadableDatabase();

        return db.rawQuery("SELECT * FROM " + Utilities.TABLE_LIST, null);
    }

    public static void createNewList(@NonNull SQLiteConnectionHelper conn, @NonNull String name) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String insert = Utilities.INSERT_LIST + "( '" + name + "' ) ";

        db.execSQL(insert);

        db.close();
    }

    public static Cursor getListItems(SQLiteConnectionHelper conn, int id) {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parameters = {(id+"")};

        return db.rawQuery("SELECT * FROM " + Utilities.TABLE_ITEM + " WHERE " + Utilities.FIELD_LIST + " = ?", parameters);

    }

    public static void updateListName(@NonNull SQLiteConnectionHelper conn, @NonNull Integer id, @NonNull String name ){
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id.toString()};

        ContentValues values = new ContentValues();


        values.put(Utilities.FIELD_NAME, name);
        int i = db.update(Utilities.TABLE_LIST, values, Utilities.FIELD_ID + " = ? ", parameters);

        db.close();

    }

    public static int deleteListsById(@NonNull SQLiteConnectionHelper conn, @NonNull ArrayList<Integer> idList) {
        SQLiteDatabase db = conn.getWritableDatabase();

        int listDeleted = 0;



        for (Integer id : idList) {

            String[] parameters = {id.toString()};


            listDeleted =+ db.delete(Utilities.TABLE_LIST, Utilities.FIELD_ID + " = ?", parameters);

        }

        db.close();

        return listDeleted;
    }



}
