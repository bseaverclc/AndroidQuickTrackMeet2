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
import com.example.androidquicktrackmeet.School;
import com.example.androidquicktrackmeet.meets.AddMeetActivity;
import com.example.androidquicktrackmeet.meets.themeet.meetathletes.AddEventsToAthleteAdapter;

import java.util.Collections;
import java.util.Comparator;

public class SchoolsActivity extends AppCompatActivity {

    private ListView listView;
    private School selectedSchool;
    private SchoolsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools2);

        System.out.println("Number of athletes" + AppData.allAthletes.size());
        // Setting up the ListView
        Comparator<School> sortByName = (School o1, School o2) -> {

            return o1.getFull().compareTo(o2.getFull());
        };
        Collections.sort(AppData.schools, sortByName);

        adapter=new SchoolsListAdapter(this, AppData.schools);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        attachListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Comparator<School> sortByName = (School o1, School o2) -> {

            return o1.getFull().compareTo(o2.getFull());
        };
        Collections.sort(AppData.schools, sortByName);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, ActivityAddSchool.class);

            //intent.putExtra("selectedAthlete", selectedAthlete);
            // intent.putExtra("events", displayedEvents);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void attachListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedSchool = (School)parent.getItemAtPosition(position);

                selectAthlete(listView);
                //System.out.println("Clicked on" + AppData.selectedMeet.getName());

            }
        });
    }

    public void selectAthlete(View view)
    {
        Intent intent = new Intent(this,schoolAthletes.class);
        intent.putExtra("selectedSchool", selectedSchool.getFull());
        startActivity(intent);
    }
}