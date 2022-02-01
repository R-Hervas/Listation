package com.multimed.listation.activities;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.multimed.listation.MainActivity;
import com.multimed.listation.R;
import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.controllers.ListController;

public class AddListActivity extends AppCompatActivity {
    View rootLayout;

    Button btnAdd;

    ImageButton btnExit;

    EditText inputNewListName;

    SQLiteConnectionHelper conn;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.circle_explotion_anim, R.anim.circle_explotion_anim);

        setContentView(R.layout.activity_add_list);

        conn = new SQLiteConnectionHelper(this, "db_lists", null, 1);

        rootLayout = findViewById(R.id.root_layout);

        if (savedInstanceState == null) {
            rootLayout.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()){
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

        btnAdd = findViewById(R.id.btn_add_new_list);
        btnExit = findViewById(R.id.btn_new_list_exit);
        inputNewListName = findViewById(R.id.input_newList);

        btnAdd.setOnClickListener(view -> {
            if (inputNewListName.getText().toString().isEmpty()){
                inputNewListName.setHint("Nombre vacio");
            } else {
                ListController.createNewList(conn, inputNewListName.getText().toString());
                startActivity(new Intent(AddListActivity.this, MainActivity.class));
            }
        });

        btnExit.setOnClickListener(view -> startActivity(new Intent(AddListActivity.this, MainActivity.class)));
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
}