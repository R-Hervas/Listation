package com.multimed.listation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.multimed.listation.adapters.ListListAdapter;
import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.controllers.ListController;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnCreateList;

    RecyclerView listRecyclerView;

    Cursor listDataSet;

    SQLiteConnectionHelper conn;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void setup() {

        conn = new SQLiteConnectionHelper(this, "db_lists", null, 1);

        btnCreateList = findViewById(R.id.btn_list_add);

        listRecyclerView = findViewById(R.id.list_recycleview);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listDataSet = ListController.getAllLists(conn);

        ListListAdapter listListAdapter = new ListListAdapter(listDataSet, this);

        listRecyclerView.setAdapter(listListAdapter);

    }

}