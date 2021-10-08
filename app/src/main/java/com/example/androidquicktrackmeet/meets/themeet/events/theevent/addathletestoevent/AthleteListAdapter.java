package com.example.androidquicktrackmeet.meets.themeet.events.theevent.addathletestoevent;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidquicktrackmeet.Athlete;
import com.example.androidquicktrackmeet.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AthleteListAdapter extends RecyclerView.Adapter<AthleteListAdapter.ViewHolder> {


    private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;
    private ArrayList<Athlete> athletes;
    private AthleteListAdapter adapter = this;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    public static ArrayList<Athlete> selectedAthletes = new ArrayList<Athlete>();
    public static ArrayList<Integer> selectedPositions = new ArrayList<Integer>();
    // private final Integer[] imgid;

    public AthleteListAdapter(Activity context, ArrayList<Athlete> athletes) {
        //super(context, R.layout.custom_athleteslist, athletes);
        this.mInflater = LayoutInflater.from(context);
        this.athletes = athletes;



        this.context = context;


    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_addathletetoevent, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Athlete a = athletes.get(position);
        //System.out.println("Calling onBind with " + a);
        holder.titleText.setText(a.getLast() + ", " + a.getFirst());
        holder.subTitleText.setText(a.getSchool());
        holder.gradeText.setText("" + a.getGrade());

//        if(selectedAthletes.contains(a)){
//            System.out.println("matched athlete" + a);
//            holder.titleText.setBackgroundColor(Color.GRAY);
//        }
        if(selectedPositions.contains(new Integer(position))){
            holder.row.setBackgroundColor(Color.GREEN);
        }
        else{
            holder.row.setBackgroundColor(Color.WHITE);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return athletes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleText;
        public TextView subTitleText;
        public TextView gradeText;
        public LinearLayout row;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            titleText = (TextView) itemView.findViewById(R.id.title);
            subTitleText = (TextView) itemView.findViewById(R.id.subtitle);
            gradeText = (TextView) itemView.findViewById(R.id.grade);
            row = itemView.findViewById(R.id.row);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("clicked");
           // if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            if(selectedPositions.contains(getAdapterPosition())){
                selectedPositions.remove(new Integer(getAdapterPosition()));
                selectedAthletes.remove(adapter.getItem(getAdapterPosition()));

            }
            else {
                selectedAthletes.add(adapter.getItem(getAdapterPosition()));
                selectedPositions.add(getAdapterPosition());
            }

//            System.out.println("clicked on " + getAdapterPosition());
//            //view.setBackgroundColor(Color.GRAY);
//            System.out.println(selectedAthletes);
//            System.out.println(selectedPositions);
            notifyDataSetChanged();
        }
    }

    // convenience method for getting data at click position
    Athlete getItem(int id) {
        return athletes.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    }








