package com.example.androidquicktrackmeet;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.ArrayList;

public class School {
    private String full, inits, uid;
    private ArrayList<String> coaches = new ArrayList<String>();

    public School(){}

    public School(String full, String inits){
        this.full = full;
        this.inits = inits;
    }

    public School(String key, HashMap<String, Object> dict){
        uid = key;
        full = (String)dict.get("full");
        inits = (String)dict.get("inits");

        if (dict.get("coaches") != null){
            coaches = (ArrayList<String>)dict.get("coaches");
        }
    }

    public String getFull(){return full; }
    public String getInits(){return inits;}
    public String showUid(){return uid;}
    public void setUid(String uid){this.uid = uid;}
    public ArrayList<String> getCoaches(){return coaches;}

    public void addCoach(String email){
        coaches.add(email);
    }

    public void saveToFirebase()
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("schoolsNew").push();
        uid = mDatabase.getKey();

        HashMap<String,Object> hash = new HashMap<String,Object>();

        hash.put("full", full);
        hash.put("inits", inits);
        hash.put("coaches", coaches);
        mDatabase.setValue(hash);

    }

    public void updateFirebase()
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("schoolsNew").child(this.uid);

        HashMap<String,Object> hash = new HashMap<String,Object>();
        hash.put("full", full);
        hash.put("inits", inits);
        hash.put("coaches", coaches);
        mDatabase.updateChildren(hash);
    }





}
