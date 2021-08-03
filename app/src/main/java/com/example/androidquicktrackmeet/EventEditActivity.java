package com.example.androidquicktrackmeet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EventEditActivity extends AppCompatActivity {

    //private Meet meet;
    private String selectedEvent;
    private int selectedRow;
    //private ListView listView;
    private RecyclerView recyclerView;
    private ArrayList<Athlete> eventAthletes = new ArrayList<Athlete>();
    private EditEventListAdapter adapter;
    //private int start = 0;
    private Button processButton, clearButton, addButton;
    ActionBar actionBar;


    @Override
    protected void onResume(){
        super.onResume();
        System.out.println("onResume");
            System.out.println("onResume updating athletes showed");
            eventAthletes.clear();
            for (Athlete a : AppData.allAthletes) {
                for (Event e : a.showEvents()) {
                    if (e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName()) && e.getName().equalsIgnoreCase(selectedEvent)) {
                        eventAthletes.add(a);
                        //break;
                    }

                }

            }




            sortByName();
            sortByMark();
            sortByPlace();
            beenScoredCheck();

            adapter.notifyDataSetChanged();


        //start++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        processButton = (Button) this.findViewById(R.id.procesEventButton);
        clearButton = (Button) this.findViewById(R.id.clearButton);
        addButton = (Button)this.findViewById(R.id.addButton);

        if(Meet.canManage){
            processButton.setActivated(true);
            clearButton.setVisibility(View.VISIBLE);
        }
        else{
            processButton.setActivated(false);
            clearButton.setVisibility(View.GONE);
        }
        if(Meet.canCoach){
            addButton.setVisibility(View.VISIBLE);
        }
        else{
            addButton.setVisibility(View.GONE);
        }

        System.out.println("onCreate for eventEditActivity");
        Intent intent = getIntent();
        selectedEvent = (String)intent.getSerializableExtra("Selected");
        selectedRow = (Integer)intent.getSerializableExtra("selectedRow");

        setTitle(selectedEvent);

        //meet = (Meet)intent.getSerializableExtra("meet");

        for(Athlete a: AppData.allAthletes) {
            for(Event e: a.showEvents()){
                if (e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName()) && e.getName().equalsIgnoreCase(selectedEvent)) {
                    eventAthletes.add(a);
                    //break;
                }

            }

        }


        sortByName();
        sortByPlace();
        sortByMark();
        beenScoredCheck();


//        adapter = new EditEventListAdapter(this, eventAthletes, selectedEvent, meet);
//        listView=(ListView)findViewById(R.id.listView);
//        listView.setAdapter(adapter);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EditEventListAdapter(this, eventAthletes,selectedEvent, processButton);
        //AthleteListAdapter.selectedAthletes.clear();
        //adapter.setClickListener(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        // attach delete option to recyclerview
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Makes keyboard disappear when you click off editText
        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });

        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        actionBar = getSupportActionBar();;
        actionBar.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(topToolBar);


    }

    public void addAction(View view){
        System.out.println("addAction being called");
        Intent intent = new Intent(this, AddAthleteToEventActivity.class);
        //intent.putExtra("meet", meet);
        intent.putExtra("event", selectedEvent);
        intent.putExtra("athletes", eventAthletes);
        startActivity(intent);

    }

    public void beenScoredCheck(){
        if(AppData.selectedMeet != null) {
            if (AppData.selectedMeet.getBeenScored().get(selectedRow) == true) {
                processButton.setBackgroundColor(Color.GREEN);
                processButton.setText("Processed");

            } else {
                processButton.setBackgroundColor(Color.LTGRAY);
                processButton.setText("Process Event");

            }
        }
    }

    public void refresh(View view){
        eventAthletes.clear();
        for(Athlete a: AppData.allAthletes) {
            for(Event e: a.showEvents()){
                if (e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName()) && e.getName().equalsIgnoreCase(selectedEvent)) {
                    eventAthletes.add(a);
                    //break;
                }

            }

        }


        sortByName();
        sortByMark();
        sortByPlace();

        beenScoredCheck();

        adapter.notifyDataSetChanged();
    }

    public void processEvent(View view) {

        if (Meet.canManage) {
            processButton.setBackgroundColor(Color.GREEN);
            processButton.setText("Processed");
            AppData.selectedMeet.setBeenScored(selectedRow, true);
            AppData.selectedMeet.updatebeenScoredFirebase();
            calcPoints();
            for (Athlete a : eventAthletes) {
                a.updateFirebase();
            }

        }
    }

    public void switcher(View view){
            String check = selectedEvent.substring(0,selectedEvent.length()-3);
            System.out.println("selected Row before switch " + selectedRow);
            for (int i = 0; i < AppData.selectedMeet.getEvents().size(); i++){
                String checker = AppData.selectedMeet.getEvents().get(i);
                if(!checker.equalsIgnoreCase(selectedEvent) && checker.contains(check)){
                    selectedEvent = checker;
                    adapter.changeEvent(selectedEvent);
                    selectedRow = i;

                    actionBar.setTitle(selectedEvent);
                    //setTitle(checker);

                    onResume();
                    System.out.println("selectedRow after switch " + selectedRow);
                    break;
                }
            }
    }

    public void calcPoints(){
        System.out.println("calling calcPoints");
        for(Athlete a: eventAthletes){
           Event event =  a.findEvent( AppData.selectedMeet.getName(), selectedEvent);
           System.out.println(event.getName() + " points being calculated");
           if(event != null && event.getPlace()!= null) {
               ArrayList<Integer> scoring = new ArrayList<Integer>();
               if (event.getMeetName().contains("4x")){
                   scoring = AppData.selectedMeet.getRelPoints();
               }
               else{scoring = AppData.selectedMeet.getIndPoints();}
               System.out.println("scoring size " + scoring.size());

               if(event.getPlace() <= scoring.size()){
                    int ties = checkForTies(event.getPlace(), eventAthletes);
                    System.out.println("Number of ties " + ties);
                    double points = 0.0;
                    if(ties !=0){
                        for(int p = event.getPlace() - 1; p < event.getPlace() - 1 + ties; p++){
                            if(p>scoring.size() -1){
                                points += 0;
                            }
                            else{
                                points += scoring.get(p);
                                System.out.println("Added points for " + a.getLast());
                            }
                        }
                        event.setPoints(points/ties);
                    }
                    else{event.setPoints(0.0);}  // if ties somehow =0
               }
               else{event.setPoints(0.0);} // if place is not for points
           }
           else{event.setPoints(0.0);}  // if there is not place
        }
        adapter.notifyDataSetChanged();
    }

    public int checkForTies(Integer place, ArrayList<Athlete> athletes){
        int ties = 0;
        for(Athlete a: athletes){
            Event event = a.findEvent(AppData.selectedMeet.getName(),this.getTitle().toString() );
            if(event != null && event.getPlace() != null){
                if (event.getPlace().equals(place)){
                    ties = ties + 1;
                }
            }
        }
        return ties;
    }


    public void clearPlaces(View view){

            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert!");
            builder.setMessage("Are you sure you want to clear all places?");
            // add the buttons
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do something like...
                   System.out.println("clicked yes");
                   for(Athlete a : eventAthletes){
                       for(Event e : a.showEvents()){
                           if(e.getName().equalsIgnoreCase(selectedEvent) && e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName()) ){
                               e.setPlace(null);
                               a.updateFirebase();
                           }
                       }
                   }
                   AppData.selectedMeet.getBeenScored().set(selectedRow, false);
                   AppData.selectedMeet.updatebeenScoredFirebase();
                   beenScoredCheck();
                   calcPoints();
                   adapter.notifyDataSetChanged();

//                    if Meet.canManage{
//                        for a in eventAthletes{
//                            for e in a.events{
//                                if e.name == title  && e.meetName == meet.name{
//                                    e.place = nil
//                                    a.updateFirebase()
//                                }
//
//                            }
//                        }
//                        meet.beenScored[selectedRow] = false
//                        meet.updatebeenScoredFirebase()
//                        processOutlet.backgroundColor = UIColor.lightGray
//                        processOutlet.setTitle("Process Event", for: .normal)
//                        calcPoints()
//                        tableViewOutlet.reloadData()
//
//                    }

                }
            });
            builder.setNegativeButton("Cancel", null);
            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }


    public void placeAction(View view){
        System.out.println("Place button pushed");
        sortByPlace();
        adapter.notifyDataSetChanged();
    }

    public void sortByPlace(){
        Comparator<Athlete> sortByPlace = (Athlete o1, Athlete o2) -> {
            if (o1.findEvent(AppData.selectedMeet.getName(), selectedEvent).getPlace() == null) {
                return (o2.findEvent(AppData.selectedMeet.getName(), selectedEvent).getPlace() == null) ? 0 : 1;
            }
            if (o2.findEvent(AppData.selectedMeet.getName(), selectedEvent).getPlace() == null) {
                return -1;
            }
            return o1.findEvent(AppData.selectedMeet.getName(), selectedEvent).getPlace().compareTo(o2.findEvent(AppData.selectedMeet.getName(), selectedEvent).getPlace());
        };
        Collections.sort(eventAthletes, sortByPlace);
        //System.out.println(eventAthletes);

    }

    public void markAction(View view){
        System.out.println("Mark button pushed");
        sortByMark();
        adapter.notifyDataSetChanged();
    }

    public void sortByMark(){
        Comparator<Athlete> sortByMark = (Athlete o1, Athlete o2) -> {
            if (o1.findEvent(AppData.selectedMeet.getName(), selectedEvent).getMarkString().equalsIgnoreCase("")) {
                return (o2.findEvent(AppData.selectedMeet.getName(), selectedEvent).getMarkString().equalsIgnoreCase("")) ? 0 : 1;
            }
            if (o2.findEvent(AppData.selectedMeet.getName(), selectedEvent).getMarkString().equalsIgnoreCase("")) {
                return -1;
            }
            return o1.findEvent(AppData.selectedMeet.getName(), selectedEvent).getMarkString().compareTo(o2.findEvent(AppData.selectedMeet.getName(), selectedEvent).getMarkString());
        };
        Collections.sort(eventAthletes, sortByMark);
        //System.out.println(eventAthletes);

    }

    public void nameAction(View view){
        System.out.println("Place button pushed");
        sortByName();
        adapter.notifyDataSetChanged();
    }

    public void sortByName(){
        Comparator<Athlete> sortByName = (Athlete o1, Athlete o2) -> {
            if (o1.getLast() == null) {
                return (o2.getLast() == null) ? 0 : 1;
            }
            if (o2.getLast()== null) {
                return -1;
            }
            return o1.getLast().compareTo(o2.getLast());
        };
        Collections.sort(eventAthletes, sortByName);
       // System.out.println(eventAthletes);

    }

    public void schoolAction(View view){
        System.out.println("Place button pushed");
        sortBySchool();
        adapter.notifyDataSetChanged();

    }

    public void sortBySchool(){
        Comparator<Athlete> sortBySchool = (Athlete o1, Athlete o2) -> {
            if (o1.getSchool() == null) {
                return (o2.getSchool() == null) ? 0 : 1;
            }
            if (o2.getSchool()== null) {
                return -1;
            }
            return o1.getSchool().compareTo(o2.getSchool());
        };
        Collections.sort(eventAthletes, sortBySchool);
       // System.out.println(eventAthletes);
    }


}