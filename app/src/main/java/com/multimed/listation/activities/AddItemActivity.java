package com.multimed.listation.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.multimed.listation.R;
import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.controllers.ItemController;

public class AddItemActivity extends AppCompatActivity {

    View rootLayout;

    Button btnAdd;

    ImageButton btnExit;

    EditText inputNewItemName;

    SQLiteConnectionHelper conn;

    Integer listId;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.circle_explotion_anim, R.anim.circle_explotion_anim);

        setContentView(R.layout.activity_add_item);

        listId = getIntent().getExtras().getInt("LIST_ID");

        conn = new SQLiteConnectionHelper(this, "db_lists", null, 1);

        rootLayout = findViewById(R.id.root_layout);

        if (savedInstanceState == null) {
            rootLayout.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity();
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }

        }

        setup();
    }

    private void setup() {

        btnAdd = findViewById(R.id.btn_add_new_item);
        btnExit = findViewById(R.id.btn_new_item_exit);

        inputNewItemName = findViewById(R.id.input_newItem);

        btnAdd.setOnClickListener(this::onClick);

    }

    private void revealActivity() {
        int cx = rootLayout.getRight() - 70;
        int cy = rootLayout.getBottom() - 70;

        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
        circularReveal.setDuration(1000);

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();

    }

    private void onClick(View view) {
        if (inputNewItemName.getText().toString().isEmpty()) {
            inputNewItemName.setHint("Nombre vacio");
        } else {
            ItemController.createNewItem(conn, inputNewItemName.getText().toString(), listId);
            Bundle bundle = new Bundle();
            bundle.putInt("LIST_ID", listId);
            startActivity(new Intent(AddItemActivity.this, ListActivity.class).putExtras(bundle));
        }
    }
}