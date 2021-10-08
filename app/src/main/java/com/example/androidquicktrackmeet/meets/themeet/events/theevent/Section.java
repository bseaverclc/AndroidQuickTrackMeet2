package com.example.androidquicktrackmeet.meets.themeet.events.theevent;

import android.widget.Button;

import com.example.androidquicktrackmeet.Athlete;

import java.util.ArrayList;

    public class Section {
        private String heatNumber;
        private ArrayList<Athlete> athletes;
        private String event;
        private Button processButton;
        public Section(String heatNumber, ArrayList<Athlete> athletes, String event, Button processButton) {
            this.heatNumber = heatNumber;
            this.athletes = athletes;
            this.event = event;
            this.processButton = processButton;
        }
        public String getHeatNumber() {
            return heatNumber;
        }
        public void setHeatNumber(String heatNumber) {
            this.heatNumber = heatNumber;
        }
        public ArrayList<Athlete> getAthletes() {
            return athletes;
        }
        public void setAthletes(ArrayList<Athlete> athletes) {
            this.athletes = athletes;
        }
        public String getEvent(){return event;}
        public Button getProcessButton(){return processButton;}

        public void setEvent(String event){
            this.event = event;
        }
    }

