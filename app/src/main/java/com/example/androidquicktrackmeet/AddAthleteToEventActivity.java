package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
   // ArrayList<Athlete> selectedAthletes = new ArrayList<Athlete>();

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
            if(AppData.selectedMeet.getSchools().keySet().contains(a.getSchoolFull())){
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

//        adapter=new AthleteListAdapter(this, displayedAthletes);
//        listView=(ListView)findViewById(R.id.listView);
//
//        listView.setAdapter(adapter);
//
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//        sp = listView.getCheckedItemPositions();


        //attachListener();

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

        Intent intent = new Intent();
        intent.putExtra("eventAthletes", eventAthletes);
        setResult(RESULT_OK, intent);
        finish();

    }

    public void selectSchool(View view){
        addAthletes();
        Button button = (Button)view;
        String buttonText = button.getText().toString();
        System.out.println("select school" + buttonText);
        displayedAthletes.clear();
        for(Athlete a : AppData.allAthletes){
            if(a.getSchool().equalsIgnoreCase(buttonText) && AppData.selectedMeet.getSchools().containsKey(a.getSchoolFull())){
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