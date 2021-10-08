package com.example.androidquicktrackmeet.meets.themeet.events.theevent;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.Meet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {
    private List<Section> sectionList;
    private Context context;
    //private EditEventListAdapter itemAdapter;
    public SectionAdapter(Context context, List<Section> sections) {
        sectionList = sections;
        this.context = context;
    }
    @NonNull
    @Override
    public SectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_section, parent, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Section section = sectionList.get(position);
        holder.bind(section);
    }
    @Override
    public int getItemCount() {
        return sectionList.size();
    }







    // Inner Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionName;
        private RecyclerView itemRecyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            System.out.println("View Holder Constructor being called");
            sectionName = itemView.findViewById(R.id.section_item_text_view);
            itemRecyclerView = itemView.findViewById(R.id.item_recycler_view);
        }
        public void bind(Section section) {
            System.out.println("Section onBind being called");
            sectionName.setText(section.getHeatNumber());
            sectionName.setTextColor(Color.rgb(255,165,0));
            // RecyclerView for items
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            itemRecyclerView.setLayoutManager(linearLayoutManager);
           EditEventListAdapter itemAdapter = new EditEventListAdapter(section.getAthletes(), section.getEvent(),section.getProcessButton(), sectionName);
            //itemAdapter = new ItemAdapter(section.getAllItemsInSection());
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(itemRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
            //Drawable mDivider = ContextCompat.getDrawable(itemRecyclerView.getContext(), R.drawable.divider);
            //dividerItemDecoration.setDrawable(mDivider);
            itemRecyclerView.addItemDecoration(dividerItemDecoration);


            itemRecyclerView.setAdapter(itemAdapter);

            // attach delete option to recyclerview
            if(Meet.canCoach){
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(itemAdapter));
                itemTouchHelper.attachToRecyclerView(itemRecyclerView);
            }

        }


    }
//    public void moveItem(int toSectionPosition, int fromSectionPosition) {
//        List<String> toItemsInSection = sectionList.get(toSectionPosition).getAllItemsInSection();
//        List<String> fromItemsInSection = sectionList.get(fromSectionPosition).getAllItemsInSection();
//        if (fromItemsInSection.size() > 3) {
//            toItemsInSection.add(fromItemsInSection.get(3));
//            fromItemsInSection.remove(3);
//            // update adapter for items in a section
//            itemAdapter.notifyDataSetChanged();
//            // update adapter for sections
//            this.notifyDataSetChanged();
//        }
//    }
}
