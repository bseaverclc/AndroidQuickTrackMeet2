package com.example.androidquicktrackmeet.meets.themeet.meetathletes;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Athlete;
import com.example.androidquicktrackmeet.meets.themeet.ScoresActivity;
import com.example.androidquicktrackmeet.meets.themeet.events.theevent.addathletestoevent.AthleteListAdapter;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.Event;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MeetAthletesAdapter extends RecyclerView.Adapter<MeetAthletesAdapter.ViewHolder>{

    private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;
    private ArrayList<Athlete> athletes;
    private MeetAthletesAdapter adapter = this;
    private LayoutInflater mInflater;
    private AthleteListAdapter.ItemClickListener mClickListener;




    public MeetAthletesAdapter(Activity context, ArrayList<Athlete> athletes){
        this.mInflater = LayoutInflater.from(context);
        this.athletes = athletes;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.meetathletes, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder( MeetAthletesAdapter.ViewHolder holder, int position) {
        Athlete a = athletes.get(position);
        System.out.println("Athlete binding is " + a.getLast());
        holder.gridLayout.removeAllViews();
        holder.athleteNameYr.setText(a.getLast() + ", "+ a.getFirst()+" ("+a.getGrade()+")");
        for(Event e : a.showEvents()) {
            if(e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName())) {
                TextView tv = new TextView(context);
                tv.setText(e.getName());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 0, 10, 0);

                GridLayout.LayoutParams gParams = new GridLayout.LayoutParams();
                gParams.columnSpec =
                        GridLayout.spec(GridLayout.UNDEFINED, 1f);
                tv.setLayoutParams(params);
                //tv.setLayoutParams(gParams);


                // tv.setTextSize(20);
                TextViewCompat.setAutoSizeTextTypeWithDefaults(tv, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                holder.gridLayout.addView(tv);
            }
                holder.row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("clicked on " + a.getLast() + " at position " + position);
                        Intent intent = new Intent(context, AthleteEventsFromMeets.class);

                        intent.putExtra("selectedAthlete", a);
                        context.startActivity(intent);
                    }
                });


        }


    }

    @Override
    public int getItemCount() {
        return athletes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView athleteNameYr;
        public GridLayout gridLayout;

        public LinearLayoutCompat row;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            athleteNameYr = (TextView) itemView.findViewById(R.id.athleteNameYear);
            System.out.println("GridLayout being created");
            gridLayout = (GridLayout)itemView.findViewById(R.id.gridLayout);
            row = (LinearLayoutCompat)itemView.findViewById(R.id.rowMeetAthletes);

            //row = itemView.findViewById(R.id.row);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            System.out.println("clicked on a row");


        }
    }
    }

