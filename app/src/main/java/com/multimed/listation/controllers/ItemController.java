package com.multimed.listation.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.utilities.Utilities;

import java.util.ArrayList;

public class ItemController {
    public static Cursor getItemsByList(@NonNull Integer idList, @NonNull SQLiteConnectionHelper conn) {

        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parameters = {(idList + "")};

        return db.rawQuery("SELECT * FROM " + Utilities.TABLE_ITEM + " WHERE " + Utilities.FIELD_LIST + " = ?", parameters);
    }

    public static void createNewItem(@NonNull SQLiteConnectionHelper conn, String name, Integer listId) {

        SQLiteDatabase db = conn.getWritableDatabase();

        String insert = Utilities.INSERT_ITEM + " ( '" + name + "' , 1, " + listId + ", 0 )";

        db.execSQL(insert);
        db.close();
    }

    public static void updateItemAmount(@NonNull SQLiteConnectionHelper conn, @NonNull Integer id, int newAmount) {

        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id.toString()};

        ContentValues values = new ContentValues();
        values.put(Utilities.FIELD_AMOUNT, newAmount);

        db.update(Utilities.TABLE_ITEM, values, Utilities.FIELD_ID + " = ?", parameters);

        db.close();
    }

    public static void updateItemCheck(@NonNull SQLiteConnectionHelper conn, @NonNull Integer id, Integer checked) {

        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id.toString()};

        ContentValues values = new ContentValues();
        values.put(Utilities.FIELD_CHECKED, checked);

        db.update(Utilities.TABLE_ITEM, values, Utilities.FIELD_ID + " = ? ", parameters);

        db.close();

    }

    public static void updateItemName(@NonNull SQLiteConnectionHelper conn, @NonNull Integer id, @NonNull String name ){
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id.toString()};

        ContentValues values = new ContentValues();


        values.put(Utilities.FIELD_NAME, name);

        db.close();

    }

    public static int deleteItemsById(@NonNull SQLiteConnectionHelper conn, @NonNull ArrayList<Integer> idList) {
        SQLiteDatabase db = conn.getWritableDatabase();

        int deletedItems = 0;

        for (Integer id : idList) {

            String[] parameters = {id.toString()};

            deletedItems =+ db.delete(Utilities.TABLE_ITEM, Utilities.FIELD_ID + " = ?", parameters);

        }

        db.close();
        return deletedItems;
    }

}
