package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class EventsActivity extends AppCompatActivity {
private Meet meet;
private String selectedEvent;
private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        EventsListAdapter adapter=new EventsListAdapter(this, meet.getEvents());
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
                //selectEventAction(listView);
            }
        });
    }

    public void selectEventAction(View view){
        Intent intent = new Intent(this, EventsActivity.class);
        intent.putExtra("Selected", selectedMeet);

//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}