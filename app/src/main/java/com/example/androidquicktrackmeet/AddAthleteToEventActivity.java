package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddAthleteToEventActivity extends AppCompatActivity {

    private Meet meet;
    private String event;
    private ArrayList<Athlete> eventAthletes;
    private ListView listView;
    private ArrayList<Athlete> displayedAthletes = new ArrayList<Athlete>();
    AthleteListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_athlete_to_event);

        Intent intent = getIntent();
        meet = (Meet)intent.getSerializableExtra("meet");
        event = (String)intent.getSerializableExtra("event");
        eventAthletes = (ArrayList<Athlete>)intent.getSerializableExtra("athletes");
        setTitle(event);

        for(Athlete a: AppData.allAthletes){
            if(meet.getSchools().keySet().contains(a.getSchoolFull())){
                displayedAthletes.add(a);
            }
        }

        Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {

            return o1.getLast().compareTo(o2.getLast());
        };
        Collections.sort(displayedAthletes, sortByName);

        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(findViewById(R.id.school1));
        buttons.add(findViewById(R.id.school2));
        buttons.add(findViewById(R.id.school3));
        buttons.add(findViewById(R.id.school4));
        ArrayList<String> initials = new ArrayList<String>(meet.getSchools().values());
        int buttonNumber = 0;
        for (String i: initials){
            buttons.get(buttonNumber).setText(i);
            buttonNumber+=1;

        }

        adapter=new AthleteListAdapter(this, displayedAthletes);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public void selectSchool(View view){

        Button button = (Button)view;
        String buttonText = button.getText().toString();
        System.out.println("select school" + buttonText);
        displayedAthletes.clear();
        for(Athlete a : AppData.allAthletes){
            if(a.getSchool().equalsIgnoreCase(buttonText) && meet.getSchools().containsKey(a.getSchoolFull())){
                displayedAthletes.add(a);
                System.out.println("added an athlete");
            }
        }
        adapter.notifyDataSetChanged();

    }
}