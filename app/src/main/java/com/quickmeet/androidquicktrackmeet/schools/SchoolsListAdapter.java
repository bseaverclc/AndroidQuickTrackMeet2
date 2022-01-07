package com.quickmeet.androidquicktrackmeet.schools;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import android.widget.ImageView;
import android.widget.TextView;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.R;
import com.quickmeet.androidquicktrackmeet.School;

import java.util.ArrayList;

public class SchoolsListAdapter extends ArrayAdapter<School> {

    private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;
    private final ArrayList<School> schools;
    private SchoolsListAdapter adapter = this;

    // private final Integer[] imgid;

    public SchoolsListAdapter(Activity context, ArrayList<School> schools) {
        super(context, R.layout.custom_schoolslist, schools);
        // TODO Auto-generated constructor stub

        this.context=context;
        // this.names=names;
        // this.grades=grades;
        //this.imgid=imgid;
        this.schools = AppData.schools;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_schoolslist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView subTitleText = (TextView) rowView.findViewById(R.id.subtitle);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);


        titleText.setText(schools.get(position).getFull());
        subTitleText.setText(schools.get(position).getInits());

        //imageView.setImageResource(imgid[position]);


        return rowView;

    }



}

