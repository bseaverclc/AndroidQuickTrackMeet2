package com.example.androidquicktrackmeet.meets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.School;
import com.example.androidquicktrackmeet.meets.themeet.events.EventsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddMeetActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private TextView theSchools;
    private int year, month, day;
    private Date date;
    public static ArrayList<School> selectedSchools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("On Create AddMeetActivity");
        setContentView(R.layout.activity_add_meet);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        selectedSchools = new ArrayList<School>();
        theSchools = (TextView)findViewById(R.id.theSchools);
        theSchools.setText("No Schools Yet");

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
                    TextView dateView = (TextView) findViewById(R.id.meetDate);
                    dateView.setText(dateString);
                }
            };


    public void selectSchools(View view){
        Intent intent = new Intent(this, AddSchoolsToMeet.class);
        //intent.putExtra("selectedSchools", selectedSchools);
        startActivity(intent);
    }

}