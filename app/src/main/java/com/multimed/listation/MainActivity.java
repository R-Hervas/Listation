package com.multimed.listation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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

    //User Interface Element
    FloatingActionButton btnCreateList;
    ImageButton btnEdit, btnDelete;

    RecyclerView listRecyclerView;
    ListListAdapter listListAdapter;

    //Dataset
    Cursor listDataSet;

    //Connection Helper
    SQLiteConnectionHelper conn;

    //MODES
    boolean editMode = false;

    /**
     * On Create
     *
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
    }

    /**
     * Setup all the elements in UI, stablish connection with the db and load all data in the recyclerview
     */
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

    /**
     * On Back Pressed terminates the whole activity
     *
     * @implNote MultiToolbarActivity
     */
    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    /**
     * Change the toolbar
     *
     * @param toolbar - DEFAULT_TOOLBAR: 0, CUSTOM_TOOLBAR: 1
     */
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

    /**
     * Prepare btnDelete for deleting one or more lists from the recylcerView and the db
     */
    @Override
    public void setupBtnDelete() {
        btnDelete = findViewById(R.id.btn_toolbar_delete);
        btnDelete.setOnClickListener(view -> {


            int listsDeleted = ListController.deleteListsById(conn, listListAdapter.getSelectedLists());
            Toast.makeText(this, "Se han eliminado " + listsDeleted + " listas", Toast.LENGTH_SHORT).show();

            listDataSet = null;
            listDataSet = ListController.getAllLists(conn);
            listListAdapter = new ListListAdapter(listDataSet, this, conn);
            listRecyclerView.setAdapter(listListAdapter);

            listListAdapter.clearSelection();
        });

    }

    @Override
    public void setBtnEditVisibility(int visibility) {
        btnEdit.setVisibility(visibility);
    }

    /**
     * Prepare the btnEdit for activating the editing mode
     */
    @Override
    public void setupBtnEdit() {
        btnEdit = findViewById(R.id.btn_toolbar_edit);

        btnEdit.setOnClickListener(view -> {
            ListListAdapter.ListViewHolder holder = ((ListListAdapter.ListViewHolder) listRecyclerView.findViewHolderForAdapterPosition(listListAdapter.getSelectedPositions().get(0)));
            if (!editMode) {
                holder.setEditModeItemLayout(true);
                editMode = true;
                listListAdapter.setSelectionMode(false); //Revisar esta linea
            } else {
                holder.setEditModeItemLayout(false);
                editMode = false;
                listListAdapter.clearSelection(); //Limpiar la seleccion
            }
        });

    }

}