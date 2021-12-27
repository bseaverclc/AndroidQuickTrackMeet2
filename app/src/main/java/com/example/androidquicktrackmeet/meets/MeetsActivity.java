package com.example.androidquicktrackmeet.meets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.Athlete;
import com.example.androidquicktrackmeet.Event;
import com.example.androidquicktrackmeet.Meet;
import com.example.androidquicktrackmeet.meets.themeet.HomeActivity;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.School;
import com.example.androidquicktrackmeet.meets.themeet.meetathletes.AddEventsToAthlete;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class MeetsActivity extends AppCompatActivity {
private ListView listView;
private MeetsListAdapter adapter;
//private Meet selectedMeet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meets);

        if(AppData.userID.equals("SRrCKcYVC8U6aZTMv0XCYHHR4BG3")){
            AppData.fullAccess = true;
        }
        else{AppData.fullAccess = false;}

        Comparator<Meet> sortByDate = (Meet o1, Meet o2) -> {
            return o1.getDate2().compareTo(o2.getDate2());
        };
        Collections.sort(AppData.meets, sortByDate);
        Collections.reverse(AppData.meets);


        adapter=new MeetsListAdapter(this, AppData.meets);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        attachListener();
// Added this code to change all 800 to 800M
//        for (int i = 0; i < AppData.allAthletes.size(); i++)
//        {
//            for(int j = 0; j < AppData.allAthletes.get(i).showEvents().size(); j++)
//            {
//                Event e = AppData.allAthletes.get(i).showEvents().get(j);
//                if(e.getName().equalsIgnoreCase("800 VAR"))
//                {
//                    AppData.allAthletes.get(i).showEvents().get(j).setName("800M VAR");
//                    AppData.allAthletes.get(i).updateFirebase();
//
//                }
//                if(e.getName().equalsIgnoreCase("800 F/S"))
//                {
//                    AppData.allAthletes.get(i).showEvents().get(j).setName("800M F/S");
//                    AppData.allAthletes.get(i).updateFirebase();
//                }
//                if(e.getName().equalsIgnoreCase("800 J/V"))
//                {
//                    AppData.allAthletes.get(i).showEvents().get(j).setName("800M J/V");
//                    AppData.allAthletes.get(i).updateFirebase();
//                }
//            }
//
//
//        }
//
//        for(int j = 0; j<AppData.meets.size(); j++){
//            Meet m = AppData.meets.get(j);
//            for (int i = 0; i < m.getEvents().size(); i++) {
//                String theEvent = m.getEvents().get(i);
//                if (theEvent.equalsIgnoreCase("800 VAR")) {
//                    m.getEvents().set(i, "800M VAR");
//                    AppData.meets.get(j).updateFirebase(m);
//                }
//                if (theEvent.equalsIgnoreCase("800 F/S")) {
//                    m.getEvents().set(i, "800M F/S");
//                    AppData.meets.get(j).updateFirebase(m);
//                }
//                if (theEvent.equalsIgnoreCase("800M J/V")) {
//                    m.getEvents().set(i, "800M J/V");
//                    AppData.meets.get(j).updateFirebase(m);
//                }
//
//            }
//
//        }

   }

//    public void addMeetAction(View view){
//        startActivity(new Intent(MeetsActivity.this, AddMeetActivity.class));
//    }


    @Override
    protected void onResume() {
        super.onResume();
        Meet.canCoach = false;
        Meet.canManage = false;
        AppData.selectedMeet = null;
//        for(int i = 0; i < AppData.meets.size(); i++)
//        {
//            if(AppData.meets.get(i) == null){
//                AppData.meets.remove(i);
//                i--;
//            }
//        }


        System.out.println("onResume happening");
        Comparator<Meet> sortByDate = (Meet o1, Meet o2) -> {
            return o1.getDate2().compareTo(o2.getDate2());
        };
        Collections.sort(AppData.meets, sortByDate);
        Collections.reverse(AppData.meets);
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
            if(AppData.userID.length()!=0) {
                Intent intent = new Intent(this, AddMeetActivity.class);
                startActivity(intent);
                return true;
            }
            else{
                AlertDialog.Builder addFailure = new AlertDialog.Builder(MeetsActivity.this);
                addFailure.setTitle("Error!");
                addFailure.setMessage("You need to be logged in to add a meet");
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

            AppData.selectedMeet = (Meet)parent.getItemAtPosition(position);
            if(AppData.selectedMeet.getUserId().equalsIgnoreCase( AppData.userID)  || AppData.fullAccess){
                Meet.canManage = true;
                Meet.canCoach = true;
                selectMeetAction(listView);
            }
            else {
                checkAccessNew();
            }
            //System.out.println("Clicked on" + AppData.selectedMeet.getName());

        }
    });
    }

    public void onFanClick(){
        System.out.println("clicked fan");
        Meet.canManage = false;
        Meet.canCoach = false;
        selectMeetAction(listView);
    }

    public void onCoachClick(){
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
            coachFailure.setNegativeButton("Ok", null);
            AlertDialog coachFailureAlert = coachFailure.create();
            coachFailure.show();
        }

    }

    public void onManageClick(){
        System.out.println("selected meet userId " + AppData.selectedMeet.getUserId());
        System.out.println("AppData.userID " + AppData.userID);


            LayoutInflater li = LayoutInflater.from(getApplicationContext());
            View promptsView = li.inflate(R.layout.meet_manager_alert, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MeetsActivity.this);

            // set alert_dialog.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            alertDialogBuilder.setNegativeButton("Cancel", null);
            alertDialogBuilder.setCancelable(true);

            EditText code = (EditText) promptsView.findViewById(R.id.meetCodeEditText);
            alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (code.getText().toString().equalsIgnoreCase(AppData.selectedMeet.getManagerCode())) {
                        Meet.canManage = true;
                        Meet.canCoach = true;
                        selectMeetAction(listView);
                    } else {
                        AlertDialog.Builder errorAlert = new AlertDialog.Builder(MeetsActivity.this);
                        errorAlert.setTitle("Error!");
                        errorAlert.setMessage("Incorrect Code");
                        errorAlert.setPositiveButton("OK", null);
                        AlertDialog errorDialog = errorAlert.create();
                        errorDialog.show();
                    }

                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

    }

    public void checkAccessNew(){
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.check_access_alert, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MeetsActivity.this);

        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setNegativeButton("Cancel", null);
        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();

        Button fanButton = (Button) promptsView.findViewById(R.id.fanButton);
        Button coachButton = (Button) promptsView.findViewById(R.id.coachButton);
        Button managerButton = (Button)promptsView.findViewById(R.id.managerButton);
        fanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFanClick();
                alertDialog.dismiss();
            }
        });
        coachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCoachClick();
                alertDialog.dismiss();
            }
        });

        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onManageClick();
                alertDialog.dismiss();
            }
        });


        // create alert dialog


        // show it
        alertDialog.show();
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