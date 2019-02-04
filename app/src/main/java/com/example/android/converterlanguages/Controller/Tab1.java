package com.example.android.converterlanguages.Controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.android.converterlanguages.Model.DBHelper;
import com.example.android.converterlanguages.R;
import com.example.android.converterlanguages.RecyclerTouchListener;

import java.util.ArrayList;

public class Tab1 extends Fragment {
    private RecyclerView mRecyclerView;
    private Tab1Adapter mAdapter;
    private DBHelper mDBHelper;
    private SwipeRefreshLayout oneSwipe;
    private ArrayList<String> values;
    private Paint p = new Paint();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v =  inflater.inflate(R.layout.tab_fragment_1, container, false);

        mRecyclerView = v.findViewById(R.id.recyclerCPlus);
        oneSwipe = v.findViewById(R.id.swipeOne);
        mDBHelper = new DBHelper(getContext());
        values = mDBHelper.getAllOne();
        mAdapter = new Tab1Adapter(values, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

     //   enableSwipe();
        oneSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                values = mDBHelper.getAllOne();
                mAdapter = new Tab1Adapter(values, getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);
                oneSwipe.setRefreshing(false);

            }
        });
        return v;
    }

//    private void enableSwipe(){
//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                final int position = viewHolder.getAdapterPosition();
//
//                if (direction == ItemTouchHelper.LEFT){
//                    final String deletedModel =  mAdapter.getItem(position);
//                    Toast.makeText(getContext(), deletedModel, Toast.LENGTH_LONG).show();
//                    final int deletedPosition = position;
//                    mDBHelper.removeOne(deletedModel);
//                    mAdapter.removeItem(position);
//                    mAdapter.notifyDataSetChanged();
//
//                    // showing snack bar with Undo option
//                    InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(mRecyclerView.getWindowToken(), 0);
//                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
//                        getActivity().getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );
//                    }
//
//                } else {
//                    final String deletedModel =  mAdapter.getItem(position);
//                    final int deletedPosition = position;
//                    mDBHelper.removeOne(deletedModel);
//                    mAdapter.removeItem(position);
//                    mAdapter.notifyDataSetChanged();
//                    InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(mRecyclerView.getWindowToken(), 0);
//                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
//                        getActivity().getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );
//                    }
//                }
//            }
//
//            @Override
//            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//
//                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
//                    View itemView = viewHolder.itemView;
//                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
//                    float width = height / 3;
//                    if(dX > 0){
//                        p.setColor(Color.parseColor("#00574b"));
//                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
//                        c.drawRect(background,p);
//                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
//                        Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.ic_delete);
//                        Bitmap icon2 = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//                        Canvas canvas = new Canvas(icon2);
//                        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//                        drawable.draw(canvas);
//                        c.drawBitmap(icon2, null, icon_dest, p);
//                    } else {
//                        p.setColor(Color.parseColor("#00574b"));
//                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
//                        c.drawRect(background,p);
//                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
//                        Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.ic_delete);
//                        Bitmap icon2 = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//                        Canvas canvas = new Canvas(icon2);
//                        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//                        drawable.draw(canvas);
//                        c.drawBitmap(icon2, null, icon_dest, p);
//
//                    }
//                }
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            }
//        };
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(mRecyclerView);
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
       if(id == R.id.itemDelete) {
       } else if(id == R.id.itemEdit) {
       }
        return super.onContextItemSelected(item);

}
}
