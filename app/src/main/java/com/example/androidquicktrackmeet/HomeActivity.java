package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Meet meet;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        meet = (Meet)intent.getSerializableExtra("Selected");
        System.out.println(meet.getSchools());
        setTitle(meet.getName());




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
        intent.putExtra("meet", meet);

//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void teamScoresAction(View view){
        Intent intent = new Intent(this, ScoresActivity.class);
        intent.putExtra("meet", meet);
        startActivity(intent);
    }


}