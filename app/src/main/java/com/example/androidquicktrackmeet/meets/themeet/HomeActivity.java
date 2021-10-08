package com.example.androidquicktrackmeet.meets.themeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.meets.themeet.events.EventsActivity;
import com.example.androidquicktrackmeet.meets.themeet.meetathletes.MeetAthletesActivity;
import com.example.androidquicktrackmeet.R;

public class HomeActivity extends AppCompatActivity {

   // private Meet meet;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        //meet = (Meet)intent.getSerializableExtra("Selected");
        System.out.println(AppData.selectedMeet.getSchools());
        setTitle(AppData.selectedMeet.getName());




//        EventsListAdapter adapter=new EventsListAdapter(this, meet.getEvents());
//        listView=(ListView)findViewById(R.id.listView);
//        listView.setAdapter(adapter);
        //attachListener();
    }

//    public void attachListener(){
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                selectedEvent = (String)parent.getItemAtPosition(position);
//                System.out.println("Clicked on" + selectedEvent);
//                selectEventAction(listView);
//            }
//        });
//    }

    public void selectEventAction(View view){
        Intent intent = new Intent(this, EventsActivity.class);
        //intent.putExtra("meet", meet);

//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void teamScoresAction(View view){
        Intent intent = new Intent(this, ScoresActivity.class);
        //intent.putExtra("meet", meet);
        startActivity(intent);
    }

    public void selectAthletesAction(View view){
        Intent intent = new Intent(this, MeetAthletesActivity.class);

        startActivity(intent);
    }


}