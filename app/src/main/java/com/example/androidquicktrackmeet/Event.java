package com.example.androidquicktrackmeet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Event implements Serializable {
    private String name, level, markString, meetName, uid;
    private Double mark, points = 0.0;
    private Integer heat, place;
    private ArrayList<String> relayMembers;

    public Event(){

    }

    public Event(String name, String level, String meetName){
        this.name = name;
        this.level = level;
        this.meetName = meetName;
        this.mark = 0.0;
        this.markString = "";

        if( name.contains("4x")){
            relayMembers = new ArrayList<String>();
        }

    }

    // Build Event from firebase
//    public Event(String key, HashMap<String, Object> dict){
//        uid = key;
//        name = (String)dict.get("name");
//        level = (String)dict.get("level");
//        mark = (Double)dict.get("mark");
//        markString = (String)dict.get("markString");
//        if(dict.get("place") != null){
//            place = (Integer)dict.get("place");
//        }
//        else{
//            place = null;
//        }
//        points = (Double)dict.get("points");
//        if(dict.get("heat") != null){
//            heat = (Integer)dict.get("heat");
//        }
//        else{
//            heat = null;
//        }
////        if(dict.get("relayMembers") != null){
////            relayMembers = (ArrayList<String>)dict.get("relayMembers");
////        }
////        else{
////            relayMembers = null;
////        }
//        meetName = (String)dict.get("meetName");
//
//
//    }


    public String getName(){return name;}
    public String getLevel(){return level;}
    public String getMeetName(){return meetName;}
    public String getMarkString(){return markString;}
    public String getUid(){return uid;}
    public Integer getPlace(){return place;}
    public ArrayList<String> getRelayMembers(){return relayMembers;}
    public Double getPoints(){return points;}
    public Double getMark(){return mark;}
    public Integer getHeat(){return heat;}




    public void setUid(String uid){
        this.uid = uid;
    }

    public void setMarkString(String markString){
        this.markString = markString;
    }
    public void setPlace(Integer place){this.place = place;}
    public void setPoints(Double points){this.points = points;}
    public void setHeat(int heat){this.heat = heat;}
    public void setName(String name){this.name = name;}




}

