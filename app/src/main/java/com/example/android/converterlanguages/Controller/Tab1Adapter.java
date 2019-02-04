package com.example.android.converterlanguages.Controller;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.converterlanguages.Model.DBHelper;
import com.example.android.converterlanguages.R;
import java.util.ArrayList;

public class Tab1Adapter extends RecyclerView.Adapter<Tab1Adapter.MyViewHolder> {
    private ArrayList<String> mList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView nameOne;

        public MyViewHolder(View view) {
            super(view);
            nameOne = view.findViewById(R.id.txtValue);
            view.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem Edit = contextMenu.add(Menu.NONE, 1, 1, "Редактировать");
            MenuItem Delete = contextMenu.add(Menu.NONE, 2, 2, "Удалить");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
              final  String deletedModel = mList.get(getAdapterPosition());
               final DBHelper mDBHelper = new DBHelper(mContext);
                LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

              //  DBHandler dbHandler = new DBHandler(ctx);
              //  List<WishMen> data = dbHandler.getWishmen();

                switch (item.getItemId()) {
                    case 1:
                        AlertDialog.Builder builderUpdateOne = new AlertDialog.Builder(mContext);
                        View mViewUpdateOne = li.inflate(R.layout.dialog_update_one, null);
                        final EditText updateEdit = mViewUpdateOne.findViewById(R.id.inputEditOne);
                        updateEdit.setText(deletedModel, TextView.BufferType.EDITABLE);
                        Button okUpdateOne = mViewUpdateOne.findViewById(R.id.btnOkUpdate);
                        Button cancelUpdateOne = mViewUpdateOne.findViewById(R.id.btnCancelUpdate);
                        builderUpdateOne.setView(mViewUpdateOne);
                        final AlertDialog dialogUpdateOne = builderUpdateOne.create();
                        dialogUpdateOne.show();
                        okUpdateOne.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String newValue = updateEdit.getText().toString();
                                mDBHelper.updateCPlus(newValue, deletedModel);
                                dialogUpdateOne.dismiss();

                            }
                        });
                        cancelUpdateOne.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogUpdateOne.dismiss();
                            }
                        });
                        break;

                    case 2:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View mView = li.inflate(R.layout.dialog_confirm, null);
                        Button ok = mView.findViewById(R.id.btnOkConfirm);
                        Button cancel = mView.findViewById(R.id.btnCancelConfirm);
                        builder.setView(mView);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDBHelper.removeOne(deletedModel);
//                                this.removeItem(getAdapterPosition());
//                                this.notifyDataSetChanged();
                                dialog.dismiss();

                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        break;
                }
                return true;
            }
        };


    }



    public Tab1Adapter(ArrayList<String> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Tab1Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Tab1Adapter.MyViewHolder holder, int position) {
        String meet = mList.get(position);
        holder.nameOne.setText(meet);
    }

    public String getItem (int pos) {
        return mList.get(pos);

    }


    public void restoreItem(String model, int position) {
        mList.add(position, model);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mList.size());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size());
    }


    public void addAll(ArrayList<String> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


}
