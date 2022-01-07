package com.quickmeet.androidquicktrackmeet.meets.themeet.events.theevent.addathletestoevent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.Athlete;
import com.quickmeet.androidquicktrackmeet.Meet;
import com.quickmeet.androidquicktrackmeet.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AddAthleteToEventActivity extends AppCompatActivity implements AthleteListAdapter.ItemClickListener {

    //private Meet meet;
    private String event;
    private ArrayList<Athlete> eventAthletes;
    private String level;
    //private ListView listView;

    private ArrayList<Athlete> displayedAthletes = new ArrayList<Athlete>();
    private AthleteListAdapter adapter;
    SparseBooleanArray sp;
    RecyclerView recyclerView;
    ActionBar actionBar;
   // ArrayList<Athlete> selectedAthletes = new ArrayList<Athlete>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            addToRoster();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_athlete_to_event);

        Intent intent = getIntent();
        //meet = (Meet)intent.getSerializableExtra("meet");

        event = (String)intent.getSerializableExtra("event");
        level = event.substring(event.length()-3);
        eventAthletes = (ArrayList<Athlete>)intent.getSerializableExtra("athletes");
        setTitle(event);

        for(Athlete a: AppData.allAthletes){
//            if(AppData.selectedMeet.getSchools().keySet().contains(a.getSchoolFull())){
//                displayedAthletes.add(a);
//            }
            if(a.getSchoolFull().equalsIgnoreCase(AppData.mySchool)){
                displayedAthletes.add(a);
            }
        }

        Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {

            return o1.getLast().compareTo(o2.getLast());
        };
        Collections.sort(displayedAthletes, sortByName);

        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(findViewById(R.id.school1));
        buttons.add(findViewById(R.id.school2));
        buttons.add(findViewById(R.id.school3));
        buttons.add(findViewById(R.id.school4));
        ArrayList<String> initials = new ArrayList<String>(AppData.selectedMeet.getSchools().values());
        int buttonNumber = 0;
        for (String i: initials){
            buttons.get(buttonNumber).setText(i);
            buttonNumber+=1;

        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AthleteListAdapter(this, displayedAthletes);
        AthleteListAdapter.selectedAthletes.clear();
        AthleteListAdapter.selectedPositions.clear();
        adapter.setClickListener(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

// Keep getting an error when trying to add the add athlete button to top bar
//        Toolbar topToolBar2 = (Toolbar) findViewById(R.id.toolbar2);
//       // setSupportActionBar(topToolBar2);
//        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        setSupportActionBar(topToolBar2);
    }

    public void addAthletes(){
        for(int i = 0; i < AthleteListAdapter.selectedAthletes.size(); i++){
            Athlete a = AthleteListAdapter.selectedAthletes.get(i);

            //System.out.println("athlete checking to add " + a);
            int stop = eventAthletes.size();
            boolean add = true;
            for(int j = 0; j< stop; j++) {
                if (eventAthletes.get(j).equals(a)) {
                    add = false;
                    break;
                }
            }
            if(add){
                a.addEvent(event, level, AppData.selectedMeet.getName());
                //eventAthletes.add(a);

                //a.updateFirebase();
            }
            else{
                System.out.println("duplicate athlete");
            }
        }
        System.out.println("addAthletes function ending");
        AthleteListAdapter.selectedAthletes.clear();
        AthleteListAdapter.selectedPositions.clear();
    }

    public void selectAthletesAction(View view){
        //System.out.println("event athletes "+ eventAthletes);
        addAthletes();

//        Intent intent = new Intent();
//        intent.putExtra("eventAthletes", eventAthletes);
//        setResult(RESULT_OK, intent);
        finish();

    }

    public void addToRoster(){
        System.out.println("addToRoster being called");
        Intent intent = new Intent(this, AddAthlete.class);
        //intent.putExtra("meet", meet);
        intent.putExtra("event", event);
        intent.putExtra("level", level);
        intent.putExtra("athletes", eventAthletes);
        startActivity(intent);
    }

    public void selectSchool(View view){
        if(Meet.canManage) {
            addAthletes();
            Button button = (Button) view;
            String buttonText = button.getText().toString();
            System.out.println("select school" + buttonText);


            displayedAthletes.clear();
            for (Athlete a : AppData.allAthletes) {
                if (a.getSchool().equalsIgnoreCase(buttonText) && AppData.selectedMeet.getSchools().containsKey(a.getSchoolFull())) {
                    displayedAthletes.add(a);
                    System.out.println("added an athlete");
                }
            }

            Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {

                return o1.getLast().compareTo(o2.getLast());
            };
            Collections.sort(displayedAthletes, sortByName);
            adapter.notifyDataSetChanged();
        }
        else{
            AlertDialog.Builder coachFailure = new AlertDialog.Builder(AddAthleteToEventActivity.this);
            coachFailure.setTitle("Failure");
            coachFailure.setMessage("You can only make changes to your team");
            coachFailure.setNegativeButton("Ok", null);
            AlertDialog coachFailureAlert = coachFailure.create();
            coachFailure.show();
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        System.out.println("clicked on name");
        //view.setSelected(true);
        //view.setBackgroundColor(Color.GREEN);

////
////
////
////                for(int i=0;i<sp.size();i++)
////                {
////                    listView.setItemChecked(i, true);
////                   System.out.println(sp.keyAt(i));
////                }

    }




//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                System.out.println("clicked an athlete");
//                //SparseBooleanArray sp = listView.getCheckedItemPositions();
////
////
////
////                for(int i=0;i<sp.size();i++)
////                {
////                    listView.setItemChecked(i, true);
////                   System.out.println(sp.keyAt(i));
////                }
//
//                //String item = ((TextView) view).getText().toString();
//                view.setSelected(true);
//                listView.setItemChecked(position, true);
//               for (int i = 0; i < sp.size(); i++){
//                   if (sp.valueAt(i)){
//                       view.setBackgroundColor(Color.GREEN);
//                   }
//               }
//
//
//
//
//            }
//        });

}