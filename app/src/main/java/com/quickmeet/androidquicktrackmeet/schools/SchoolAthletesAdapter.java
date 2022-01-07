package com.quickmeet.androidquicktrackmeet.schools;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.quickmeet.androidquicktrackmeet.Athlete;
import com.quickmeet.androidquicktrackmeet.R;

import java.util.ArrayList;

public class SchoolAthletesAdapter extends ArrayAdapter<Athlete> {
    private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;
    private final ArrayList<Athlete> athletes;
    private SchoolAthletesAdapter adapter = this;
    private TextView nameView, yearView;

    // private final Integer[] imgid;

    public SchoolAthletesAdapter(Activity context, ArrayList<Athlete> athletes) {
        super(context, R.layout.adapter_school_athletes, athletes);
        // TODO Auto-generated constructor stub

        this.context=context;
        // this.names=names;
        // this.grades=grades;
        //this.imgid=imgid;
        this.athletes = athletes;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.adapter_school_athletes, null,true);

        nameView = (TextView) rowView.findViewById(R.id.athleteName);
        yearView= (TextView) rowView.findViewById(R.id.athleteYear);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);


        nameView.setText(athletes.get(position).getLast() + ", " + athletes.get(position).getFirst());
        yearView.setText("" + athletes.get(position).getGrade());

        //imageView.setImageResource(imgid[position]);


        return rowView;

    }


}
