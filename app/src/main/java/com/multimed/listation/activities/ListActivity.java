package com.multimed.listation.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.multimed.listation.MainActivity;
import com.multimed.listation.R;
import com.multimed.listation.adapters.ItemListAdapter;
import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.controllers.ItemController;
import com.multimed.listation.support.MultiToolbarActivity;

public class ListActivity extends AppCompatActivity implements MultiToolbarActivity {

    FloatingActionButton btnCreateItem;
    ImageButton btnEdit;

    RecyclerView itemRecyclerView;

    Cursor itemDataSet;

    SQLiteConnectionHelper conn;

    Integer idList;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        idList = getIntent().getExtras().getInt("LIST_ID");

        setup();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void setup() {

        conn = new SQLiteConnectionHelper(this, "db_lists", null, 1);


        btnCreateItem = findViewById(R.id.btn_item_add);
        btnCreateItem.setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, AddItemActivity.class);

            Bundle bundle = new Bundle();
            bundle.putInt("LIST_ID", idList);
            intent.putExtras(bundle);

            startActivity(intent);
        });

        itemRecyclerView = findViewById(R.id.item_recyclerview);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemDataSet = ItemController.getItemsByList(idList, conn);

        ItemListAdapter itemListAdapter = new ItemListAdapter(itemDataSet, this, conn);

        itemRecyclerView.setAdapter(itemListAdapter);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListActivity.this, MainActivity.class));
    }

    @Override
    public void changeToolbar(int toolbar) {
        switch(toolbar){
            case 1:
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.delete_toolbar);
                btnEdit = findViewById(R.id.btn_toolbar_edit);
                break;
            case 0:
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                break;
        }
    }

    @Override
    public void setEditVisibility(int visibility) {
        btnEdit.setVisibility(visibility);
    }
}