package com.example.androidquicktrackmeet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private EditEventListAdapter mAdapter;

    public SwipeToDeleteCallback(EditEventListAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
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
        System.out.println("OnMove being called");
        int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            mAdapter.moveItem(from,to);
            mAdapter.notifyItemMoved(from,to);

        //        val adapter = recyclerView.adapter as MainRecyclerViewAdapter
//        val from = viewHolder.adapterPosition
//        val to = target.adapterPosition
//        // 2. Update the backing model. Custom implementation in
//        //    MainRecyclerViewAdapter. You need to implement
//        //    reordering of the backing model inside the method.
//        adapter.moveItem(from, to)
//        // 3. Tell adapter to render the model update.
//        adapter.notifyItemMoved(from, to)

        return true;
    }
}
