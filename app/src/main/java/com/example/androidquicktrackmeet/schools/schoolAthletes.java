package com.example.androidquicktrackmeet.schools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Athlete;
import com.example.androidquicktrackmeet.Meet;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.meets.AddMeetActivity;
import com.example.androidquicktrackmeet.meets.themeet.events.theevent.addathletestoevent.AddAthlete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class schoolAthletes extends AppCompatActivity {
private ListView listView;
private ArrayList<Athlete> athletes = new ArrayList<Athlete>();
private String selectedSchool;
    private SchoolAthletesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_athletes);
        Intent intent = getIntent();
        selectedSchool = (String)intent.getSerializableExtra("selectedSchool");
        for(Athlete a : AppData.allAthletes)
        {
            if (a.getSchoolFull().equalsIgnoreCase(selectedSchool))
            {
                athletes.add(a);
            }
        }
        Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {

            return o1.getLast().compareTo(o2.getLast());
        };
        Collections.sort(athletes, sortByName);

        adapter=new SchoolAthletesAdapter(this, athletes);
        listView=(ListView)findViewById(R.id.salistView);
        listView.setAdapter(adapter);
        setTitle(selectedSchool);
    }

    @Override
    protected void onResume() {
        super.onResume();
        athletes.clear();
        for(Athlete a : AppData.allAthletes)
        {
            if (a.getSchoolFull().equalsIgnoreCase(selectedSchool))
            {
                athletes.add(a);
            }
        }
        Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {

            return o1.getLast().compareTo(o2.getLast());
        };
        Collections.sort(athletes, sortByName);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schoolathletesmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_athlete) {
            Intent intent = new Intent(this, AddAthlete.class);
            intent.putExtra("theSchool", selectedSchool);

            //intent.putExtra("selectedAthlete", selectedAthlete);
            // intent.putExtra("events", displayedEvents);

            startActivity(intent);
            return true;
        }
        if(id == R.id.check_coaches)
        {

        }

        return super.onOptionsItemSelected(item);
    }




}