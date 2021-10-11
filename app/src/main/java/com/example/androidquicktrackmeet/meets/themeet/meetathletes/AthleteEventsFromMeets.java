package com.example.androidquicktrackmeet.meets.themeet.meetathletes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
    ArrayList<Event> displayedEvents = new ArrayList<Event>();
    //RecyclerView recyclerView;
    private RecyclerView recyclerView;
    AthleteEventsFromMeetsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_events_from_meets);

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        selectedAthlete = (Athlete)intent.getSerializableExtra("selectedAthlete");
        System.out.println(selectedAthlete.getLast() + "was accepted");


        for(Event e : selectedAthlete.showEvents()) {
            if (e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName())) {
                    displayedEvents.add(e);
            }
        }



        adapter=new AthleteEventsFromMeetsAdapter(displayedEvents, selectedAthlete);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable mDivider = ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.divider);
        dividerItemDecoration.setDrawable(mDivider);

        recyclerView.addItemDecoration(dividerItemDecoration);


        recyclerView.setAdapter(adapter);
       // listView=(ListView)findViewById(R.id.athleteEventsFromMeets);
       // listView.setAdapter(adapter);
        setTitle(selectedAthlete.getFirst() + " " + selectedAthlete.getLast());
        //attachListener();
        //eventPosition = 0;
    }
}