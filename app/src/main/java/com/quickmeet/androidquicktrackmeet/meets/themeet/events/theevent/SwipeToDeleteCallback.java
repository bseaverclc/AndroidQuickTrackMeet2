package com.quickmeet.androidquicktrackmeet.meets.themeet.events.theevent;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private EditEventListAdapter mAdapter;
   // private SectionAdapter mAdapter;

    public SwipeToDeleteCallback(EditEventListAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT );
        mAdapter = adapter;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if(mAdapter.deleteItem(position)){
            System.out.println("deleteItem returned true");

        }
        else{
            mAdapter.notifyDataSetChanged();
        }

    }
//
    @Override
    public boolean onMove(RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//return false;
//        System.out.println("OnMove being called");
//        int from = viewHolder.getAdapterPosition();
//            int to = target.getAdapterPosition();
//            mAdapter.moveItem(from,to);
//            mAdapter.notifyItemMoved(from,to);
//
        return true;
    }

//    @Override
//    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        super.onSelectedChanged(viewHolder, actionState);
//
//        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
//            viewHolder.itemView.setAlpha(0.5f);
//            //viewHolder?.itemView?.alpha = 0.5f
//        }
//    }
//
//    @Override
//    public void clearView(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder) {
//        super.clearView(recyclerView, viewHolder);
//       // viewHolder.itemView.setAlpha((1.0f));
//    }
}
