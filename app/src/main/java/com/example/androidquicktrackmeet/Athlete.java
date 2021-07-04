package com.example.androidquicktrackmeet;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Athlete {

    private String first, last, school, schoolFull, uid;
    private int grade;
    private ArrayList<Event> events = new ArrayList<Event>();

    public Athlete(){
        // default constructor must exist to be written to firebase
    }
    public Athlete(String first, String last, String school, int grade, String schoolFull){
        this.first = first;
        this.last = last;
        this.school = school;
        this.grade = grade;
        this.schoolFull = schoolFull;
        this.uid = "";
    }
    // Map<String,String> toppings = new HashMap<>();
    /*
   public Athlete(String key, Map<String, Object> dict){
       first = (String)dict.get("first");
       last = (String)dict.get("last");
       school = (String)dict.get("school");
       grade = (Integer)dict.get("grade");
       schoolFull = (String)dict.get("schoolFull");
       uid = key;
   }
   */


    public Athlete(String id, String first, String last, String school, int grade, String schoolFull){
        this.first = first;
        this.last = last;
        this.uid = id;
        this.school = school;
        this.schoolFull = schoolFull;
        this.grade = grade;
        this.uid = "";
    }

    public boolean equals(Athlete other){
        if (this.first.equals(other.first) && this.last.equals(other.last) && this.schoolFull.equals(other.schoolFull) && this.grade == other.grade){
            return true;
        }
        else{return false;}
    }

    public void addEvent(String name, String level, String meetName){
        Event e = new Event(name, level, meetName);
        events.add(e);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("events").push();
        uid = mDatabase.getKey();
        e.setUid(uid);
        mDatabase.setValue(e);
    }

    public void addEvent(Event e){
        events.add(e);


    }

    public void saveToFirebase(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").push();
        uid = mDatabase.getKey();
        mDatabase.setValue(this);
    }


//    func addEvent(name: String, level: String, meetName: String){
//        let e = Event(name: name, level: level, meetName: meetName)
//        events.append(e)
//        let ref = Database.database().reference().child(uid!)
//        e.uid = ref.childByAutoId().key
//        print("added event with key \(e.uid!)")
//        updateFirebase()
//    }


    public String getFirst() {
        return first;
    }
    public String getLast() {
        return last;
    }
    public String getSchool() {
        return school;
    }
    public String getSchoolFull() {
        return schoolFull;
    }
    public int getGrade() {return grade; }
    public String getUid(){return uid;}
    //public ArrayList<Event> getEvents(){return events;}

    public ArrayList<Event> showEvents(){
        return events;
    }


    public void setFirst(String first){this.first = first;
        System.out.println("Calling setFirst");}
    public void setLast(String last){this.last = last;}
    public void setSchool(String last){this.school = last;}
    public void setSchoolFull(String schoolFull){this.schoolFull = schoolFull;}
    public void setGrade(int grade){this.grade = grade;}
    public void setUid(String uid){this.uid = uid;}

    public void updateFirebase(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(this.uid);
        for(Event e : this.events){
            Map<String, Object> dict = new HashMap<String, Object>();
            dict.put("meetName", e.getMeetName());
            dict.put("name", e.getName());
            dict.put("level", e.getLevel());
            dict.put("markString", e.getMarkString());

            ref.child("events").child(e.getUid()).updateChildren(dict);
            System.out.println("updated an event in firebase");
        }


    }
//    func updateFirebase(){
//        var ref = Database.database().reference().child("athletes").child(uid!)
//        let dict = ["first": self.first, "last":self.last, "school": self.school, "schoolFull":self.schoolFull, "grade":self.grade] as [String : Any]
//
//        ref.updateChildValues(dict)
//        ref = ref.child("events")
//
//        for e in events{
//            let eventDict = ["meetName": e.meetName,"name": e.name, "level":e.level, "mark": e.mark, "markString": e.markString, "place":e.place ?? nil, "points": e.points, "heat": e.heat] as [String : Any]
//            ref.child(e.uid!).updateChildValues(eventDict)
//            if let rm = e.relayMembers{
//                print("trying to update relayMembers in firebase")
//                ref.child(e.uid!).updateChildValues(["relayMembers": rm])
//                print("relay members updated in firebase")
//
//            }
//
//        }
//
//
//        print("updating athlete in firebase")
//    }


    public String toString(){
        return first;
    }
}
