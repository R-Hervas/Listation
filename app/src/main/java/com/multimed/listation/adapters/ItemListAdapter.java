package com.multimed.listation.adapters;

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

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    Cursor localDataBase;

    Context context;

    SQLiteConnectionHelper conn;

    public ItemListAdapter(Cursor localDataBase, Context context, SQLiteConnectionHelper conn) {
        this.localDataBase = localDataBase;
        this.context = context;
        this.conn = conn;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        localDataBase.moveToFirst();
        localDataBase.move(position);

        holder.getLblName().setText(localDataBase.getString(1));
        holder.getLblAmount().setText(localDataBase.getInt(2) + "");
    }

    @Override
    public int getItemCount() {
        return localDataBase.getCount();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView lblName, lblAmount;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.lblName = itemView.findViewById(R.id.lbl_item_name);
            this.lblAmount = itemView.findViewById(R.id.lbl_item_amount);
        }

        public TextView getLblName() {
            return lblName;
        }

        public TextView getLblAmount() {
            return lblAmount;
        }
    }
}
