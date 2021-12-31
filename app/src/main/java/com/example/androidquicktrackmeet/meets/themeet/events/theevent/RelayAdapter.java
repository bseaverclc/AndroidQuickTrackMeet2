package com.example.androidquicktrackmeet.meets.themeet.events.theevent;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Athlete;
import com.example.androidquicktrackmeet.Event;
import com.example.androidquicktrackmeet.Meet;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.meets.themeet.events.theevent.addathletestoevent.AthleteListAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RelayAdapter extends RecyclerView.Adapter<RelayAdapter.ViewHolder> {
    String splitName;
    ArrayList<Athlete> relayMembers;
    Event chosenEvent, relayEvent;
    Athlete relayAthlete;

    public RelayAdapter(ArrayList<Athlete> relayMembers, String splitName, Event selectedEvent, Athlete relayAthlete){
        this.relayMembers = relayMembers;
        this.splitName = splitName;
        this.relayEvent= selectedEvent;
        this.relayAthlete = relayAthlete;
    }


    @Override
    public RelayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.adapter_relay, parent, false);

        return new RelayAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RelayAdapter.ViewHolder holder, int position) {

         holder.nameView.setText(relayMembers.get(position).getLast() + ", " + relayMembers.get(position).getFirst());
         for(Event e: relayMembers.get(position).showEvents()){
             if (e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName()) && e.getName().equalsIgnoreCase(splitName))
             {
                 holder.splitView.setText(e.getMarkString());
                 chosenEvent = e;
             }
         }
         if(Meet.canCoach) {
             holder.splitView.addTextChangedListener(new MyMarkTextListener(chosenEvent, relayMembers.get(position)));
         }
         else{
             holder.splitView.setEnabled(false);
         }

    }

    @Override
    public int getItemCount() {
        System.out.println("relayMemberSize " + relayMembers.size());
        return relayMembers.size();
    }

    public boolean deleteItem(int position) {
        System.out.println("Deleting a relay member with uid " + relayAthlete.getUid());

        for(Event e: relayMembers.get(position).showEvents()){
            if (e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName()) && e.getName().equalsIgnoreCase(splitName))
            {


                relayEvent.getRelayMembers().remove(position);
                relayAthlete.updateFirebase();

                //removing split event from firebase
                relayMembers.get(position).deleteEventFirebase(e.getUid());
                relayMembers.remove(position);

                this.notifyDataSetChanged();
                break;
            }
        }


        return true;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        EditText splitView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.relayAthleteName);
            splitView = itemView.findViewById(R.id.relayAthleteSplit);


        }
    }

        private class MyMarkTextListener implements TextWatcher {
            private int position;
            private Athlete a;
            private Event chosenEvent;

            public MyMarkTextListener(Event event, Athlete a){
                chosenEvent = event;
                this.a = a;
            }

            public void updatePosition(int position) {
                this.position = position;
            }

            public void updateAthlete(Athlete a) {
                this.a = a;

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(chosenEvent != null) {
                    chosenEvent.setMarkString(s.toString());
                    a.updateFirebase();
                }
            }
        }
}
