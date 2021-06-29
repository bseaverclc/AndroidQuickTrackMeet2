package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private Meet meet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        meet = (Meet)intent.getSerializableExtra("Selected");
        System.out.println(meet.getSchools());
    }

//    public void eventsAction(View view){
//        System.out.println("meets pushed");
//
//        Intent intent = new Intent(this, .class);
//
//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }
}