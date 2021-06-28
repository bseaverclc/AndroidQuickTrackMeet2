package com.example.androidquicktrackmeet;

import java.util.HashMap;
import java.util.ArrayList;

public class School {
    private String full, inits, uid;
    private ArrayList<String> coaches;

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
    public ArrayList<String> getCoaches(){return coaches;}

    public void addCoach(String email){
        coaches.add(email);
    }





}
