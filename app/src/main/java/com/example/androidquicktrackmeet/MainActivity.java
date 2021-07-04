package com.example.androidquicktrackmeet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        readAthletesFromFirebase();
        readSchoolsFromFirebase();
        readMeetsFromFirebase();

    }

    public void readMeetsFromFirebase() {
        //        Read from the database
        DatabaseReference meetDatabase = FirebaseDatabase.getInstance().getReference();
        meetDatabase.child("meets").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previous) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previous) {
                //  System.out.println(dataSnapshot);

                String key = dataSnapshot.getKey();
                //  System.out.println("key is "+ key);

                Meet m = dataSnapshot.getValue((Meet.class));
                String sDate1 = (String)dataSnapshot.child("date").getValue();

                // Adding the Date to the Meet
                try {
                    assert sDate1 != null;
                    m.addDate(new SimpleDateFormat("MM/dd/yy", Locale.US).parse(sDate1));
                } catch (Exception e) {
                    System.out.println("not a valid date!");
                    m.addDate(new Date());
                }



                AppData.meets.add(m);
                System.out.println(m.getDate2());
                System.out.println("total meets added "+ AppData.meets.size());



                //adapter.notifyDataSetChanged();
                //Log.d(TAG, "Value is: " + value);
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }


            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("failed to read from database");
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void readAthletesFromFirebase(){
        //        Read from the database
        mDatabase.child("athletes").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previous) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previous) {
                //  System.out.println(dataSnapshot);

                String key = dataSnapshot.getKey();
                //  System.out.println("key is "+ key);

                Athlete a = dataSnapshot.getValue((Athlete.class));


                AppData.allAthletes.add(a);
                //System.out.println("printing athletes" + AppData.allAthletes);
                System.out.println("allAthletes count "+ AppData.allAthletes.size());

                mDatabase.child("athletes").child(key).child("events").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String previous) {

                    }

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previous) {

//                        ArrayList<Event> myEvents = new ArrayList<Event>();
//                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                            Event e = ds.getValue(Event.class);
//                            myEvents.add(e);
//                            System.out.println(e);
//                        }
                        if(dataSnapshot.getKey().equalsIgnoreCase("relayMembers")){

                        }
                        else {
                            System.out.println("event snapshot" + dataSnapshot);
                            Event ev = dataSnapshot.getValue(Event.class);
                            a.addEvent(ev);
                            ev.setUid(dataSnapshot.getKey());

                            //System.out.println("getting an event from firebase ");
                            for (Event e : a.showEvents()) {
                                System.out.println(e.getRelayMembers());

                            }
                            //adapter.notifyDataSetChanged();
                        }
                    }

                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot snapshot,String previousChildName) {

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.out.println("failed to read from database");
                        // Failed to read value
                        //Log.w(TAG, "Failed to read value.", error.toException());
                    }

                });

                //adapter.notifyDataSetChanged();
                //Log.d(TAG, "Value is: " + value);
            }
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot,String previousChildName) {

            }


            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("failed to read from database");
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void readSchoolsFromFirebase(){
        DatabaseReference sdatbase = FirebaseDatabase.getInstance().getReference().child("schoolsNew");
        sdatbase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                School s = snapshot.getValue((School.class));
                AppData.schools.add(s);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void testAddAthleteToFirebase(){
        // Add an athlete to firebase

//        Athlete a = new Athlete("Event Athlete", "Seaver", "CLC", 12, "Crystal Lake Central");
//        athletes.add(a);
//        a.saveToFirebase();
//        a.addEvent("Test Event", "VAR", "TestMeet");
        //athletes.add(new Athlete("Jeff", "Owen", "CLC",10, "Crystal Lake Central"));
    }

    public void meetsAction(View view){
        System.out.println("meets pushed");
        startActivity(new Intent(MainActivity.this, MeetsActivity.class));
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//
//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }

    public void schoolsAction(View view){
        System.out.println("schools pushed");
        startActivity(new Intent(MainActivity.this, SchoolsActivity.class));
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//
//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }
}