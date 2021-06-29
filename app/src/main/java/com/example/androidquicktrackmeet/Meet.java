package com.example.androidquicktrackmeet;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Meet implements Serializable {

    public static boolean canManage = false;
    public static boolean canCoach = false;
    private String name, gender,uid, coachCode = "", managerCode = "", userId = "";
    private Date date;
    private HashMap<String,String> schools;
    private ArrayList<String> levels, events;
    private ArrayList<Integer> indPoints, relPoints;
    private ArrayList<Boolean> beenScored;

public Meet(){}


public Meet(String key, HashMap<String, Object> dict) {
    this.uid = key;
    name = (String) dict.get("name");

    String sDate1 = (String) dict.get("date");
    try {
        assert sDate1 != null;
        Date date1 = new SimpleDateFormat("MM/dd/yy", Locale.US).parse(sDate1);
    } catch (Exception e) {
        System.out.println("not a valid date!");
        date = new Date();
    }

    gender = (String) dict.get("gender");

    schools = (HashMap<String, String>) dict.get("schools");

    levels = (ArrayList<String>)dict.get("levels");

    events = (ArrayList<String>) dict.get("events");

    indPoints = (ArrayList<Integer>) dict.get("indPoints");

    relPoints = (ArrayList<Integer>) dict.get("relPoints");

    beenScored = (ArrayList<Boolean>) dict.get("beenScored");

    coachCode = (String) dict.get("coachCode");
    managerCode = (String) dict.get("managerCode");
    userId = (String) dict.get("userId");


}


public Meet(String name, Date date, HashMap<String,String> schools, String gender, ArrayList<String> levels, ArrayList<String> events,
            ArrayList<Integer> indPoints, ArrayList<Integer> relPoints, ArrayList<Boolean> beenScored, String coachCode, String managerCode){
    this.name = name;
    this.date = date;
    this.schools = schools;
    this.gender = gender;
    this.levels = levels;
    this.events = events;
    this.indPoints = indPoints;
    this.relPoints = relPoints;
    this.beenScored = beenScored;
    this.coachCode = coachCode;
    this.managerCode = managerCode;
    this.userId = AppData.userID;
}

    public String getName(){return name;}
    public Date getDate2(){return date;}
    public HashMap<String,String> getSchools(){return schools;}
    public String getGender(){return gender;}

    public ArrayList<String> getLevels(){return levels;}
    public ArrayList<String> getEvents(){return events;}
    public ArrayList<Integer> getIndPoints(){return indPoints;}
    public ArrayList<Integer> getRelPoints(){return relPoints;}
    public ArrayList<Boolean> getBeenScored(){return beenScored;}
    public String getCoachCode(){return coachCode;}
    public String getManagerCode(){return managerCode;}

    public void addDate(Date d){
    date = d;
    }



}
