package com.multimed.listation.utilities;

public class Utilities {

    //Constants tables
    public static final String TABLE_LIST = "list";
    public static final String TABLE_ITEM = "item";

    //Constants fields
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_LIST =  "list";

    //Table creation statements
    public static final String CREATE_TABLE_LIST =
            "CREATE TABLE " + TABLE_LIST + " (" + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIELD_NAME + " TEXT) ";
    public static final String CREATE_TABLE_ITEM =
            "CREATE TABLE " + TABLE_ITEM + " (" + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIELD_NAME + " TEXT, " +
            FIELD_AMOUNT + " INTEGER, " + FIELD_LIST + " INTEGER)";


    //Table drop if exits statements
    public static final String DROP_IFEXITS_LIST = " DROP TABLE IF EXISTS " + TABLE_LIST;
    public static final String DROP_IFEXITS_ITEM = " DROP TABLE IF EXISTS " + TABLE_ITEM;

    //Insert register statement
    public static final String INSERT_LIST = "INSERT INTO " + TABLE_LIST + "( " + FIELD_NAME + " ) VALUES";
    public static final String INSERT_ITEM = "INSERT INTO " + TABLE_ITEM + "( " + FIELD_NAME + ", " +  FIELD_AMOUNT + ", "  + FIELD_LIST + " ) VALUES";
}
