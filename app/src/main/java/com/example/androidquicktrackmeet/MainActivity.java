package com.example.androidquicktrackmeet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
   private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private TextView signInText;
    private SignInButton googleSignInButton;
    private Button emailSignInButton;
    private Button logOutButton;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        signInText = findViewById(R.id.signInText);
        googleSignInButton = findViewById((R.id.sign_in_button));
        emailSignInButton = findViewById(R.id.emailButton);
        logOutButton = findViewById(R.id.logOutButton);
        logOutButton.setVisibility(View.GONE);

        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            signInText.setText(account.getEmail() + " from google");
            googleSignInButton.setVisibility(View.GONE);
            emailSignInButton.setVisibility(View.GONE);
            logOutButton.setVisibility(View.VISIBLE);
            AppData.coach = account.getEmail();
            AppData.userID = account.getId();


        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            signInText.setText(currentUser.getEmail() + " from email");
            googleSignInButton.setVisibility(View.GONE);
            emailSignInButton.setVisibility(View.GONE);
            logOutButton.setVisibility(View.VISIBLE);
        }



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent,1);
            }


        });

        readAthletesFromFirebase();
        readSchoolsFromFirebase();
        readMeetsFromFirebase();

    }

    @Override
    protected void onStart() {
        super.onStart();



        //updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }



    public void checkSignInEmail(String emailIn, String passwordIn){
        mAuth.signInWithEmailAndPassword(emailIn, passwordIn)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("checkSignInEmail", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            AppData.userID = user.getUid();
                            signInText.setText(user.getEmail());
                            for (School school : AppData.schools){
                                for(String coach: school.getCoaches()){
                                    if (coach.equalsIgnoreCase(user.getEmail())){
                                        AppData.mySchool = school.getFull();
                                        AppData.coach = coach;
                                    }
                                }
                            }
                            googleSignInButton.setVisibility(View.GONE);
                            emailSignInButton.setVisibility(View.GONE);
                            logOutButton.setVisibility(View.VISIBLE);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("checkSignInEmail", "signInWithEmail:failure " + task.getException());

                            //updateUI(null);
                        }
                    }
                });
    }

    public void logOut(View view){
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut();
        signInText.setText("Not Logged in");
        AppData.userID = "";
        AppData.coach = "";
        AppData.mySchool = "";
        googleSignInButton.setVisibility(View.VISIBLE);
        emailSignInButton.setVisibility(View.VISIBLE);
        logOutButton.setVisibility(View.GONE);
    }

    public void signInEmail(View view){
        // get alert_dialog.xml view
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.email_login, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

         EditText emailEditText = (EditText) promptsView.findViewById(R.id.email);
        EditText passwordEditText = (EditText) promptsView.findViewById(R.id.password);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        Log.i("email", emailEditText.getText().toString());
                        Log.i("password",passwordEditText.getText().toString());
                        checkSignInEmail(emailEditText.getText().toString(),passwordEditText.getText().toString());

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

//    public void didSignIn(){
//
//

//            nameOutlet.text = "\(user.email!)"
//            logInOutlet.isHidden = true
//            logOutOutlet.isHidden = false
//            removeAccountOutlet.isHidden = false
//            authorizationButton.isHidden = true
//            emailButtonOutlet.isHidden = true
//        }
//       else{
//            nameOutlet.text = "Not Logged in"
//            logInOutlet.isHidden = false
//            logOutOutlet.isHidden = true
//            removeAccountOutlet.isHidden = true
//            authorizationButton.isHidden = false
//            emailButtonOutlet.isHidden = false
//        }
//    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            signInText.setText(account.getEmail());
            googleSignInButton.setVisibility(View.GONE);
            emailSignInButton.setVisibility(View.GONE);
            logOutButton.setVisibility(View.VISIBLE);


            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            Log.i("MainActivity", "Logged in as " + account.getEmail());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("MainActivity", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    public void readMeetsFromFirebase() {
        //        Read from the database
        DatabaseReference meetDatabase = FirebaseDatabase.getInstance().getReference();
        meetDatabase.child("meets").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previous) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String key = dataSnapshot.getKey();
                Meet m = dataSnapshot.getValue((Meet.class));
                m.setUid(key);
                for(int i =0; i < AppData.meets.size(); i++){
                    if (AppData.meets.get(i).getName().equalsIgnoreCase(m.getName())){
                        AppData.meets.set(i, m);
                        Log.i("MainActivity", "meet has been changed");
                        break;
                    }
                }

            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previous) {
                //  System.out.println(dataSnapshot);

                String key = dataSnapshot.getKey();
                //  System.out.println("key is "+ key);

                Meet m = dataSnapshot.getValue((Meet.class));
                m.setUid(key);
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
               // System.out.println(m.getDate2());
              //  System.out.println("total meets added "+ AppData.meets.size());



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
               // System.out.println("athlete child changed");
                String key = dataSnapshot.getKey();
                Athlete a = dataSnapshot.getValue((Athlete.class));
                dataSnapshot = dataSnapshot.child("events");

                for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Event user = snapShot.getValue(Event.class);
                    a.addEvent(user);
                }


                a.setUid(key);
                for(int i =0; i< AppData.allAthletes.size(); i++){
                    if(AppData.allAthletes.get(i).getUid().equalsIgnoreCase(key)){
                        AppData.allAthletes.set(i, a);
                       // System.out.println("changed in firebase " + AppData.allAthletes.get(i).getLast());
                        break;
                    }
                }

//                for i in 0..<AppData.allAthletes.count{
//                    if(AppData.allAthletes[i].uid == uid){
//                        AppData.allAthletes[i] = a
//                        print("Athlete \(i)Changed \(AppData.allAthletes[i].last)")
//                    }
//
//
//                }


            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previous) {
                //  System.out.println(dataSnapshot);

                String key = dataSnapshot.getKey();
                //  System.out.println("key is "+ key);

                Athlete a = dataSnapshot.getValue((Athlete.class));
                a.setUid(key);
               // System.out.println("Athletes uid " + a.getUid());


                AppData.allAthletes.add(a);
                //System.out.println("printing athletes" + AppData.allAthletes);
                //System.out.println("allAthletes count "+ AppData.allAthletes.size());

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
                            //System.out.println("event snapshot" + dataSnapshot);
                            Event ev = dataSnapshot.getValue(Event.class);


                            ev.setUid(dataSnapshot.getKey());
                          //  System.out.println("Added event to firebase Event Uid " + ev.getUid());

                            a.addEvent(ev);

                            //System.out.println("getting an event from firebase ");
//                            for (Event e : a.showEvents()) {
//                                System.out.println(e.getRelayMembers());
//
//                            }
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
        //System.out.println("meets pushed");
        startActivity(new Intent(MainActivity.this, MeetsActivity.class));
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//
//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }

    public void schoolsAction(View view){
      //  System.out.println("schools pushed");
        startActivity(new Intent(MainActivity.this, SchoolsActivity.class));
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//
//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }
}