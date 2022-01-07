package com.quickmeet.androidquicktrackmeet.meets.themeet.events.theevent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.Athlete;
import com.quickmeet.androidquicktrackmeet.Event;
import com.quickmeet.androidquicktrackmeet.Meet;
import com.quickmeet.androidquicktrackmeet.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RelayActivity extends AppCompatActivity {
    Athlete relayAthlete;
    String eventName, meetName, splitName;
    RecyclerView recyclerView, recyclerView2;
    Event selectedEvent;
    ArrayList<Athlete> relayMembersAthlete = new ArrayList<Athlete>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("RelayActivity created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relay);
        Intent intent = getIntent();
        relayAthlete = (Athlete) intent.getSerializableExtra("relayAthlete");
        eventName = intent.getStringExtra("eventName");
        meetName = AppData.selectedMeet.getName();
        for (Event e : relayAthlete.showEvents()) {
            if (e.getName().equalsIgnoreCase(eventName) && e.getMeetName().equalsIgnoreCase(meetName)) {
                selectedEvent = e;
                if(selectedEvent.getRelayMembers()!= null ) {
                    for (String uid : selectedEvent.getRelayMembers()) {
                        for (Athlete a : AppData.allAthletes) {
                            if (a.getUid().equals(uid)) {
                                relayMembersAthlete.add(a);
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }
        splitName = eventName.substring(2, 5) + " split " + eventName.substring(eventName.length() - 3);
        System.out.println("splitName " + splitName);

        setTitle(eventName + " " + relayAthlete.getLast() + ", " + relayAthlete.getFirst() + " splits");


        recyclerView = findViewById(R.id.relayRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RelayAdapter adapter = new RelayAdapter(relayMembersAthlete, splitName, selectedEvent, relayAthlete);
        recyclerView.setAdapter(adapter);

        if (Meet.canCoach) {

            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                @Override
                public boolean isItemViewSwipeEnabled() {
                    return true;
                }

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    System.out.println("Trying to split athlete from relay");
                    int spot = viewHolder.getAdapterPosition();
                    adapter.deleteItem(spot);
                    //adapter.notifyDataSetChanged();
                    //events.remove(position);
                    //adapter.notifyDataSetChanged();

                }
            };

            ItemTouchHelper touchHelper = new ItemTouchHelper(itemTouchHelperCallback);
            touchHelper.attachToRecyclerView(recyclerView);


            // Setting up 2nd RecyclerView
            ArrayList<Athlete> schoolAthletes = new ArrayList<Athlete>();
            for(Athlete a: AppData.allAthletes)
            {
                if (a.getSchoolFull().equalsIgnoreCase(relayAthlete.getSchoolFull())){
                    schoolAthletes.add(a);
                }
            }

            Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {

                return o1.getLast().compareTo(o2.getLast());
            };
            Collections.sort(schoolAthletes, sortByName);
            recyclerView2 = findViewById(R.id.athletesRecycler);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));


            RelayAllAthletesAdapter adapter2 = new RelayAllAthletesAdapter(schoolAthletes, adapter, splitName);
            recyclerView2.setAdapter(adapter2);


        }


    }



}