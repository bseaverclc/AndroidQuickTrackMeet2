package com.quickmeet.androidquicktrackmeet.schools;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.R;
import com.quickmeet.androidquicktrackmeet.School;

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
        if(AppData.userID.equals("SRrCKcYVC8U6aZTMv0XCYHHR4BG3")){
            AppData.fullAccess = true;
        }
        else{AppData.fullAccess = false;}

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
            if(AppData.userID.length() !=0)
            {
                Intent intent = new Intent(this, ActivityAddSchool.class);
                startActivity(intent);
                return true;
            }
            else {
                AlertDialog.Builder addFailure = new AlertDialog.Builder(SchoolsActivity.this);
                addFailure.setTitle("Error!");
                addFailure.setMessage("You need to be logged in to add a school");
                addFailure.setPositiveButton("OK", null );
                addFailure.show();
            }
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