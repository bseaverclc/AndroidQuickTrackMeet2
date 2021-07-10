package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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
 private LinearLayoutCompat linearLayout;
 private HashMap<String, HashMap<String, Double>> teamPoints = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        linearLayout = (LinearLayoutCompat) this.findViewById(R.id.linearLayoutScores);
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



        displayScores();
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
                }
            }


        }

}