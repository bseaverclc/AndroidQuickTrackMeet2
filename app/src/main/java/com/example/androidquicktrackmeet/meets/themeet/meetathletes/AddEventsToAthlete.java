package com.example.androidquicktrackmeet.meets.themeet.meetathletes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.meets.themeet.events.EventsListAdapter;

public class AddEventsToAthlete extends AppCompatActivity {
  EventsListAdapter adapter;
  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events_to_athlete);

        adapter=new EventsListAdapter(this, AppData.selectedMeet.getEvents());
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        setTitle(AppData.selectedMeet.getName());
    }


}