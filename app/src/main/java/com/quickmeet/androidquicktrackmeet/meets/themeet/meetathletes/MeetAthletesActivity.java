package com.quickmeet.androidquicktrackmeet.meets.themeet.meetathletes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.Athlete;
import com.quickmeet.androidquicktrackmeet.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class MeetAthletesActivity extends AppCompatActivity {
    ArrayList<Athlete> displayedAthletes = new ArrayList<Athlete>();
    RecyclerView recyclerView;
    MeetAthletesAdapter adapter;
    String selectedSchool = "";



    @Override
    protected void onResume() {

        displayedAthletes.clear();
        for(Athlete a: AppData.allAthletes){
            if(AppData.selectedMeet.getSchools().keySet().contains(a.getSchoolFull())) {
                if (selectedSchool.length() > 0 && a.getSchoolFull().equalsIgnoreCase(selectedSchool)) {
                    displayedAthletes.add(a);
                }
            }
        }
        Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {

            return o1.getLast().compareTo(o2.getLast());
        };
        Collections.sort(displayedAthletes, sortByName);
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedSchool = AppData.mySchool;
        setContentView(R.layout.activity_meet_athletes);

        for(Athlete a: AppData.allAthletes){
            if(AppData.selectedMeet.getSchools().keySet().contains(a.getSchoolFull())){
                displayedAthletes.add(a);
            }
        }

        Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {

            return o1.getLast().compareTo(o2.getLast());
        };
        Collections.sort(displayedAthletes, sortByName);



        recyclerView = findViewById(R.id.meetAthletesRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MeetAthletesAdapter(this, displayedAthletes);

        //adapter.setClickListener(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);



        LinearLayoutCompat linearLayout = (LinearLayoutCompat)findViewById(R.id.schoolRow);
        for(Map.Entry<String,String> s: AppData.selectedMeet.getSchools().entrySet()){
            Button but = new Button(this);
            but.setText(s.getValue().toString());
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String buttonText=  but.getText().toString();

                    displayedAthletes.clear();
                    for(Athlete a : AppData.allAthletes){
                        if(a.getSchool().equalsIgnoreCase(buttonText) && AppData.selectedMeet.getSchools().containsKey(a.getSchoolFull())){
                            displayedAthletes.add(a);
                            selectedSchool = a.getSchoolFull();
                            //System.out.println("added an athlete");
                        }
                    }

                    Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {

                        return o1.getLast().compareTo(o2.getLast());
                    };
                    Collections.sort(displayedAthletes, sortByName);
                    adapter.notifyDataSetChanged();
                }
            });
            linearLayout.addView(but);

        }
    }






}