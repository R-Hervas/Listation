package com.multimed.listation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.multimed.listation.activities.AddListActivity;
import com.multimed.listation.adapters.ListListAdapter;
import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.controllers.ListController;
import com.multimed.listation.support.MultiToolbarActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MultiToolbarActivity {

    public static final int DEFAULT_TOOLBAR = 0;
    public static final int CUSTOM_TOOLBAR = 1;

    FloatingActionButton btnCreateList;
    ImageButton btnEdit, btnDelete;

    RecyclerView listRecyclerView;

    Cursor listDataSet;

    SQLiteConnectionHelper conn;

    ListListAdapter listListAdapter;

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
        btnCreateList.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddListActivity.class)));

        listRecyclerView = findViewById(R.id.list_recycleview);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listDataSet = ListController.getAllLists(conn);

        listListAdapter = new ListListAdapter(listDataSet, this, conn);

        listRecyclerView.setAdapter(listListAdapter);

    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    public void changeToolbar(int toolbar) {
        switch (toolbar) {
            case 1:
                Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.delete_toolbar);
                setupBtnEdit();
                setupBtnDelete();
                break;
            case 0:
                Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                break;
        }

    }

    @Override
    public void setupBtnDelete() {
        btnDelete = findViewById(R.id.btn_toolbar_delete);
        btnDelete.setOnClickListener(view -> {
            for (Integer id :
                    listListAdapter.getSelectedPositions()) {
                listListAdapter.notifyItemRemoved(id);
            }

            int listsDeleted = ListController.deleteListsById(conn, listListAdapter.getSelectedLists());
            Toast.makeText(this, "Se han eliminado " + listsDeleted + " listas", Toast.LENGTH_SHORT).show();

            listListAdapter.clearSelection();
        });

    }

    @Override
    public void setupBtnEdit() {
        btnEdit = findViewById(R.id.btn_toolbar_edit);
        btnEdit.setOnClickListener(view -> ListController.updateListName(conn, listListAdapter.getSelectedLists().get(0), "Pingas"));
    }

    @Override
    public void setEditVisibility(int visibility) {
        btnEdit.setVisibility(visibility);
    }


}