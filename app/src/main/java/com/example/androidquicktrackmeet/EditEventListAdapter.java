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
    private final ArrayList<Athlete> athletes;
    private EditEventListAdapter adapter = this;

    // private final Integer[] imgid;

    public EditEventListAdapter(Activity context, ArrayList<Athlete> athletes) {
        super(context, R.layout.custom_schoolslist, athletes);
        // TODO Auto-generated constructor stub

        this.context=context;
        // this.names=names;
        // this.grades=grades;
        //this.imgid=imgid;
        this.athletes = AppData.allAthletes;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_schoolslist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        EditText mark = (EditText)rowView.findViewById((R.id.editMark));
        System.out.println(athletes.get(position).showEvents().size());
        for (Event e : athletes.get(position).showEvents()){
            System.out.println("attemptng to set mark");
            mark.setText(e.getMarkString());
        }
        mark.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //athletes.get(position).setFirst(mark.getText().toString());
                // System.out.println(athletes.get(position));
                for (Event e : athletes.get(position).showEvents()){
                    e.setMarkString(mark.getText().toString());
                    //athletes.get(position).updateFirebase();
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

        titleText.setText(athletes.get(position).getFirst());

        //imageView.setImageResource(imgid[position]);
        subtitleText.setText(""+athletes.get(position).getGrade());

        return rowView;

    }



}

