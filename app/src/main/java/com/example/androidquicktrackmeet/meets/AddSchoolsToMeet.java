package com.example.androidquicktrackmeet.meets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Athlete;
import com.example.androidquicktrackmeet.Event;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.School;
import com.example.androidquicktrackmeet.meets.themeet.meetathletes.AddEventsToAthleteAdapter;
import com.example.androidquicktrackmeet.schools.SchoolsListAdapter;

import java.util.ArrayList;

public class AddSchoolsToMeet extends AppCompatActivity {
    //private ArrayList<School> selectedSchools;
    AddSchoolsToMeetAdapter adapter;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schools_to_meet);

        Intent intent = getIntent();

        // maybe pass over current selected schools?

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new AddSchoolsToMeetAdapter();
        recyclerView.setAdapter(adapter);
        setTitle("Select Schools");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addselected, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_selected) {
//            for(int i = 0; i < AddSchoolsToMeetAdapter.selectedPositions.size(); i++){
//                String a = AddSchoolsToMeetAdapter.selectedSchools.get(i);
//
//                //System.out.println("athlete checking to add " + a);
//                int stop = events.size();
//                boolean add = true;
//                for(int j = 0; j< stop; j++) {
//                    if (events.get(j).getName().equals(a)) {
//                        add = false;
//                        break;
//                    }
//                }
//                if(add){
//                    //String event = a.substring(0,a.length()-3);
//                    String level = a.substring(a.length()-3);
//                    selectedAthlete.addEvent(a, level, AppData.selectedMeet.getName());
//                    //eventAthletes.add(a);
//
//                    //a.updateFirebase();
//                }
//                else{
//                    System.out.println("duplicate event");
//                }
//            }
//            System.out.println("addAthletes function ending");
//            AddEventsToAthleteAdapter.selectedEvents.clear();
//            AddEventsToAthleteAdapter.selectedPositions.clear();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}