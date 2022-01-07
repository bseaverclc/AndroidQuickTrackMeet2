package com.quickmeet.androidquicktrackmeet.meets;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.R;
import com.quickmeet.androidquicktrackmeet.School;

import androidx.recyclerview.widget.RecyclerView;

public class AddSchoolsToMeetAdapter extends RecyclerView.Adapter<AddSchoolsToMeetAdapter.ViewHolder>  {



    //public static ArrayList<String> selectedSchools = new ArrayList<String>();
   // public static ArrayList<Integer> selectedPositions = new ArrayList<Integer>();


    private AddSchoolsToMeetAdapter adapter = this;
    private LayoutInflater mInflater;
    private RecyclerView recyclerView;



    public AddSchoolsToMeetAdapter(){

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public AddSchoolsToMeetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.adapter_add_schools_to_meet, parent, false);

        return new AddSchoolsToMeetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddSchoolsToMeetAdapter.ViewHolder holder, int position) {
        String eString = AppData.schools.get(position).getFull();
        //System.out.println("onBind at " + position);
        holder.schoolName.setText(eString);
        holder.initials.setText(AppData.schools.get(position).getInits());

        holder.row.setBackgroundColor(Color.WHITE);
        for (School school: AddMeetActivity.selectedSchools)
        {
            if(school.getFull().equalsIgnoreCase(eString))
            {
                holder.row.setBackgroundColor(Color.GREEN);
                break;
            }

        }


//        if(selectedPositions.contains(new Integer(position))){
//            holder.row.setBackgroundColor(Color.GREEN);
//        }
//        else{
//            holder.row.setBackgroundColor(Color.WHITE);
//        }
    }

    @Override
    public int getItemCount() {

        return AppData.schools.size();
    }

    public School getItem(int id) {
        return AppData.schools.get(id);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView initials;
        TextView schoolName;
        LinearLayout row;

        public ViewHolder(View itemView){
            super(itemView);
            schoolName = (TextView)itemView.findViewById(R.id.title);
            initials = (TextView)itemView.findViewById(R.id.subtitle);
            row = itemView.findViewById(R.id.row);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {

            System.out.println("clicked");
            // if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            boolean add = true;
            School selected = AppData.schools.get(getAdapterPosition());
            for(School school : AddMeetActivity.selectedSchools)
            {
                if(selected.getFull().equalsIgnoreCase(school.getFull()))
                {
                    AddMeetActivity.selectedSchools.remove(selected);
                    add = false;
                    break;
                }
            }
            if(add)
            {
                AddMeetActivity.selectedSchools.add(selected);
            }

//            if(selectedPositions.contains(getAdapterPosition())){
//                selectedPositions.remove(new Integer(getAdapterPosition()));
//                selectedSchools.remove(adapter.getItem(getAdapterPosition()));
//
//            }
//            else {
//                selectedSchools.add(adapter.getItem(getAdapterPosition()));
//                selectedPositions.add(getAdapterPosition());
//            }

//            System.out.println("clicked on " + getAdapterPosition());
//            //view.setBackgroundColor(Color.GRAY);
            System.out.println(AddMeetActivity.selectedSchools);
//            System.out.println(selectedPositions);
            notifyDataSetChanged();

        }
    }
}
