package com.multimed.listation.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.multimed.listation.utilities.Utilities;

public class SQLiteConnectionHelper extends SQLiteOpenHelper {


    @RequiresApi(api = Build.VERSION_CODES.P)
    public SQLiteConnectionHelper(@Nullable Context context, @Nullable String name,@Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilities.CREATE_TABLE_LIST);
        db.execSQL(Utilities.CREATE_TABLE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Utilities.DROP_IFEXITS_LIST);
        db.execSQL(Utilities.DROP_IFEXITS_ITEM);
    }
}
