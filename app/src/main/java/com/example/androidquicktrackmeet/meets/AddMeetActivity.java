package com.example.androidquicktrackmeet.meets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Meet;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.School;
import com.example.androidquicktrackmeet.meets.themeet.events.EventsActivity;
import com.example.androidquicktrackmeet.meets.themeet.events.theevent.addathletestoevent.AddAthlete;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AddMeetActivity extends AppCompatActivity {
    private Meet meet;
    private Meet selectedMeet;
    private boolean changeMeet = false;

    ArrayList<String> events = new ArrayList<String>(
            Arrays.asList("4x800", "4x100", "3200", "110HH", "100M", "800M", "4x200", "400M", "300IM", "1600", "200M", "4x400", "Long Jump", "Triple Jump", "High Jump", "Pole Vault", "Shot Put", "Discus"));

    ArrayList<String> lev = new ArrayList<>();
    ArrayList<Integer> indPoints = new ArrayList<Integer>();
    ArrayList<Integer>  relPoints = new ArrayList<Integer>();

    private LinearLayout wholeScreen;
    private EditText meetName;

    private RadioButton menRadio, womenRadio;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private TextView theSchools;
    private int year, month, day;
    private Date date;
    public static ArrayList<School> selectedSchools;
    private CheckBox var, fs, jv;
    private ArrayList<EditText> individualScores, relayScores;
    private EditText coachCode, mangerCode;
    private Button submit;

    public void initialzeValues()
    {
        meetName.setText(selectedMeet.getName());
        meetName.setEnabled(false);
        meetName.setBackgroundColor(Color.GRAY);

        SimpleDateFormat sdf = new SimpleDateFormat("EE MM/dd/yy");
        String dateString = sdf.format(selectedMeet.getDate2());
        dateView.setText(dateString);

        if(selectedMeet.getGender().equalsIgnoreCase("M")){
            menRadio.setChecked(true);
        }
        else{
            womenRadio.setChecked(true);
        }

        for(String level: selectedMeet.getLevels())
        {
            if(level.equalsIgnoreCase("VAR"))
            {
                var.setChecked(true);
            }
            if(level.equalsIgnoreCase("F/S"))
            {
                fs.setChecked(true);
            }
            if(level.equalsIgnoreCase("JV"))
            {
                jv.setChecked(true);
            }
        }

        // Add Schools
       for(School school: AppData.schools)
       {
           if(selectedMeet.getSchools().get(school.getFull()) != null)
           {
               selectedSchools.add(school);
           }
       }
       String schools = "";
        for(School school : selectedSchools)
        {
            schools += school.getFull() + "\n";
        }
        theSchools.setText(schools);

        int i = 0;
        for(int point: selectedMeet.getIndPoints())
        {
            individualScores.get(i).setText("" + point);
            i++;
        }
        i = 0;
        for(int point: selectedMeet.getRelPoints())
        {
            relayScores.get(i).setText("" + point);
        }

        coachCode.setText(selectedMeet.getCoachCode());
        mangerCode.setText(selectedMeet.getManagerCode());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("On Create AddMeetActivity");
        //System.out.println(events);
        setContentView(R.layout.activity_add_meet);
        Intent intent = getIntent();
        selectedMeet = (Meet)intent.getSerializableExtra("selectedMeet");



        wholeScreen = findViewById(R.id.wholeScreen);
        meetName = findViewById(R.id.meetName);

        menRadio = findViewById(R.id.menRadio);
        womenRadio = findViewById(R.id.womenRadio);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EE MM/dd/yy");
        String dateString = sdf.format(date);
        dateView = (TextView)(findViewById(R.id.meetDate));
        dateView.setText(dateString);

        selectedSchools = new ArrayList<School>();
        theSchools = (TextView)findViewById(R.id.theSchools);
        theSchools.setText("No Schools Added");

        var = (CheckBox)(findViewById(R.id.var));
        fs = (CheckBox)(findViewById(R.id.fs));
        jv = (CheckBox)(findViewById(R.id.jv));

        individualScores = new ArrayList<EditText>();
        individualScores.add(findViewById(R.id.ind1));
        individualScores.add(findViewById(R.id.ind2));
        individualScores.add(findViewById(R.id.ind3));
        individualScores.add(findViewById(R.id.ind4));
        individualScores.add(findViewById(R.id.ind5));
        individualScores.add(findViewById(R.id.ind6));
        individualScores.add(findViewById(R.id.ind7));
        individualScores.add(findViewById(R.id.ind8));
        relayScores = new ArrayList<EditText>();
        relayScores.add(findViewById(R.id.rel1));
        relayScores.add(findViewById(R.id.rel2));
        relayScores.add(findViewById(R.id.rel3));
        relayScores.add(findViewById(R.id.rel4));
        relayScores.add(findViewById(R.id.rel5));
        relayScores.add(findViewById(R.id.rel6));
        relayScores.add(findViewById(R.id.rel7));
        relayScores.add(findViewById(R.id.rel8));

        coachCode = findViewById(R.id.coachCode);
        mangerCode = findViewById(R.id.managerCode);
        submit = findViewById(R.id.submit);

        if(selectedMeet != null)
        {
            changeMeet = true;
            System.out.println("We are changing a meet!");

            initialzeValues();
        }


        // Makes keyboard disappear when you click off editText
        wholeScreen.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        System.out.println("onResume");
        String schools = "";
        for(School school : selectedSchools)
        {
            schools += school.getFull() + "\n";
        }
        theSchools.setText(schools);
        System.out.println(schools);
        super.onResume();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {

                    calendar.set(arg1, arg2, arg3);

                    date = calendar.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("EE MM/dd/yy");
                    String dateString = sdf.format(date);
                    dateView.setText(dateString);
                }
            };


    public void selectSchools(View view){
        Intent intent = new Intent(this, AddSchoolsToMeet.class);
        //intent.putExtra("selectedSchools", selectedSchools);
        startActivity(intent);
    }



    public void onSubmit(View v)
    {
        String gen = "";
        AlertDialog.Builder failureAlert = new AlertDialog.Builder(AddMeetActivity.this);
        failureAlert.setTitle("Error!");
        failureAlert.setMessage("You need to fill out all fields");
        failureAlert.setNegativeButton("Ok", null);
        AlertDialog coachFailureAlert = failureAlert.create();
        if(meetName.getText().length() == 0) {
            failureAlert.setMessage("You need to have a meet name");
            failureAlert.show();
            return;
        }

        if(!changeMeet)
        {
            for(Meet oldMeet : AppData.meets)
            {
                if(oldMeet.getName().equalsIgnoreCase(meetName.getText().toString()))
                {
                    failureAlert.setMessage("Meet name already in use");
                    failureAlert.show();
                    return;
                }
            }
        }

        if(selectedSchools.size() == 0)
        {
            failureAlert.setMessage("You must have at least 1 school");
            failureAlert.show();
            return;
        }
        if(selectedSchools.size() > 4)
        {
            failureAlert.setMessage("You can only have a max of 4 schools");
            failureAlert.show();
            return;
        }
        if(!menRadio.isChecked() && !womenRadio.isChecked())
        {
            failureAlert.setMessage("You must pick a gender");
            failureAlert.show();
            return;
        }
        gen = "M";
        if(womenRadio.isChecked())
        {
            gen = "W";
            for(int i = 0; i<events.size(); i++)
            {
                if(events.get(i).equalsIgnoreCase("110HH")){
                    events.set(i,"100HH");
                }
                if(events.get(i).equalsIgnoreCase("300IM")){
                    events.set(i,"300LH");
                }
            }
        }

        lev.clear();
        if(var.isChecked()){
            lev.add(var.getText().toString());
        }
        if(fs.isChecked()){
            lev.add(fs.getText().toString());
        }
        if(jv.isChecked()){
            lev.add(jv.getText().toString());
        }
        if(lev.size() == 0)
        {
            failureAlert.setMessage("You must pick at least 1 level");
            failureAlert.show();
            return;
        }

        ArrayList<Boolean> beenScored = new ArrayList<Boolean>();
        ArrayList<String> eventLeveled = new ArrayList<String>();
        for(String e: events)
        {
            for(String level: lev)
            {
                eventLeveled.add(e + " " + level);
                beenScored.add(false);
            }
        }

        indPoints.clear();
        int i = 0;
        while(i<8 && !individualScores.get(i).getText().toString().equalsIgnoreCase(""))
        {
            try{
                indPoints.add(Integer.parseInt(individualScores.get(i).getText().toString()));
            }
            catch (Exception e)
            {
                failureAlert.setMessage("Points need to be integer values");
                failureAlert.show();
                return;
            }
            i++;
        }
        if(i == 0)
        {
            failureAlert.setMessage("You must have some individual points");
            failureAlert.show();
            return;
        }

        relPoints.clear();
        i = 0;
        while(i<8 && !relayScores.get(i).getText().toString().equalsIgnoreCase(""))
        {
            try{
                relPoints.add(Integer.parseInt(relayScores.get(i).getText().toString()));
            }
            catch (Exception e)
            {
                failureAlert.setMessage("Points need to be integer values");
                failureAlert.show();
                return;
            }
            i++;
        }
        if(i == 0)
        {
            failureAlert.setMessage("You must have some relay points");
            failureAlert.show();
            return;
        }

        if( coachCode.getText().toString().equalsIgnoreCase(""))
        {
            failureAlert.setMessage("You must have a coaches code");
            failureAlert.show();
            return;
        }

        if( mangerCode.getText().toString().equalsIgnoreCase(""))
        {
            failureAlert.setMessage("You must have a meet manager code");
            failureAlert.show();
            return;
        }

        //Add the meet
       // public Meet(String name, Date date, HashMap<String,String> schools, String gender, ArrayList<String> levels, ArrayList<String> events,
        //    ArrayList<Integer> indPoints, ArrayList<Integer> relPoints, ArrayList<Boolean> beenScored, String coachCode, String managerCode){
        HashMap<String, String> schoolsHash = new HashMap<String, String>();
        for(School s: selectedSchools) {
            schoolsHash.put(s.getFull(), s.getInits());
        }

        meet = new Meet(meetName.getText().toString(), date, schoolsHash,gen,lev, eventLeveled,indPoints,relPoints,beenScored,coachCode.getText().toString(),mangerCode.getText().toString());

        if (changeMeet)
        {
            if (selectedMeet != null)
            {
                selectedMeet.updateFirebase(meet);
                System.out.println("Trying to update meet in firebase");
            }
        }
        else{
            AppData.meets.add(meet);
            meet.saveToFirebase();
        }

        finish();

    }

}