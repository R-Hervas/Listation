package com.multimed.listation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.multimed.listation.MainActivity;
import com.multimed.listation.R;
import com.multimed.listation.activities.ListActivity;
import com.multimed.listation.connection.SQLiteConnectionHelper;
import com.multimed.listation.controllers.ListController;
import com.multimed.listation.support.MultiToolbarActivity;

import java.util.ArrayList;

public class ListListAdapter extends RecyclerView.Adapter<ListListAdapter.ListViewHolder> {

    //DataBase in the adapter
    Cursor localDataBase;

    //Selection lists
    ArrayList<Integer> selectedLists = new ArrayList<>();
    ArrayList<Integer> selectedPositions = new ArrayList<>();

    //Modes of the adapter
    private boolean selectionMode = false;
    private boolean editMode = false;

    //Context and connection of use
    Context context;
    SQLiteConnectionHelper conn;

    /**
     * Constructor
     *
     * @param localDataBase - Cursor: Data to be loaded in the adapter
     * @param context       - Context: Context of use for the adapter
     * @param conn          - SQLiteConnectionHelper to connect the db as call ListController
     */
    public ListListAdapter(Cursor localDataBase, Context context, SQLiteConnectionHelper conn) {
        this.localDataBase = localDataBase;
        this.context = context;
        this.conn = conn;
    }


    // OVERRIDE methods from RecyclerView ---------------------------------------------------------
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_item, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        localDataBase.moveToFirst();
        localDataBase.move(position);

        int id = localDataBase.getInt(0);

        holder.setId(id);
        holder.getLblName().setText(localDataBase.getString(1));
        holder.getInputName().setText(localDataBase.getString(1));
        holder.getLblNumberItems().setText(ListController.getListItems(conn, id).getCount() + "");
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return localDataBase.getCount();
    }

    //OVERRIDE methods from RecyclerView ----------------------------------------------------------

    //GETTERS n SETTERS --------------------------------------------------------------------------

    public ArrayList<Integer> getSelectedLists() {
        return selectedLists;
    }

    public ArrayList<Integer> getSelectedPositions() {
        return selectedPositions;
    }

    //GETTERS n SETTERS --------------------------------------------------------------------------

    /**
     * Cleans all selections in recycler view
     */
    public void clearSelection() {
        selectedLists.clear();
        selectedPositions.clear();
    }

    public void setSelectionMode(boolean selectionMode) {
        this.selectionMode = selectionMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final TextView lblName, lblNumberItems;
        private final EditText inputName;

        private Integer id = -1;
        private Integer position = -1;

        private boolean selected = false;


        public ListViewHolder(@NonNull View itemView) {

            super(itemView);

            this.lblName = itemView.findViewById(R.id.lbl_list_name);
            this.lblNumberItems = itemView.findViewById(R.id.lbl_item_count);
            this.inputName = itemView.findViewById(R.id.input_list_name);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public boolean updateName() {
            String newName = inputName.getText().toString();
            if (newName.equals("")){
                inputName.setHint("Nombre Vacio");
            } else if (newName.length() > 15){
                Toast.makeText(context, "Su nombre de lista es demasiado largo", Toast.LENGTH_LONG).show();
            } else {
                lblName.setText(newName);

                ListController.updateListName(conn, id, newName);

                clearSelection();
                View view = itemView.findViewById(R.id.list_background_layout);
                view.setBackgroundResource(R.color.white);
                return true;
            }
            return false;
        }

        // GETTERS n SETTERS -------------------------------------------------------------------

        public TextView getLblName() {
            return lblName;
        }

        public TextView getLblNumberItems() {
            return lblNumberItems;
        }

        public EditText getInputName() {
            return inputName;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        // GETTERS n SETTERS -------------------------------------------------------------------

        /**
         * Activates and deactivates the item view from edit mode to default
         *
         * @param editModeItemLayout - boolean with visibility of edit layout
         */
        public void setEditModeItemLayout(boolean editModeItemLayout) {
            if (editModeItemLayout) {
                lblName.setVisibility(View.INVISIBLE);
                lblNumberItems.setVisibility(View.INVISIBLE);
                inputName.setVisibility(View.VISIBLE);
                inputName.setSelection(inputName.length());
            } else {
                lblName.setVisibility(View.VISIBLE);
                lblNumberItems.setVisibility(View.VISIBLE);
                inputName.setVisibility(View.INVISIBLE);
                itemView.setBackgroundResource(R.color.white);
            }
        }

        @Override
        public void onClick(View view) {
            if (!editMode) {
                if (!selectionMode) {

                    Intent intent = new Intent(context, ListActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("LIST_ID", id);

                    intent.putExtras(bundle);

                    clearSelection();
                    context.startActivity(intent);

                } else if (!selected) {

                    selectList(id, position);

                } else if (selected) {

                    deselectList(id, position);

                    if (selectedLists.size() == 0 && context instanceof MultiToolbarActivity) {

                        setSelectionMode(false);
                        ((MultiToolbarActivity) context).changeToolbar(MultiToolbarActivity.DEFAULT_TOOLBAR);

                    }
                }
            }
        }


        @Override
        public boolean onLongClick(View v) {

            if (!selectionMode) {
                if (!selected) {
                    if (selectedLists.size() == 0 && context instanceof MultiToolbarActivity) {
                        setSelectionMode(true);
                        ((MultiToolbarActivity) context).changeToolbar(MainActivity.CUSTOM_TOOLBAR);
                    }
                    selectList(id, position);
                } else {
                    deselectList(id, position);
                }
            }

            return true;
        }

        /**
         * Selects an item from list (Includes add it from selection lists, change background, update Edit)
         *
         * @param id
         * @param position
         */
        public void selectList(int id, int position) {
            View view = itemView.findViewById(R.id.list_background_layout);

            //Add item to the selection lists
            setSelected(true);
            selectedLists.add(id);
            selectedPositions.add(position);

            //Changes background
            view.setBackgroundResource(R.color.selected_green);

            //Changes editbtn state
            if (selectedLists.size() > 1 && context instanceof MultiToolbarActivity) {
                ((MultiToolbarActivity) context).setBtnEditVisibility(View.INVISIBLE);
            }
        }

        /**
         * Deselects an item from list (Includes remove it from selection lists, change background, update Edit)
         *
         * @param id
         * @param position
         */
        public void deselectList(int id, int position) {
            View view = itemView.findViewById(R.id.list_background_layout);

            // Removes the item from the selection lists
            setSelected(false);
            selectedLists.remove((Object) id);
            selectedPositions.remove((Object) position);

            //Changes background color
            view.setBackgroundResource(R.color.white);

            //Change edit btn state
            if (selectedLists.size() == 1 && context instanceof MultiToolbarActivity) {
                ((MultiToolbarActivity) context).setBtnEditVisibility(View.VISIBLE);
            }
        }
    }
}
