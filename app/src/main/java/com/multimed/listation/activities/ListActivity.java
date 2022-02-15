package com.multimed.listation.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.multimed.listation.MainActivity;
import com.multimed.listation.R;
import com.multimed.listation.adapters.ItemListAdapter;
import com.multimed.listation.adapters.ListListAdapter;
import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.controllers.ItemController;
import com.multimed.listation.controllers.ListController;
import com.multimed.listation.support.MultiToolbarActivity;

import java.util.Objects;

public class ListActivity extends AppCompatActivity implements MultiToolbarActivity {

    FloatingActionButton btnCreateItem;
    ImageButton btnEdit, btnDelete, btnConfirm;

    RecyclerView itemRecyclerView;

    Cursor itemDataSet;

    ItemListAdapter itemListAdapter;

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

        itemListAdapter = new ItemListAdapter(itemDataSet, this, conn);

        itemRecyclerView.setAdapter(itemListAdapter);

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListActivity.this, MainActivity.class));
    }

    @Override
    public void setupBtnDelete() {
        btnDelete = findViewById(R.id.btn_toolbar_delete);
        btnDelete .setOnClickListener(view -> {
            int itemsDeleted  = ItemController.deleteItemsById(conn, itemListAdapter.getSelectedItems());
            Toast.makeText(this, "Se han eliminado " + itemsDeleted + " productos", Toast.LENGTH_SHORT).show();

            itemDataSet = null;
            itemDataSet = ItemController.getItemsByList(idList, conn);
            itemListAdapter = new ItemListAdapter(itemDataSet, this, conn);
            itemRecyclerView.setAdapter(itemListAdapter);



            itemListAdapter.clearSelection();
        });
    }

    @Override
    public void setupBtnEdit() {
        btnEdit = findViewById(R.id.btn_toolbar_edit);
        btnEdit.setOnClickListener(view -> ListController.updateListName(conn, itemListAdapter.getSelectedItems().get(0), "Pingas"));
    }

    @Override
    public void setupBtnConfirm() {
        btnConfirm = findViewById(R.id.btn_toolbar_update);
        btnConfirm.setOnClickListener(view -> {
            ListListAdapter.ListViewHolder holder = ((ListListAdapter.ListViewHolder) itemRecyclerView.findViewHolderForAdapterPosition(itemListAdapter.getSelectedPositions().get(0)));
            holder.updateName();
            Toast.makeText(this, "List Updated", Toast.LENGTH_SHORT).show();
            holder.setEditModeItemLayout(false);
            itemListAdapter.setSelectionMode(false);
            itemListAdapter.setEditMode(false);
            itemListAdapter.clearSelection();
            changeToolbar(MultiToolbarActivity.DEFAULT_TOOLBAR);
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
    }

    @Override
    public void changeToolbar(int toolbar) {
        switch(toolbar){
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
    public void setBtnEditVisibility(int visibility) {
        btnEdit.setVisibility(visibility);
    }
}