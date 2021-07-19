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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditEventListAdapter extends RecyclerView.Adapter<EditEventListAdapter.ViewHolderEditEvent> {

    private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;

    private EditEventListAdapter adapter = this;
    private ArrayList<Athlete> athletes;
    private String event;
    private Meet meet;
    private LayoutInflater mInflater;


    // private final Integer[] imgid;

    public EditEventListAdapter(Activity context, ArrayList<Athlete> athletes, String event, Meet meet) {
        //super(context, R.layout.custom_editeventlist, athletes);
        // TODO Auto-generated constructor stub
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        // this.names=names;
        // this.grades=grades;
        //this.imgid=imgid;

        this.athletes = athletes;
        this.event = event;
        this.meet = meet;

        System.out.println(this.athletes);
        }

    // inflates the row layout from xml when needed
    @Override
    public EditEventListAdapter.ViewHolderEditEvent onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_editeventlist, parent, false);
        return new EditEventListAdapter.ViewHolderEditEvent(view, new MyMarkTextListener(), new MyPlaceTextListener());
    }

    @Override
    public void onBindViewHolder(EditEventListAdapter.ViewHolderEditEvent holder, int position) {
       // System.out.println("Onbindbeing called");
        Athlete a = athletes.get(position);
        holder.markListener.updateAthlete(a);
        holder.placeListener.updateAthlete(a);
        holder.titleText.setText(athletes.get(position).getLast() + ", "+athletes.get(position).getFirst());

        //imageView.setImageResource(imgid[position]);
        holder.subTitleText.setText(athletes.get(position).getSchool() + "  "+athletes.get(position).getGrade());


        //System.out.println(athletes.get(position).showEvents().size());
        for (Event e : a.showEvents()){
            //System.out.println("attemptng to set mark");
            if(e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(meet.getName())) {
                holder.mark.setText(e.getMarkString());
                System.out.println("printing mark for " + a.getLast() + e.getMarkString());
                if (e.getPlace() != null) {
                    holder.place.setText(("" + e.getPlace()));
                }
                else{
                    holder.place.setText("");
                }
                if (e.getPoints() != null){
                    holder.points.setText("" + e.getPoints());
                }
                else{
                    holder.points.setText("");
                }
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return athletes.size();
    }

    public void deleteItem(int position){
        Athlete a = athletes.get(position);
        for(int i =0; i<a.showEvents().size(); i++){
            if (a.showEvents().get(i).getName().equals(event) && a.showEvents().get(i).getMeetName().equalsIgnoreCase(meet.getName())) {

                a.showEvents().remove(i);
                break;
            }
        }

        athletes.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolderEditEvent extends RecyclerView.ViewHolder {

        public TextView titleText, points, subTitleText;
        public EditText mark, place;
        public LinearLayout cell;
        public MyMarkTextListener markListener;
        public MyPlaceTextListener placeListener;


        public ViewHolderEditEvent(View itemView, MyMarkTextListener markListener, MyPlaceTextListener placeListener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            titleText = (TextView) itemView.findViewById(R.id.title);
            points = (TextView) itemView.findViewById(R.id.points);
             mark = (EditText)itemView.findViewById((R.id.editMark));
             place = (EditText)itemView.findViewById(R.id.editPlace);
            cell = itemView.findViewById(R.id.cell);
            subTitleText = itemView.findViewById(R.id.subtitle);

            this.markListener = markListener;
            this.placeListener = placeListener;
            this.mark.addTextChangedListener(markListener);
            this.place.addTextChangedListener(placeListener);
            //itemView.setOnClickListener(this);
        }

    }

    private class MyMarkTextListener implements TextWatcher {
        private int position;
        private Athlete a;

        public void updatePosition(int position) {
            this.position = position;
        }
        public void updateAthlete(Athlete a){
            this.a = a;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //System.out.println("after mark text changed fired");
            for (Event e : a.showEvents()){
                if(e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(meet.getName())) {
                    e.setMarkString(s.toString());

                    athletes.get(position).updateFirebase();
                    break;
                }
            }

            //adapter.notifyDataSetChanged();
        }
    }

    // PlaceListener Class
    private class MyPlaceTextListener implements TextWatcher {
        private int position;
        private Athlete a;

        public void updatePosition(int position) {
            this.position = position;
        }
        public void updateAthlete(Athlete a){
            this.a = a;
        }

        public void afterTextChanged(Editable s) {
            //System.out.println("after place text changed fired");
                for (Event e : a.showEvents()){
                    if(e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(meet.getName())) {
                        try {
                            e.setPlace(Integer.parseInt(s.toString()));
                            break;
                        }
                        catch(Exception excep){
                            e.setPlace(null);
                        }
                    }
                }

                //adapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {



            }
    }



//    public View getView(int position, View view, ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.custom_editeventlist, null,true);
//        Athlete a = athletes.get(position);
//        TextView titleText = (TextView) rowView.findViewById(R.id.title);
//        TextView points = (TextView) rowView.findViewById(R.id.points);
//        EditText mark = (EditText)rowView.findViewById((R.id.editMark));
//        EditText place = (EditText)rowView.findViewById(R.id.editPlace);
//        //System.out.println(athletes.get(position).showEvents().size());
//        for (Event e : a.showEvents()){
//            //System.out.println("attemptng to set mark");
//            if(e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(meet.getName())) {
//                mark.setText(e.getMarkString());
//                System.out.println("printing mark for " + a.getLast() + e.getMarkString());
//                if (e.getPlace() != null) {
//                    place.setText(("" + e.getPlace()));
//                }
//                if (e.getPoints() != null){
//                    points.setText("" + e.getPoints());
//                }
//                break;
//            }
//        }
//        mark.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                //athletes.get(position).setFirst(mark.getText().toString());
//                // System.out.println(athletes.get(position));
//                for (Event e : a.showEvents()){
//                    if(e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(meet.getName())) {
//                        e.setMarkString(mark.getText().toString());
//                        System.out.println("after text changed fired");
//                        //athletes.get(position).updateFirebase();
//                        break;
//                    }
//                }
//
//                //adapter.notifyDataSetChanged();
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                System.out.println("text is changing");
//
//            }
//        });
//
//       place.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                //athletes.get(position).setFirst(mark.getText().toString());
//                // System.out.println(athletes.get(position));
//                for (Event e : a.showEvents()){
//                    if(e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(meet.getName())) {
//                        try {
//                            e.setPlace(Integer.parseInt(place.getText().toString()));
//                            break;
//                        }
//                        catch(Exception excep){
//
//                        }
//                    }
//                }
//
//                //adapter.notifyDataSetChanged();
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//
//
//            }
//        });
//        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
//
//        titleText.setText(athletes.get(position).getLast() + ", "+athletes.get(position).getFirst());
//
//        //imageView.setImageResource(imgid[position]);
//        subtitleText.setText(athletes.get(position).getSchool() + "  "+athletes.get(position).getGrade());
//
//        return rowView;
//
//    }



}

