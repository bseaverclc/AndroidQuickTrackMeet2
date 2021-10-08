package com.example.androidquicktrackmeet.meets;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Meet;
import com.example.androidquicktrackmeet.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MeetsListAdapter extends ArrayAdapter<Meet> {

    private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;
    private final ArrayList<Meet> meets;
    private MeetsListAdapter adapter = this;

    // private final Integer[] imgid;

    public MeetsListAdapter(Activity context, ArrayList<Meet> meets) {
        super(context, R.layout.custom_meetslist, meets);
        // TODO Auto-generated constructor stub

        this.context=context;
        // this.names=names;
        // this.grades=grades;
        //this.imgid=imgid;
        this.meets = AppData.meets;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_meetslist, null,true);


        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView subTitleText = (TextView) rowView.findViewById(R.id.subtitle);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        Date date = meets.get(position).getDate2();
         String datey = DateFormat.getDateInstance().format(date);
        titleText.setText(datey);
        subTitleText.setText(meets.get(position).getName());
        //view.setMinimumHeight((int)(view.getParent().getHeight()*.10));

        //imageView.setImageResource(imgid[position]);


        return rowView;

    }



}

