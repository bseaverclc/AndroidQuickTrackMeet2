package com.example.androidquicktrackmeet.meets.themeet.meetathletes;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Athlete;
import com.example.androidquicktrackmeet.Event;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.meets.themeet.events.EventsListAdapter;
import com.example.androidquicktrackmeet.meets.themeet.events.theevent.EditEventListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class AthleteEventsFromMeetsAdapter extends RecyclerView.Adapter<AthleteEventsFromMeetsAdapter.ViewHolder>  {



    private Athlete athlete;
    // private Meet meet;
    private LayoutInflater mInflater;

    private RecyclerView recyclerView;

    //private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;
    private final ArrayList<Event> events;
    private AthleteEventsFromMeetsAdapter adapter = this;
    //private Meet meet;

    // private final Integer[] imgid;
    public AthleteEventsFromMeetsAdapter(ArrayList<Event> events, Athlete athlete) {
       // super(context, R.layout.adapter_athlete_events_from_meets, events);

        //this.mInflater = LayoutInflater.from(context);
        //this.context=context;
        // this.names=names;
        // this.grades=grades;
        //this.imgid=imgid;
        this.events = events;
        this.athlete = athlete;
        //this.meet = meet;
        //this.context = context;


    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    // inflates the row layout from xml when needed
    @Override
    public AthleteEventsFromMeetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.adapter_athlete_events_from_meets, parent, false);

        return new AthleteEventsFromMeetsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AthleteEventsFromMeetsAdapter.ViewHolder holder, int position) {
        System.out.println("Onbind being called for " + events.get(position).getName());
        Event e = events.get(position);
        holder.eventName.setText(e.getName());
        holder.meetName.setText(e.getMeetName());
        if (e.getMarkString() != "") {
            holder.eventMark.setText(e.getMarkString());
        } else {
            holder.eventMark.setText("");
        }
        if (e.getPlace() != null) {
                holder.place.setText("place: " + e.getPlace());
            }
        else {
            holder.place.setText("");
        }
    }

    @Override
    public int getItemCount() {
        System.out.println(events.size());
        return events.size();

    }





public class ViewHolder extends RecyclerView.ViewHolder {

    TextView eventName;
    TextView meetName;
    TextView eventMark;
    TextView place;
    LinearLayoutCompat row;

    public ViewHolder(View itemView)
    {
        super(itemView);
        eventName = (TextView) itemView.findViewById(R.id.eventName);
        meetName = (TextView) itemView.findViewById(R.id.meetName);
        eventMark = (TextView) itemView.findViewById(R.id.eventMark);
        place = (TextView) itemView.findViewById(R.id.place);
        row = itemView.findViewById(R.id.row);
    }



}





//    public View getView(int position, View view, ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.adapter_athlete_events_from_meets, null,true);
//
//        TextView titleText = (TextView) rowView.findViewById(R.id.eventName);
//        titleText.setText(events.get(position));
//        //System.out.println("position " + position + " of events with value of " + AppData.selectedMeet.getBeenScored().get(position));
//
//        return rowView;
//
//    }
}
