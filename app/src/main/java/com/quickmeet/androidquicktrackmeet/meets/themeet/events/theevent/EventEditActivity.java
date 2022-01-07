package com.quickmeet.androidquicktrackmeet.meets.themeet.events.theevent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.quickmeet.androidquicktrackmeet.meets.themeet.events.theevent.addathletestoevent.AddAthleteToEventActivity;
import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.Athlete;
import com.quickmeet.androidquicktrackmeet.R;
import com.quickmeet.androidquicktrackmeet.Meet;
import com.quickmeet.androidquicktrackmeet.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EventEditActivity extends AppCompatActivity  {

    //private Meet meet;
    private String selectedEvent;
    private int selectedRow;
    //private ListView listView;
    private RecyclerView recyclerView;
    private ArrayList<Athlete> eventAthletes = new ArrayList<Athlete>();
    private ArrayList<Athlete> heat1Athletes = new ArrayList<Athlete>();
    private ArrayList<Athlete> heat2Athletes = new ArrayList<Athlete>();
    private SectionAdapter adapter;
    private boolean hasSections = false;
    private boolean fieldEvent = false;
    ArrayList<Section> sections;
    //private int start = 0;
    private Button processButton, clearButton, refreshButton;
    ActionBar actionBar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.eventeditmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addToEvent) {
            if(Meet.canCoach) {
                Intent intent = new Intent(this, AddAthleteToEventActivity.class);
                //intent.putExtra("meet", meet);
                intent.putExtra("event", selectedEvent);
                intent.putExtra("athletes", eventAthletes);
                startActivity(intent);
                return true;
            }

        }
        else
        {
            switcher();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        System.out.println("onResume");
            System.out.println("onResume updating athletes showed");
            eventAthletes.clear();
            heat1Athletes.clear();
            heat2Athletes.clear();
            updateAthletesAndUI();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        processButton = (Button) this.findViewById(R.id.processEventButton);
        clearButton = (Button) this.findViewById(R.id.clearButton);
        refreshButton = (Button)this.findViewById(R.id.refreshButton);
        clearButton.setBackgroundColor(Color.GRAY);
        refreshButton.setBackgroundColor(Color.GRAY);
        //addButton = (Button)this.findViewById(R.id.addButton);

        if(Meet.canManage){
            processButton.setActivated(true);
            clearButton.setVisibility(View.VISIBLE);
        }
        else{
            processButton.setActivated(false);
            clearButton.setVisibility(View.GONE);
        }
//        if(Meet.canCoach){
//            addButton.setVisibility(View.VISIBLE);
//        }
//        else{
//            addButton.setVisibility(View.GONE);
//        }

        System.out.println("onCreate for eventEditActivity");
        Intent intent = getIntent();
        selectedEvent = (String)intent.getSerializableExtra("Selected");
        selectedRow = (Integer)intent.getSerializableExtra("selectedRow");

        if(selectedEvent.contains("100M") || selectedEvent.contains("200M") && !selectedEvent.contains("3200M")|| selectedEvent.contains("400M")){
            hasSections = true;
        }
        if(selectedEvent.contains("Jump") || selectedEvent.contains("Pole") || selectedEvent.contains("Discus")|| selectedEvent.contains("Shot")){
            fieldEvent = true;
        }
        setTitle(selectedEvent);

        //meet = (Meet)intent.getSerializableExtra("meet");

       updateAthletesAndUI();


//        adapter = new EditEventListAdapter(this, eventAthletes, selectedEvent, meet);
//        listView=(ListView)findViewById(R.id.listView);
//        listView.setAdapter(adapter);
        recyclerView = findViewById(R.id.recyclerView);
       LinearLayoutManager manager = new LinearLayoutManager(this);


         recyclerView.setLayoutManager(manager);
        sections = new ArrayList<Section>();

        if(hasSections) {

            Section one = new Section("heat 1", heat1Athletes, selectedEvent, processButton);
            Section two = new Section("heat 2", heat2Athletes, selectedEvent, processButton);
            sections.add(one);
            sections.add(two);
        }
        Section open = new Section("open", eventAthletes, selectedEvent, processButton);
        sections.add(open);
        adapter = new SectionAdapter(this, sections);


        //adapter = new EditEventListAdapter(eventAthletes,selectedEvent, processButton);
        //AthleteListAdapter.selectedAthletes.clear();
        //adapter.setClickListener(this);
        //DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable mDivider = ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.divider);
        dividerItemDecoration.setDrawable(mDivider);

        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);



        // Makes keyboard disappear when you click off editText
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//
//                return false;
//            }
//        });

//        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(topToolBar);
//        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        setSupportActionBar(topToolBar);




    }

    public void updateAthletesAndUI(){
        for(Athlete a: AppData.allAthletes) {
            for(Event e: a.showEvents()){
                if (e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName()) && e.getName().equalsIgnoreCase(selectedEvent)) {
                    if(e.getHeat()==null || e.getHeat() == 0)
                        eventAthletes.add(a);
                    else if (e.getHeat().intValue() == 1){
                        heat1Athletes.add(a);
                    }
                    else if (e.getHeat().intValue() == 2){
                        heat2Athletes.add(a);
                    }
                    else eventAthletes.add(a);
                    //break;
                }

            }

        }


        sortByName();
        sortByMark();
        sortByPlace();

        beenScoredCheck();


    }

    public void addAction(){
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
                processButton.setBackgroundColor(Color.GRAY);
                processButton.setText("Process Event");

            }
        }
    }

    public void refresh(View view){
        eventAthletes.clear();
        heat2Athletes.clear();
        heat1Athletes.clear();


        updateAthletesAndUI();

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

    public void switcher(){
            String check = selectedEvent.substring(0,selectedEvent.length()-3);
            System.out.println("selected Row before switch " + selectedRow);
            for (int i = 0; i < AppData.selectedMeet.getEvents().size(); i++){
                String checker = AppData.selectedMeet.getEvents().get(i);
                if(!checker.equalsIgnoreCase(selectedEvent) && checker.contains(check)){
                    selectedEvent = checker;
                    //adapter.changeEvent(selectedEvent);
                    for(Section s: sections){
                        s.setEvent(selectedEvent);
                    }
                    selectedRow = i;

                    setTitle(selectedEvent);
                    //setTitle(checker);

                    onResume();
                    System.out.println("selectedRow after switch " + selectedRow);
                    break;
                }
            }
    }

    public void calcPoints(ArrayList<Athlete> theAthletes){
        for(Athlete a: theAthletes){
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
                    int ties = checkForTies(event.getPlace(), theAthletes);
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
                        double rounded = (int)(points/ties*100)/100.0;
                        event.setPoints(rounded);
                    }
                    else{event.setPoints(0.0);}  // if ties somehow =0
                }
                else{event.setPoints(0.0);} // if place is not for points
            }
            else{event.setPoints(0.0);}  // if there is not place
        }

    }
    public void calcPoints(){
        System.out.println("calling calcPoints");
        ArrayList<Athlete> pointAthletes = new ArrayList<Athlete>();
        pointAthletes.addAll(eventAthletes);
        pointAthletes.addAll(heat1Athletes);
        pointAthletes.addAll(heat2Athletes);
        calcPoints(pointAthletes);
       // calcPoints(heat1Athletes);
        //calcPoints(heat2Athletes);

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
                    for(Athlete a : heat1Athletes){
                        for(Event e : a.showEvents()){
                            if(e.getName().equalsIgnoreCase(selectedEvent) && e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName()) ){
                                e.setPlace(null);
                                a.updateFirebase();
                            }
                        }
                    }

                    for(Athlete a : heat2Athletes){
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
            Integer placeo1 = o1.findEvent(AppData.selectedMeet.getName(),selectedEvent).getPlace();
            Integer placeo2 = o2.findEvent(AppData.selectedMeet.getName(),selectedEvent).getPlace();

                if (placeo1 == null) {
                    return (placeo2 == null) ? 0 : 1;
                }
                if (placeo2 == null) {
                    return -1;
                }
                return placeo1.compareTo(placeo2);

        };

        Collections.sort(eventAthletes, sortByPlace);
        Collections.sort(heat1Athletes, sortByPlace);
        Collections.sort(heat2Athletes, sortByPlace);

    }

    public void markAction(View view){
        System.out.println("Mark button pushed");
        sortByMark();
        adapter.notifyDataSetChanged();
    }

    public void sortByMark(){
        Comparator<Athlete> sortByMark = (Athlete o1, Athlete o2) -> {
            String marko1 = o1.findEvent(AppData.selectedMeet.getName(), selectedEvent).getMarkString();
            String marko2 = o2.findEvent(AppData.selectedMeet.getName(), selectedEvent).getMarkString();
            if(!marko1.equalsIgnoreCase("")) {
                while (marko1.length() < marko2.length()) {
                    marko1 = 0 + marko1;
                }
            }
            if(!marko2.equalsIgnoreCase("")) {
                while (marko2.length() < marko1.length()) {
                    marko2 = 0 + marko2;
                }
            }
            if(!fieldEvent) {
                if (marko1.equalsIgnoreCase("")) {
                    return (marko2.equalsIgnoreCase("")) ? 0 : 1;
                }
                if (marko2.equalsIgnoreCase("")) {
                    return -1;
                }
                return marko1.compareTo(marko2);
            }
            else{
                if (marko1.equalsIgnoreCase("")) {
                    return (marko2.equalsIgnoreCase("")) ? 0 : 1;
                }
                if (marko2.equalsIgnoreCase("")) {
                    return -1;
                }
                return -1*(marko1.compareTo(marko2));
            }
        };
        Collections.sort(eventAthletes, sortByMark);
        Collections.sort(heat1Athletes, sortByMark);
        Collections.sort(heat2Athletes, sortByMark);

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
        Collections.sort(heat1Athletes, sortByName);
        Collections.sort(heat2Athletes, sortByName);
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
        Collections.sort(heat1Athletes, sortBySchool);
        Collections.sort(heat2Athletes, sortBySchool);
       // System.out.println(eventAthletes);
    }


}