package com.example.androidquicktrackmeet.meets.themeet.meetathletes;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Athlete;
import com.example.androidquicktrackmeet.Event;
import com.example.androidquicktrackmeet.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddEventsToAthleteAdapter extends RecyclerView.Adapter<AddEventsToAthleteAdapter.ViewHolder> {

    private Athlete selectedAthlete;
    private ArrayList<Event> events;
    public static ArrayList<String> selectedEvents = new ArrayList<String>();
    public static ArrayList<Integer> selectedPositions = new ArrayList<Integer>();


    private AddEventsToAthleteAdapter adapter = this;
    private LayoutInflater mInflater;
    private RecyclerView recyclerView;



    public AddEventsToAthleteAdapter( Athlete selectedAthlete, ArrayList<Event> events){
        this.selectedAthlete = selectedAthlete;
        this.events = events;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public AddEventsToAthleteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.adapter_add_events_to_athlete, parent, false);

        return new AddEventsToAthleteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddEventsToAthleteAdapter.ViewHolder holder, int position) {
          String eString = AppData.selectedMeet.getEvents().get(position);
          //System.out.println("onBind at " + position);
           holder.eventName.setText(eString);


        if(selectedPositions.contains(new Integer(position))){
            holder.row.setBackgroundColor(Color.GREEN);
        }
        else{
            holder.row.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {

        return AppData.selectedMeet.getEvents().size();
    }

    public String getItem(int id) {
        return AppData.selectedMeet.getEvents().get(id);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView eventName;
        LinearLayout row;

        public ViewHolder(View itemView){
            super(itemView);
            eventName = (TextView)itemView.findViewById(R.id.eventName3);
            row = itemView.findViewById(R.id.row3);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {

            System.out.println("clicked");
            // if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            if(selectedPositions.contains(getAdapterPosition())){
                selectedPositions.remove(new Integer(getAdapterPosition()));
                selectedEvents.remove(adapter.getItem(getAdapterPosition()));

            }
            else {
                selectedEvents.add(adapter.getItem(getAdapterPosition()));
                selectedPositions.add(getAdapterPosition());
            }

//            System.out.println("clicked on " + getAdapterPosition());
//            //view.setBackgroundColor(Color.GRAY);
           System.out.println(selectedEvents);
//            System.out.println(selectedPositions);
            notifyDataSetChanged();

        }
    }
}
