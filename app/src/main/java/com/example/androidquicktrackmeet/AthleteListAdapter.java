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

public class AthleteListAdapter extends ArrayAdapter<Athlete> {

    private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;
    private ArrayList<Athlete> athletes;
    private AthleteListAdapter adapter = this;

    // private final Integer[] imgid;

    public AthleteListAdapter(Activity context, ArrayList<Athlete> athletes) {
        super(context, R.layout.custom_athleteslist, athletes);
        // TODO Auto-generated constructor stub

        this.context=context;
        // this.names=names;
        // this.grades=grades;
        //this.imgid=imgid;
        this.athletes = athletes;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_athleteslist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView subTitleText = (TextView) rowView.findViewById(R.id.subtitle);
        TextView gradeText = (TextView) rowView.findViewById(R.id.grade);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        Athlete a = athletes.get(position);
        titleText.setText(a.getLast() + ", " + a.getFirst());
        subTitleText.setText(a.getSchool());
        gradeText.setText(""+a.getGrade());


        //imageView.setImageResource(imgid[position]);


        return rowView;

    }



}

