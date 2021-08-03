package com.example.androidquicktrackmeet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private EditEventListAdapter mAdapter;

    public SwipeToDeleteCallback(EditEventListAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        int position = viewHolder.getAdapterPosition();

        if(mAdapter.deleteItem(position))
            {return;}
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }
}
