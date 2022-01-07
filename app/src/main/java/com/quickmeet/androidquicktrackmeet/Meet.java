package com.quickmeet.androidquicktrackmeet;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
    public String getUserId(){return userId;}

    public void addDate(Date d){
    date = d;
    }

    public void setUid(String uid){this.uid = uid;}

    public void setBeenScored(int position, boolean value){
     beenScored.set(position, value);
     System.out.println(beenScored);
    }


    public void updatebeenScoredFirebase(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("meets").child(this.uid);
        HashMap<String, Object> bs = new HashMap<String, Object>();
        bs.put("beenScored", beenScored);
        ref.updateChildren(bs);
    }

    public void updateFirebase(Meet m)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("meets").child(this.uid);

        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yy");
        String datey = outputFormat.format(m.getDate2());
        System.out.println("updating firebase with date " + datey);
        addDate(m.date);


        HashMap<String,Object> hash = new HashMap<String,Object>();
        hash.put("name", m.getName());
        hash.put("date", datey);
        hash.put("schools", m.getSchools());
        hash.put("gender", m.getGender());
        hash.put("levels", m.getLevels());
        hash.put("events", m.getEvents());
        hash.put("indPoints", m.getIndPoints());
        hash.put("relPoints", m.getRelPoints());
        hash.put("beenScored", m.getBeenScored());
        hash.put("coachCode", m.getCoachCode());
        hash.put("managerCode", m.getManagerCode());
        hash.put("userId", m.getUserId());

        mDatabase.updateChildren(hash);

    }

    public void saveToFirebase(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("meets").push();
        uid = mDatabase.getKey();
        this.setUid(uid);

//        let dict = ["name": self.name, "date": dateString, "schools": self.schools,
//                "gender":self.gender, "levels":self.levels, "events": self.events,
//                "indPoints":self.indPoints, "relPoints": self.relPoints,
//                "beenScored": self.beenScored, "coachCode": coachCode,
//                "managerCode": managerCode, "userId": userId] as [String : Any]

        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yy");
        String datey = outputFormat.format(date);


        HashMap<String,Object> hash = new HashMap<String,Object>();
        hash.put("name", name);
        hash.put("date", datey);
        hash.put("schools", schools);
        hash.put("gender", gender);
        hash.put("levels", levels);
        hash.put("events", events);
        hash.put("indPoints", indPoints);
        hash.put("relPoints", relPoints);
        hash.put("beenScored", beenScored);
        hash.put("coachCode", coachCode);
        hash.put("managerCode", managerCode);
        hash.put("userId", userId);

        mDatabase.setValue(hash);

    }



}
