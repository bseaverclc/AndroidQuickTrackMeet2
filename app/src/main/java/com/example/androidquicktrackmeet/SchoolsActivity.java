package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class SchoolsActivity extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools2);

        System.out.println("Number of athletes" + AppData.allAthletes.size());
        // Setting up the ListView
        SchoolsListAdapter adapter=new SchoolsListAdapter(this, AppData.schools);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }
}