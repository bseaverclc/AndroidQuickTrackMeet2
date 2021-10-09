package com.example.androidquicktrackmeet.meets.themeet.meetathletes;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.meets.themeet.events.EventsListAdapter;

import java.util.ArrayList;

public class AthleteEventsFromMeetsAdapter extends ArrayAdapter<String> {

    private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;
    private final ArrayList<String> events;
    private AthleteEventsFromMeetsAdapter adapter = this;
    //private Meet meet;

    // private final Integer[] imgid;
    public AthleteEventsFromMeetsAdapter(Activity context, ArrayList<String> events) {
        super(context, R.layout.adapter_athlete_events_from_meets, events);
        // TODO Auto-generated constructor stub

        this.context=context;
        // this.names=names;
        // this.grades=grades;
        //this.imgid=imgid;
        this.events = events;
        //this.meet = meet;


    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.adapter_athlete_events_from_meets, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.eventName);
        titleText.setText(events.get(position));
        //System.out.println("position " + position + " of events with value of " + AppData.selectedMeet.getBeenScored().get(position));

        return rowView;

    }
}
