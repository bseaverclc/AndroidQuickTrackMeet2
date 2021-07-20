package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.TextViewCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ScoresActivity extends AppCompatActivity {
 private Meet meet;
 private TextView meetName;
 private LinearLayout linearLayout;
 private HashMap<String, HashMap<String, Double>> teamPoints = new HashMap<>();
 private String relayString = "";
 String output = "";
    TextView textViewOutlet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        linearLayout = (LinearLayout)this.findViewById(R.id.linearLayoutScores);
        Intent intent = getIntent();
        meet = (Meet)intent.getSerializableExtra("meet");

        meetName = (TextView)this.findViewById(R.id.meetName);
        meetName.setText(meet.getName());
        setTitle("Team Scores");

        Collection<String> values = meet.getSchools().values();
        ArrayList<String> initials = new ArrayList<String>(values);
        Collections.sort(initials);


        for(String lev: meet.getLevels()){
            HashMap<String, Double> inner = new HashMap<String, Double>();
            for(int i = 0; i < initials.size(); i++) {

                inner.put(initials.get(i), 0.0);

            }
            teamPoints.put(lev, inner );
        }
        System.out.println(teamPoints);

        textViewOutlet = new TextView(this);
        textViewOutlet.setMovementMethod(new ScrollingMovementMethod());

        computeScores();
        displayScores();
    }

    public void copyPasteAction(View view){
        ClipboardManager clipboardManager;
        ClipData clipData;

        String txtcopy = output;
        clipData = ClipData.newPlainText("text",txtcopy);
        clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(clipData);

        String url = "https://www.athletic.net/TrackAndField/Illinois/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    public void displayScores(){
        for (String l: meet.getLevels()){
            TextView levelTitle = new TextView(this);
            levelTitle.setText(l + " Scores");
            TextViewCompat.setTextAppearance(levelTitle, android.R.style.TextAppearance_Medium);
            levelTitle.setPadding(0,20,0,10);
            levelTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(levelTitle);

            LinearLayout scoresLayout = new LinearLayout(this);
            scoresLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT); // or set height to any fixed value you want
            //scoresLayout.setWeightSum(3);

            scoresLayout.setLayoutParams(lp);
            linearLayout.addView(scoresLayout);
            HashMap<String, Double> levelScores = teamPoints.get(l);
            for (Map.Entry<String, Double> ts : levelScores.entrySet()) {
                TextView score = new TextView(this);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                //p.weight = 1;
                score.setLayoutParams(p);
                score.setText(ts.getKey() + ": " + ts.getValue() );
                score.setGravity(Gravity.CENTER_HORIZONTAL);
                scoresLayout.addView(score);

                String fullSchool = "";

                for (Map.Entry<String,String> sch: meet.getSchools().entrySet()) {
                    if (sch.getValue().equalsIgnoreCase(ts.getKey())) {
                        fullSchool = sch.getKey();
                        break;
                    }
                }

                output += "S,"+ meet.getGender() + "," + l + ",," + fullSchool + "," + ts.getValue() + "\n";


            }
            }
        textViewOutlet.setText(output);
          linearLayout.addView(textViewOutlet);

        }

        public void computeScores(){
        for(Athlete a : AppData.allAthletes){
            for(Event e : a.showEvents()){
                if (e.getMeetName().equalsIgnoreCase(meet.getName())){
                    if(!e.getMarkString().equalsIgnoreCase("")){
                        String units = "";
                        if(e.getName().contains("Jump") || e.getName().contains("Vault") || e.getName().contains("Shot") || e.getName().contains("Discus")){
                            units = "m";
                        }
                        HashMap<String, Double> temp = teamPoints.get(e.getLevel());
                        if (temp != null) {
                            double currentPoints = temp.get(a.getSchool());
                            try {
                                currentPoints = e.getPoints() + currentPoints;
                            } catch (Exception exc) {

                            }
                            temp.put(a.getSchool(), currentPoints);
                            teamPoints.put(e.getLevel(), temp);
                        }

                        // If event was a relay and names of relay members to relayString for athletic.net
                        if(e.getName().contains("4x")) {
                            relayString = "";
                            if (e.getRelayMembers() != null) {
                                for (String id : e.getRelayMembers()) {
                                    for (Athlete ath : AppData.allAthletes) {
                                        if (ath.getUid().equalsIgnoreCase(id)) {
                                            relayString = relayString + ", " + ath.getLast() + ", " + ath.getFirst() + ", " + ath.getGrade();
                                            break;
                                        }
                                    }
                                }
                            }




                            if (e.getPlace() != null) {
                                output += "R," + meet.getGender() + "," + e.getLevel() + "," + e.getName().substring(0, e.getName().length() - 4) + "," + e.getPlace() + ",,,," + a.getSchoolFull() + "," + e.getMarkString() + units + "," + e.getPoints() + ",Finals,," + relayString + "\n";
                            } else {
                                output += "R," + meet.getGender() + "," + e.getLevel() + "," + e.getName().substring(0, e.getName().length() - 4) + ",,,,," + a.getSchoolFull() + "," + e.getMarkString() + units + "," + e.getPoints() + ",Finals,," + relayString + "\n";
                            }
                        }

                        // If event was an individual event
                        else{
                            if(e.getPlace() != null){
                                output += "E," + meet.getGender() + "," + e.getLevel() + "," + e.getName().substring(0, e.getName().length() - 4) + "," + e.getPlace() + "," + a.getLast() + "," + a.getFirst() + "," + a.getGrade() + "," + a.getSchoolFull() + "," + e.getMarkString() + units + "," + e.getPoints() + ",Finals,," + "\n";
                            }
                            else{
                                output += "E," + meet.getGender() + "," + e.getLevel() + "," + e.getName().substring(0, e.getName().length() - 4) + ","  + "," + a.getLast() + "," + a.getFirst() + "," + a.getGrade() + "," + a.getSchoolFull() + "," + e.getMarkString() + units + "," + e.getPoints() + ",Finals,," + "\n";
                            }
                        }



                    }
                }
            }
        }

        textViewOutlet.setText(output);
        }

//    func computeScores(){
//
//        for a in AppData.allAthletes{
//            //var updated = false
//
//            for e in a.events{
//                if e.meetName == meet.name{
//                    if e.markString != "" {
//
//                        //  If event was individual
//                    else{
//                            if let pl = e.place{
//                                textViewOutlet.text += "E,\(meet.gender),\(e.level),\(e.name.dropLast(4)),\(pl),\(a.last),\(a.first) ,\(a.grade),\(a.schoolFull),\(e.markString)\(units),\(e.points),Finals, , \n"
//                            }
//                      else{
//                                textViewOutlet.text += "E,\(meet.gender),\(e.level),\(e.name.dropLast(4)), ,\(a.last),\(a.first) ,\(a.grade),\(a.schoolFull),\(e.markString)\(units),\(e.points),Finals, , \n"
//
//                            }
//                        }
//
//
//                    }
//                }
//            }
//        }
//
//
//        // Need to figure out how to clear out labels everytime????
//        // I think I should add the labels in view did load and update their text values here
//        // I will need to build an array of uiLabels
//        var i = 0
//        let sortedTeamPoints = teamPoints.sorted{ $0.key < $1.key }
//        for (level,scores) in sortedTeamPoints {
//            if i < schoolsStackView.count{
//                // add level text header
//                levelsOutlet[i].text = "\(level) Scores"
//
//
//                let sortedScores = scores.sorted{ $0.key < $1.key }
//                for (initials,score) in sortedScores{
//                    // print info to textview
//                    var fullSchool = ""
//                    for (sch,ini) in meet.schools{
//                        if initials == ini{
//                            fullSchool = sch
//                        }
//                    }
//                    textViewOutlet.text += "S,\(meet.gender),\(level),,\(fullSchool),\(score)\n"
//
//                    // set up school labels
//                    let label = UILabel()
//                    label.text = "\(initials)"
//                    label.textAlignment = .center
//                    schoolsStackView[i].addArrangedSubview(label)
//
//                    // set up score labels
//                    let label2 = UILabel()
//                    label2.text = "\(score)"
//                    label2.textAlignment = .center
//                    scoresStackView[i].addArrangedSubview(label2)
//                }
//
//            }
//            else{print("i value too big when adding score labels")}
//            i+=1
//        }
//
//
//    }

}