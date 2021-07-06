package com.example.androidquicktrackmeet;


import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditEventListAdapter extends ArrayAdapter<Athlete> {

    private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;

    private EditEventListAdapter adapter = this;
    private ArrayList<Athlete> athletes;
    private String event;
    private Meet meet;


    // private final Integer[] imgid;

    public EditEventListAdapter(Activity context, ArrayList<Athlete> athletes, String event, Meet meet) {
        super(context, R.layout.custom_editeventlist, athletes);
        // TODO Auto-generated constructor stub

        this.context=context;
        // this.names=names;
        // this.grades=grades;
        //this.imgid=imgid;

        this.athletes = athletes;
        this.event = event;
        this.meet = meet;
        System.out.println(this.athletes);
        }





    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_editeventlist, null,true);
        Athlete a = athletes.get(position);
        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView points = (TextView) rowView.findViewById(R.id.points);
        EditText mark = (EditText)rowView.findViewById((R.id.editMark));
        EditText place = (EditText)rowView.findViewById(R.id.editPlace);
        //System.out.println(athletes.get(position).showEvents().size());
        for (Event e : a.showEvents()){
            //System.out.println("attemptng to set mark");
            if(e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(meet.getName())) {
                mark.setText(e.getMarkString());
                System.out.println("printing mark for " + a.getLast() + e.getMarkString());
                if (e.getPlace() != null) {
                    place.setText(("" + e.getPlace()));
                }
                if (e.getPoints() != null){
                    points.setText("" + e.getPoints());
                }
                break;
            }
        }
        mark.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //athletes.get(position).setFirst(mark.getText().toString());
                // System.out.println(athletes.get(position));
                for (Event e : a.showEvents()){
                    if(e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(meet.getName())) {
                        e.setMarkString(mark.getText().toString());
                        System.out.println("after text changed fired");
                        //athletes.get(position).updateFirebase();
                        break;
                    }
                }

                //adapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                System.out.println("text is changing");

            }
        });
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText.setText(athletes.get(position).getLast() + ", "+athletes.get(position).getFirst());

        //imageView.setImageResource(imgid[position]);
        subtitleText.setText(athletes.get(position).getSchool() + "  "+athletes.get(position).getGrade());

        return rowView;

    }



}

