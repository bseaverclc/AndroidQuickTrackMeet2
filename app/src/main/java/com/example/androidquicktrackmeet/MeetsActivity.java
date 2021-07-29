package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class MeetsActivity extends AppCompatActivity {
private ListView listView;
//private Meet selectedMeet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meets);

        Comparator<Meet> sortByDate = (Meet o1, Meet o2) -> {
            return o1.getDate2().compareTo(o2.getDate2());
        };
        Collections.sort(AppData.meets, sortByDate);
        Collections.reverse(AppData.meets);


        MeetsListAdapter adapter=new MeetsListAdapter(this, AppData.meets);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        attachListener();


    }

//    public void addMeetAction(View view){
//        startActivity(new Intent(MeetsActivity.this, AddMeetActivity.class));
//    }


    @Override
    protected void onResume() {
        super.onResume();
        Meet.canCoach = false;
        Meet.canManage = false;
        System.out.println("onResume happening");
    }

    public void attachListener(){
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            AppData.selectedMeet = (Meet)parent.getItemAtPosition(position);

            checkAccess();
            //System.out.println("Clicked on" + AppData.selectedMeet.getName());

        }
    });
    }

    public void checkAccess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Access");
        //builder.setMessage("Are you sure you want to clear all places?");
        // add the buttons
        builder.setPositiveButton("Fan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something like...
                System.out.println("clicked fan");
                Meet.canManage = false;
                Meet.canCoach = false;
                selectMeetAction(listView);
            }
        });
        builder.setNegativeButton("Coach", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something like...
                System.out.println("clicked coach");
                //ArrayList<String> keys = (ArrayList<String>)AppData.selectedMeet.getSchools().ke;
                for (Map.Entry<String, String> entry : AppData.selectedMeet.getSchools().entrySet()){
                    for(School school: AppData.schools){
                        System.out.println("checking school" + school.getFull() + " with " + entry.getKey());
                        if(school.getFull().equalsIgnoreCase(entry.getKey())){
                            for(String coach: school.getCoaches()){
                                System.out.println("checking coach " + coach + " with "+ AppData.coach);
                                if(coach.equalsIgnoreCase(AppData.coach)){
                                    System.out.println("found coach");
                                    Meet.canCoach = true;
                                    AppData.mySchool = school.getFull();
                                    AlertDialog.Builder coachSuccess = new AlertDialog.Builder(MeetsActivity.this);
                                    coachSuccess.setTitle("Success");
                                    coachSuccess.setMessage("You are logged in to edit entries for " + AppData.mySchool );
                                    coachSuccess.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do something like...
                                            selectMeetAction(listView);
                                        }
                                    });
                                    AlertDialog coachSuccessAlert = coachSuccess.create();
                                    coachSuccessAlert.show();
                                    break;
                                }
                            }

                        }
                        if(Meet.canCoach){break;}
                    }
                    if(Meet.canCoach){break;}
                }
                if(!Meet.canCoach) {
                    AlertDialog.Builder coachFailure = new AlertDialog.Builder(MeetsActivity.this);
                    coachFailure.setTitle("Failure");
                    coachFailure.setMessage("You are not logged in as a coach for any of the teams in this meet");
                    AlertDialog coachFailureAlert = coachFailure.create();
                    coachFailure.show();
                }

//
            }
        });
       // builder.set("Cancel", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void selectMeetAction(View view){
       // Meet.canCoach = true;
        //Meet.canManage = true;
        Intent intent = new Intent(this, HomeActivity.class);
        //intent.putExtra("Selected", selectedMeet);


//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}