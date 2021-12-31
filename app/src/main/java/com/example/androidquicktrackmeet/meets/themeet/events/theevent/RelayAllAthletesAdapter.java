package com.example.androidquicktrackmeet.meets.themeet.events.theevent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Athlete;
import com.example.androidquicktrackmeet.Event;
import com.example.androidquicktrackmeet.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RelayAllAthletesAdapter extends RecyclerView.Adapter<RelayAllAthletesAdapter.ViewHolder> {

    private ArrayList<Athlete> schoolAthletes;
    RelayAdapter relayAdapter;
    String splitName;
     public RelayAllAthletesAdapter(ArrayList<Athlete> schoolAthletes, RelayAdapter otherAdapter, String splitName){
         this.schoolAthletes = schoolAthletes;
         relayAdapter = otherAdapter;
         this.splitName = splitName;

     }

    @Override
    public RelayAllAthletesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.adapter_relay_allathletes, parent, false);

        return new RelayAllAthletesAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RelayAllAthletesAdapter.ViewHolder holder, int position) {
         holder.athleteNameView.setText(schoolAthletes.get(position).getLast() + ", " + schoolAthletes.get(position).getFirst());
         holder.initsView.setText(schoolAthletes.get(position).getSchool());
         holder.yearView.setText("" +schoolAthletes.get(position).getGrade());

     }

    @Override
    public int getItemCount() {
        return schoolAthletes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView athleteNameView, yearView, initsView;

        public ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            athleteNameView = (TextView)itemView.findViewById(R.id.theAthleteName);
            yearView = (TextView)itemView.findViewById(R.id.theGrade);
            initsView = (TextView)itemView.findViewById(R.id.theSchool);
        }

        @Override
        public void onClick(View v) {
             Athlete chosenA = schoolAthletes.get(getAdapterPosition());
             System.out.println("position clicked on Adapter " + getAdapterPosition() + " " + chosenA.getLast());
             for (Athlete a: relayAdapter.relayMembers){
                 if(a.getUid().equals(chosenA.getUid())){
                     System.out.println("Already in relay");
                     return;
                 }
             }
            System.out.println(relayAdapter.relayEvent.getName());
             if(relayAdapter.relayEvent.getRelayMembers()==null){
                 relayAdapter.relayEvent.resetRelayMembers();
             }

             // Add uid to relay team

             relayAdapter.relayEvent.getRelayMembers().add(chosenA.getUid());
             System.out.println(relayAdapter.relayAthlete.getUid());
             relayAdapter.relayAthlete.updateFirebase();

             // Add split event to chosenA
            chosenA.addEvent(splitName, splitName.substring(splitName.length()-3), AppData.selectedMeet.getName());
            // event added to firebase in addEvent
            //chosenA.updateFirebase();

            relayAdapter.relayMembers.add(chosenA);
            relayAdapter.notifyDataSetChanged();

        }
    }
}
