package com.multimed.listation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.multimed.listation.R;
import com.multimed.listation.connection.SQLiteConnectionHelper;

public class ListActivity extends AppCompatActivity {

    FloatingActionButton btnCreateItem;

    RecyclerView itemRecyclerView;

    Cursor itemDataSet;

    SQLiteConnectionHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
}