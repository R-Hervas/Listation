package com.multimed.listation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.multimed.listation.R;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_item,viewGroup, false);
        return new ListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        localDataBase.moveToFirst();
        localDataBase.move(position);

        holder.getLblName().setText(localDataBase.getString(1));
        holder.getLblNumberItems().setText(ListController.getListItems(conn, localDataBase.getInt(0)).getCount() + "");
    }

    @Override
    public int getItemCount() {
        return localDataBase.getCount();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{

        private final TextView lblName, lblNumberItems;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.lblName = itemView.findViewById(R.id.lbl_list_name);
            this.lblNumberItems = itemView.findViewById(R.id.lbl_item_count);
        }

        public TextView getLblName() {
            return lblName;
        }

        public TextView getLblNumberItems() {
            return lblNumberItems;
        }
    }
}