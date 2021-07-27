package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class EventsActivity extends AppCompatActivity {
//private Meet meet;
private String selectedEvent;
private int selectedRow;
private ListView listView;
public static int eventPosition;
EventsListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Intent intent = getIntent();
        //selectedEvent = (String)intent.getSerializableExtra("Selected");
        //meet = (Meet)intent.getSerializableExtra("meet");

        adapter=new EventsListAdapter(this, AppData.selectedMeet.getEvents());
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
         setTitle(AppData.selectedMeet.getName());
        attachListener();
        eventPosition = 0;


    }

    @Override
    protected void onResume() {
        super.onResume();
        for(int i = 0; i<AppData.meets.size(); i++){
            if(AppData.meets.get(i).getName().equalsIgnoreCase(AppData.selectedMeet.getName())){
                AppData.selectedMeet = AppData.meets.get(i);
                break;
            }
        }
        adapter=new EventsListAdapter(this, AppData.selectedMeet.getEvents());
        listView.setAdapter(adapter);

        System.out.println("onResume dataset changed");
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//    }

    //    public void addEventAction(View view){
//        startActivity(new Intent(MeetsActivity.this, AddMeetActivity.class));
//    }

    public void attachListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedEvent = (String)parent.getItemAtPosition(position);
                selectedRow = position;
                eventPosition = position;
                System.out.println("Clicked on" + selectedEvent);
                selectEventAction(listView);
            }
        });
    }

    public void selectEventAction(View view){
        Intent intent = new Intent(this, EventEditActivity.class);
        intent.putExtra("Selected", selectedEvent);
        //intent.putExtra("meet", meet);
        intent.putExtra("selectedRow", selectedRow);

//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}