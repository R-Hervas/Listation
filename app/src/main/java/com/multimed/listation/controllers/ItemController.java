package com.multimed.listation.controllers;

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

        String insert = Utilities.INSERT_ITEM + " ( '" + name + "' , 1, " + listId + " )";

        db.execSQL(insert);
        db.close();
    }
}
