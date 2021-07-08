package com.example.androidquicktrackmeet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EventEditActivity extends AppCompatActivity {

    private Meet meet;
    private String selectedEvent;
    private ListView listView;
    private ArrayList<Athlete> eventAthletes = new ArrayList<Athlete>();
    private EditEventListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        System.out.println("onCreate for eventEditActivity");
        Intent intent = getIntent();
        selectedEvent = (String)intent.getSerializableExtra("Selected");
        setTitle(selectedEvent);
        meet = (Meet)intent.getSerializableExtra("meet");
        for(Athlete a: AppData.allAthletes) {
            for(Event e: a.showEvents()){
                if (e.getMeetName().equalsIgnoreCase(meet.getName()) && e.getName().equalsIgnoreCase(selectedEvent)) {
                    eventAthletes.add(a);
                    //break;
                }

            }

        }

        adapter = new EditEventListAdapter(this, eventAthletes, selectedEvent, meet);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        ActionBar actionBar = getSupportActionBar();;
        actionBar.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(topToolBar);


    }

    public void addAction(View view){
        System.out.println("addAction being called");
        Intent intent = new Intent(this, AddAthleteToEventActivity.class);
        intent.putExtra("meet", meet);
        intent.putExtra("event", selectedEvent);
        intent.putExtra("athletes", eventAthletes);
        startActivityForResult(intent, 0);

        //startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0){
            eventAthletes = (ArrayList<Athlete>)data.getSerializableExtra("eventAthletes");
            // add athletes?
        }
        adapter.clear();
        eventAthletes.clear();
        for(Athlete a: AppData.allAthletes) {
            for(Event e: a.showEvents()){
                if (e.getMeetName().equalsIgnoreCase(meet.getName()) && e.getName().equalsIgnoreCase(selectedEvent)) {
                    eventAthletes.add(a);
                    //break;
                }

            }

        }
        adapter.addAll(eventAthletes);
        //adapter.notifyDataSetChanged();

    }

    public void placeAction(View view){
        System.out.println("Place button pushed");

        Comparator<Athlete> sortByPlace = (Athlete o1, Athlete o2) -> {
            if (o1.findEvent(meet.getName(), selectedEvent).getPlace() == null) {
                return (o2.findEvent(meet.getName(), selectedEvent).getPlace() == null) ? 0 : 1;
            }
            if (o2.findEvent(meet.getName(), selectedEvent).getPlace() == null) {
                return -1;
            }
           return o1.findEvent(meet.getName(), selectedEvent).getPlace().compareTo(o2.findEvent(meet.getName(), selectedEvent).getPlace());
        };
        Collections.sort(eventAthletes, sortByPlace);
        System.out.println(eventAthletes);
        adapter.notifyDataSetChanged();



    }

    public void markAction(View view){
        System.out.println("Place button pushed");

        Comparator<Athlete> sortByMark = (Athlete o1, Athlete o2) -> {
            if (o1.findEvent(meet.getName(), selectedEvent).getPlace() == null) {
                return (o2.findEvent(meet.getName(), selectedEvent).getMarkString() == null) ? 0 : 1;
            }
            if (o2.findEvent(meet.getName(), selectedEvent).getMarkString() == null) {
                return -1;
            }
            return o1.findEvent(meet.getName(), selectedEvent).getMarkString().compareTo(o2.findEvent(meet.getName(), selectedEvent).getMarkString());
        };
        Collections.sort(eventAthletes, sortByMark);
        System.out.println(eventAthletes);
        adapter.notifyDataSetChanged();



    }

    public void nameAction(View view){
        System.out.println("Place button pushed");

        Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {
            if (o1.getLast() == null) {
                return (o2.getLast() == null) ? 0 : 1;
            }
            if (o2.getLast()== null) {
                return -1;
            }
            return o1.getLast().compareTo(o2.getLast());
        };
        Collections.sort(eventAthletes, sortByName);
        System.out.println(eventAthletes);
        adapter.notifyDataSetChanged();



    }

    public void schoolAction(View view){
        System.out.println("Place button pushed");

        Comparator<Athlete> sortBySchool = (Athlete o1, Athlete o2) -> {
            if (o1.getSchool() == null) {
                return (o2.getSchool() == null) ? 0 : 1;
            }
            if (o2.getSchool()== null) {
                return -1;
            }
            return o1.getSchool().compareTo(o2.getSchool());
        };
        Collections.sort(eventAthletes, sortBySchool);
        System.out.println(eventAthletes);
        adapter.notifyDataSetChanged();



    }


}