package com.quickmeet.androidquicktrackmeet.meets.themeet.events.theevent.addathletestoevent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.Athlete;
import com.quickmeet.androidquicktrackmeet.R;
import com.quickmeet.androidquicktrackmeet.School;

import java.util.ArrayList;
import java.util.Map;

public class AddAthlete extends AppCompatActivity {
    private String event;
    private String level;
    private ArrayList<Athlete> eventAthletes;
    private RadioGroup yearRadio, schoolRadio;
    private EditText firstName, lastName;
    private RelativeLayout relativeScreen;

    private String theSchool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_athlete2);

        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        yearRadio = (RadioGroup)findViewById(R.id.yearRadio);
        schoolRadio = (RadioGroup)findViewById(R.id.schoolRadio);
        relativeScreen =(RelativeLayout)findViewById(R.id.relativeScreen);
        setTitle("Add an Athlete");


        Intent intent = getIntent();
        //meet = (Meet)intent.getSerializableExtra("meet");

        event = (String) intent.getSerializableExtra("event");
        if(event != null)
        {
            level = event.substring(event.length() - 3);
            eventAthletes = (ArrayList<Athlete>) intent.getSerializableExtra("athletes");


            for (String sch : AppData.selectedMeet.getSchools().values()) {
                RadioButton button = new RadioButton(this);
                button.setText(sch);
                schoolRadio.addView(button);
            }
        }

        theSchool = (String) intent.getSerializableExtra("theSchool");
        if(theSchool != null)
        {
            String schoolInit = "";
           for(School s: AppData.schools)
           {
               if(s.getFull().equalsIgnoreCase(theSchool))
               {
                   schoolInit = s.getInits();
               }
           }
            RadioButton button = new RadioButton(this);
            button.setText(schoolInit);
            schoolRadio.addView(button);
            button.setChecked(true);
        }


        yearRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(relativeScreen.getRootView().getWindowToken(), 0);
            }
        });


        relativeScreen.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });

    }


    public void addToRoster(View view) {
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();

        RadioButton selectedSchool = (RadioButton) findViewById(schoolRadio.getCheckedRadioButtonId());
        RadioButton selectedYear = (RadioButton) findViewById(yearRadio.getCheckedRadioButtonId());

        if (!(first.length() != 0 && last.length() != 0 && selectedSchool != null && selectedYear != null)) {
            System.out.println("Something is blank!");
            AlertDialog.Builder coachFailure = new AlertDialog.Builder(AddAthlete.this);
            coachFailure.setTitle("Error!");
            coachFailure.setMessage("You need to fill out all fields");
            coachFailure.setNegativeButton("Ok", null);
            AlertDialog coachFailureAlert = coachFailure.create();
            coachFailure.show();
        } else {

            String schoolFull = "";
            if(theSchool == null)
            {
                for (Map.Entry<String, String> entry : AppData.selectedMeet.getSchools().entrySet()) {
                    if (entry.getValue().equalsIgnoreCase(selectedSchool.getText().toString())) {
                        schoolFull = entry.getKey();
                    }
                }
            }
            else{
                schoolFull = theSchool;
            }
            System.out.println("FirstLast " + first + last + selectedSchool.getText().toString() + schoolFull);


            Athlete a = new Athlete(first, last, selectedSchool.getText().toString(), Integer.parseInt(selectedYear.getText().toString()), schoolFull, "");
            boolean add = true;
            for (Athlete ath : AppData.allAthletes) {
                if (a.equals(ath)) {
                    add = false;
                    break;
                }
            }
            if (add) {

                AppData.allAthletes.add(a);
                a.saveToFirebase();
                if(event != null) {
                    a.addEvent(event, level, AppData.selectedMeet.getName());
                    eventAthletes.add(a);
                }
                finish();
            }

        }
    }
}