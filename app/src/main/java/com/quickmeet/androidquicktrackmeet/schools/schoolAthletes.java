package com.quickmeet.androidquicktrackmeet.schools;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.Athlete;
import com.quickmeet.androidquicktrackmeet.R;
import com.quickmeet.androidquicktrackmeet.School;
import com.quickmeet.androidquicktrackmeet.meets.themeet.events.theevent.addathletestoevent.AddAthlete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class schoolAthletes extends AppCompatActivity {
private ListView listView;
private ArrayList<Athlete> athletes = new ArrayList<Athlete>();
private String selectedSchool;
    private SchoolAthletesAdapter adapter;
    private School sch = new School();

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

        if(AppData.schools.size()>0) {

            for (School s : AppData.schools) {
                if (s.getFull().equalsIgnoreCase(selectedSchool)) {
                    sch = s;
                    break;
                }
            }
        }


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
            if (sch.getCoaches().contains(AppData.coach) || AppData.fullAccess) {
                Intent intent = new Intent(this, AddAthlete.class);
                intent.putExtra("theSchool", selectedSchool);

                //intent.putExtra("selectedAthlete", selectedAthlete);
                // intent.putExtra("events", displayedEvents);

                startActivity(intent);
                return true;
            }
            else{
                AlertDialog.Builder addFailure = new AlertDialog.Builder(this);
                addFailure.setTitle("Error!");
                addFailure.setMessage("You need to be a coach of this school to add an athlete");
                addFailure.setPositiveButton("OK", null );
                addFailure.show();
                return true;
            }
        }
        if(id == R.id.check_coaches)
        {
              Intent intent = new Intent(this,SchoolCoachesActivity.class);
              intent.putExtra("theSchool", selectedSchool);
              startActivity(intent);
              return true;
        }

        return super.onOptionsItemSelected(item);
    }




}