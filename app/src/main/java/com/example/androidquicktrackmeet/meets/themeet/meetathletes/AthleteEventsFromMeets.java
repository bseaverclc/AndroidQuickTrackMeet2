package com.example.androidquicktrackmeet.meets.themeet.meetathletes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Athlete;
import com.example.androidquicktrackmeet.Event;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.meets.themeet.events.EventsListAdapter;

import java.util.ArrayList;

public class AthleteEventsFromMeets extends AppCompatActivity {

    Athlete selectedAthlete;
    ArrayList<String> displayedEvents = new ArrayList<String>();
    //RecyclerView recyclerView;
    private ListView listView;
    AthleteEventsFromMeetsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_events_from_meets);

        Intent intent = getIntent();
        selectedAthlete = (Athlete)intent.getSerializableExtra("selectedAthlete");


        for(Event e : selectedAthlete.showEvents()) {
            if (e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName())) {
                    displayedEvents.add(e.getName());
            }
        }



        adapter=new AthleteEventsFromMeetsAdapter(this, displayedEvents);
        listView=(ListView)findViewById(R.id.athleteEventsFromMeets);
        listView.setAdapter(adapter);
        setTitle(selectedAthlete.getFirst() + " " + selectedAthlete.getLast());
        //attachListener();
        //eventPosition = 0;
    }
}