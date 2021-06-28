package com.example.androidquicktrackmeet;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Meet {

    public static boolean canManage = false;
    public static boolean canCoach = false;
    private String name, gender,uid, coachCode = "", managerCode = "", userId = "";
    private Date date;
    private Map<String,String> schools;
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

    schools = (Map<String, String>) dict.get("schools");

    List levels2 = (List<String>)dict.get("levels");

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




}
