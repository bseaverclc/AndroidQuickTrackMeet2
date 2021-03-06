package com.quickmeet.androidquicktrackmeet.meets.themeet.events;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import android.widget.ImageView;
import android.widget.TextView;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.R;

import java.util.ArrayList;

public class EventsListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;
    private final ArrayList<String> events;
    private EventsListAdapter adapter = this;
    //private Meet meet;

    // private final Integer[] imgid;

    public EventsListAdapter(Activity context, ArrayList<String> events) {
        super(context, R.layout.custom_eventslist, events);
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
        View rowView=inflater.inflate(R.layout.custom_eventslist, null,true);





        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        //TextView subTitleText = (TextView) rowView.findViewById(R.id.subtitle);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);


        titleText.setText(events.get(position));
        System.out.println("position " + position + " of events with value of " + AppData.selectedMeet.getBeenScored().get(position));

        if(AppData.selectedMeet.getBeenScored().get(position)== true){
            titleText.setBackgroundColor(Color.GREEN);
        }
        else if (position%2 == 1){titleText.setBackgroundColor(Color.LTGRAY);}
        else{titleText.setBackgroundColor(Color.WHITE);}
        //subTitleText.setText(meets.get(position).getName());

        //imageView.setImageResource(imgid[position]);


        return rowView;

    }



}

