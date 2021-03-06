package com.multimed.listation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.multimed.listation.MainActivity;
import com.multimed.listation.R;
import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.controllers.ItemController;
import com.multimed.listation.support.MultiToolbarActivity;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    Cursor localDataBase;

    Context context;

    SQLiteConnectionHelper conn;

    ArrayList<Integer> selectedItems = new ArrayList<>();
    ArrayList<Integer> selectedPositions = new ArrayList<>();
    boolean selectionMode = false;
    boolean editMode;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        localDataBase.moveToFirst();
        localDataBase.move(position);

        holder.setId(localDataBase.getInt(0));
        holder.getLblName().setText(localDataBase.getString(1));
        holder.getInputName().setText(localDataBase.getString(1));
        holder.getLblAmount().setText(localDataBase.getInt(2) + "");
        holder.getCheckBox().setChecked(localDataBase.getInt(4) == 1);
        holder.setPosition(position);

    }

    @Override
    public int getItemCount() {
        return localDataBase.getCount();
    }

    public ArrayList<Integer> getSelectedItems() {
        return selectedItems;
    }

    public ArrayList<Integer> getSelectedPositions() {
        return selectedPositions;
    }

    public void clearSelection() {
        selectedItems.clear();
        selectedPositions.clear();
    }

    public void setSelectionMode(boolean b) {
        selectionMode = b;
    }

    public void setEditMode(boolean b) {
        editMode = b;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TextWatcher, View.OnLongClickListener {

        private final TextView lblName;
        private final TextView lblAmount;

        private final ImageButton btnLower;
        private final ImageButton btnHighUp;

        private final EditText inputName;

        private final CheckBox checkBox;

        private Integer id = -1;
        private boolean selected = false;
        private int position = -1;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.checkBox = itemView.findViewById(R.id.chkd_item);
            this.lblName = itemView.findViewById(R.id.lbl_item_name);
            this.lblAmount = itemView.findViewById(R.id.lbl_item_amount);
            this.btnHighUp = itemView.findViewById(R.id.btn_item_higher);
            this.btnLower = itemView.findViewById(R.id.btn_item_lower);
            this.inputName = itemView.findViewById(R.id.input_item_name);

            checkBox.setOnClickListener(this);

            btnLower.setOnClickListener(this);
            btnHighUp.setOnClickListener(this);

            lblAmount.addTextChangedListener(this);

            itemView.setOnLongClickListener(this);


        }

        public TextView getLblName() {
            return lblName;
        }

        public TextView getLblAmount() {
            return lblAmount;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public EditText getInputName() {
            return inputName;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View view) {
                int currentAmount = Integer.parseInt(lblAmount.getText().toString());

                switch (view.getId()) {
                    case (R.id.btn_item_lower):
                        ItemController.updateItemAmount(conn, id, --currentAmount);
                        lblAmount.setText(currentAmount + "");
                        break;
                    case (R.id.btn_item_higher):
                        ItemController.updateItemAmount(conn, id, ++currentAmount);
                        lblAmount.setText(currentAmount + "");
                        break;
                    case (R.id.chkd_item):
                        System.out.println("Traduzco esto - " + (checkBox.isChecked() ? 1 : 0));

                        int checked = checkBox.isChecked() ? 1 : 0;
                        ItemController.updateItemCheck(conn, id, checked);
                        break;

                }

        }

        public void setId(int id) {
            this.id = id;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }



        @Override
        public void onTextChanged(CharSequence value, int i, int i1, int i2) {
            if (Integer.parseInt(value.toString()) == 1) {
                btnLower.setVisibility(View.INVISIBLE);
            } else {
                btnLower.setVisibility(View.VISIBLE);
            }
        }


        //Not used method needed to be overriden
        @Override
        public void afterTextChanged(Editable editable) {

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public boolean onLongClick(View v) {
            if (!editMode) {
                if (!selected) {
                    if (selectedItems.size() == 0) {
                        selectionMode = true;
                        if (context instanceof MultiToolbarActivity) {
                            ((MultiToolbarActivity) context).changeToolbar(MainActivity.CUSTOM_TOOLBAR);
                        }

                    }
                    selectItem(id);

                } else {
                    deselectItem(id);
                    if (selectedItems.size() == 0) {
                        selectionMode = false;
                        if (context instanceof MultiToolbarActivity) {
                            ((MultiToolbarActivity) context).changeToolbar(MultiToolbarActivity.DEFAULT_TOOLBAR);
                        }
                    }
                }
            }

            return true;
        }

        public void selectItem(int id) {
            View view = itemView.findViewById(R.id.item_background_layout);
            setSelected(true);
            selectedItems.add(id);
            selectedPositions.add(position);
            setSelectModeItemLayout(true, view);
            if (selectedItems.size() > 1 && context instanceof MultiToolbarActivity){
                ((MultiToolbarActivity) context).setBtnEditVisibility(View.INVISIBLE);
            }

        }

        public void deselectItem(int id) {
            View view = itemView.findViewById(R.id.item_background_layout);
            setSelected(false);
            selectedItems.remove((Object) id);
            selectedPositions.remove((Object) position);
            setSelectModeItemLayout(false, view);
            if (selectedItems.size() == 1 && context instanceof MultiToolbarActivity){
                ((MultiToolbarActivity) context).setBtnEditVisibility(View.VISIBLE);
            }
        }

        /**
         * Changes elements in the item view between editMode and DefaultMode
         * @param selected - {true}
         */
        public void setSelectModeItemLayout(boolean selected, View view) {
            int visible = !selected ? View.VISIBLE : View.INVISIBLE;
            view.setBackgroundResource(selected? R.color.selected_green : R.color.white);
            checkBox.setVisibility(visible);
            if (Integer.parseInt(lblAmount.getText().toString()) > 1){btnLower.setVisibility(visible);}
            lblAmount.setVisibility(visible);
            btnHighUp.setVisibility(visible);
        }

        public void setEditModeItemLayout(boolean editModeItemLayout) {
            if (editModeItemLayout){
                lblName.setVisibility(View.INVISIBLE);
                inputName.setVisibility(View.VISIBLE);
                inputName.setSelection(inputName.length());
            } else {
                lblName.setVisibility(View.VISIBLE);
                inputName.setVisibility(View.INVISIBLE);
                btnLower.setVisibility(View.VISIBLE);
                btnHighUp.setVisibility(View.VISIBLE);
                checkBox.setVisibility(View.VISIBLE);
                lblAmount.setVisibility(View.VISIBLE);
                itemView.setBackgroundResource(R.color.white);

            }
        }

        public boolean updateName() {
            String newName = inputName.getText().toString();
            if (newName.equals("")){
                inputName.setHint(R.string.Empty_Name);
            } else if (newName.length() > 15){
                Toast.makeText(context, R.string.tooLrg_Name_warning_item, Toast.LENGTH_LONG).show();
            } else {
                lblName.setText(newName);

                ItemController.updateItemName(conn, id, newName);

                clearSelection();
                View view = itemView.findViewById(R.id.item_background_layout);
                view.setBackgroundResource(R.color.white);
                return true;
            }
            return false;
        }
    }
}
