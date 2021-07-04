package com.example.androidquicktrackmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MeetsActivity extends AppCompatActivity {
private ListView listView;
private Meet selectedMeet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meets);



        MeetsListAdapter adapter=new MeetsListAdapter(this, AppData.meets);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        attachListener();

    }

//    public void addMeetAction(View view){
//        startActivity(new Intent(MeetsActivity.this, AddMeetActivity.class));
//    }

    public void attachListener(){
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            selectedMeet = (Meet)parent.getItemAtPosition(position);
            System.out.println("Clicked on" + selectedMeet.getName());
            selectMeetAction(listView);
        }
    });
    }

    public void selectMeetAction(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("Selected", selectedMeet);

//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}