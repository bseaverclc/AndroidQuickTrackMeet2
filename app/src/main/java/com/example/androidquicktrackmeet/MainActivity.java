package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void meetsAction(View view){
        System.out.println("meets pushed");
        startActivity(new Intent(MainActivity.this, MeetsActivity.class));
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//
//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }

    public void schoolsAction(View view){
        System.out.println("meets pushed");
        startActivity(new Intent(MainActivity.this, SchoolsActivity.class));
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//
//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }
}