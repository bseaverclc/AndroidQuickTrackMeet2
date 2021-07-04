package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EventEditActivity extends AppCompatActivity {

    private Meet meet;
    private String selectedEvent;
    private ListView listView;
    private ArrayList<Athlete> eventAthletes = new ArrayList<Athlete>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);


        Intent intent = getIntent();
        selectedEvent = (String)intent.getSerializableExtra("Selected");
        setTitle(selectedEvent);
        meet = (Meet)intent.getSerializableExtra("meet");
        for(Athlete a: AppData.allAthletes) {
            for(Event e: a.showEvents()){
                if (e.getMeetName().equals(meet.getName()) && e.getName().equals(selectedEvent)) {
                    eventAthletes.add(a);
                }

            }

        }

            EditEventListAdapter adapter=new EditEventListAdapter(this, eventAthletes, selectedEvent, meet);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

         attachListener();

    }

//    public void addEventAction(View view){
//        startActivity(new Intent(MeetsActivity.this, AddMeetActivity.class));
//    }

    public void attachListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedEvent = (String)parent.getItemAtPosition(position);
                System.out.println("Clicked on" + selectedEvent);
                selectEventAction(listView);
            }
        });
    }

    public void selectEventAction(View view){
        Intent intent = new Intent(this, EventEditActivity.class);
        intent.putExtra("Selected", selectedEvent);

//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}