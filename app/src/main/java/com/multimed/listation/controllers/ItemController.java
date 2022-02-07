package com.multimed.listation.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.utilities.Utilities;

public class ItemController {
    public static Cursor getItemsByList(Integer idList, SQLiteConnectionHelper conn) {

        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parameters = {(idList+"")};

        return db.rawQuery("SELECT * FROM " + Utilities.TABLE_ITEM + " WHERE " + Utilities.FIELD_LIST + " = ?", parameters);
    }

    public static void createNewItem(SQLiteConnectionHelper conn, String name, Integer listId) {

        SQLiteDatabase db = conn.getWritableDatabase();

        String insert = Utilities.INSERT_ITEM + " ( '" + name + "' , 1, " + listId + ", 0 )";

        db.execSQL(insert);
        db.close();
    }

    public static void updateItemAmount(SQLiteConnectionHelper conn, Integer id, int newAmount) {

        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id.toString()};

        ContentValues values = new ContentValues();
        values.put(Utilities.FIELD_AMOUNT, newAmount);

        db.update(Utilities.TABLE_ITEM, values, Utilities.FIELD_ID + " = ?", parameters);

        db.close();
    }

    public static void updateItemCheck(SQLiteConnectionHelper conn, Integer id, Integer checked) {

        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id.toString()};

        ContentValues values = new ContentValues();

        System.out.println(" GUARDO ESTO : " + checked);
        values.put(Utilities.FIELD_CHECKED, checked);
        int i = db.update(Utilities.TABLE_ITEM, values, Utilities.FIELD_ID + " = ? ", parameters);
        System.out.println("He modificado estas lineas" + i);

        db.close();

    }
}
