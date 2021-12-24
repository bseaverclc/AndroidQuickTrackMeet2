package com.example.androidquicktrackmeet.schools;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.School;
import com.example.androidquicktrackmeet.meets.themeet.events.theevent.addathletestoevent.AddAthlete;

public class ActivityAddSchool extends AppCompatActivity {
private RadioButton menButton, womenButton;
private EditText fullSchoolEdit, initEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school);

        menButton = (RadioButton) (findViewById(R.id.menRadio));
        womenButton = (RadioButton) (findViewById(R.id.womenRadio));
        fullSchoolEdit = (EditText)(findViewById(R.id.fullSchoolEdit));
        initEdit = (EditText)(findViewById(R.id.initEdit));
        setTitle("Add a School");
    }

    public void submitSchool(View view)
    {
        AlertDialog.Builder schoolFailure = new AlertDialog.Builder(ActivityAddSchool.this);
        schoolFailure.setTitle("Error!");
        schoolFailure.setNegativeButton("Ok", null);
        AlertDialog schoolFailureAlert = schoolFailure.create();
        if((!menButton.isChecked() && !womenButton.isChecked()) || fullSchoolEdit.getText().length() == 0 || initEdit.getText().length() == 0)
        {

            schoolFailure.setMessage("You need to fill out all fields");
            schoolFailure.show();
            return;
        }
        else
        {
            String gender = "W";
            if(menButton.isChecked()){
                gender = "M";
            }
            String schoolFull = fullSchoolEdit.getText().toString() + " ("+gender+")";
            String initials = initEdit.getText().toString();
            for(School s : AppData.schools)
            {
                if(s.getFull().equalsIgnoreCase(schoolFull))
                {
                    schoolFailure.setMessage("School name already in use");
                    schoolFailure.show();
                    return;
                }
                else if (s.getInits().equalsIgnoreCase(initials) && s.getFull().substring(s.getFull().length()-2,s.getFull().length()-1).equalsIgnoreCase(gender))
                {
                    schoolFailure.setMessage("Initials already in use");
                    schoolFailure.show();
                    return;
                }
            }

            School addSchool = new School(schoolFull,initials);
            addSchool.addCoach(AppData.coach);
            AppData.schools.add(addSchool);
            addSchool.saveToFirebase();

            finish();
        }
    }
}