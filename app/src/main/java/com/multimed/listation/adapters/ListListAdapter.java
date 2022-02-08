package com.multimed.listation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.multimed.listation.R;
import com.multimed.listation.activities.ListActivity;
import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.controllers.ListController;

public class ListListAdapter extends RecyclerView.Adapter<ListListAdapter.ListViewHolder> {

    Cursor localDataBase;

    Context context;

    SQLiteConnectionHelper conn;

    public ListListAdapter(Cursor localDataBase, Context context, SQLiteConnectionHelper conn) {
        this.localDataBase = localDataBase;
        this.context = context;
        this.conn = conn;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_item, viewGroup, false);
        return new ListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        localDataBase.moveToFirst();
        localDataBase.move(position);

        int id = position + 1;

        holder.setId(id);
        holder.getLblName().setText(localDataBase.getString(1));
        holder.getLblNumberItems().setText(ListController.getListItems(conn, id).getCount() + "");
    }

    @Override
    public int getItemCount() {
        return localDataBase.getCount();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private final TextView lblName, lblNumberItems;

        private Integer id = -1;
        private boolean selected = false;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.lblName = itemView.findViewById(R.id.lbl_list_name);
            this.lblNumberItems = itemView.findViewById(R.id.lbl_item_count);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public TextView getLblName() {
            return lblName;
        }

        public TextView getLblNumberItems() {
            return lblNumberItems;
        }

        public void setId(Integer id){this.id = id;}

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ListActivity.class);

            Bundle bundle = new Bundle();
            bundle.putInt("LIST_ID", id);

            intent.putExtras(bundle);

            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            View view = itemView.findViewById(R.id.list_background_layout);
            if (!selected) {
                setSelected(true);
                view.setBackgroundResource(R.color.selected_green);
            } else {
                setSelected(false);
                view.setBackgroundResource(R.color.white);
            }

            return true;
        }
    }
}
