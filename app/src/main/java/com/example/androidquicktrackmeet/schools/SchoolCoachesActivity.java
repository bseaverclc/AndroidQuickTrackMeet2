package com.example.androidquicktrackmeet.schools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.androidquicktrackmeet.AppData;
import com.example.androidquicktrackmeet.R;
import com.example.androidquicktrackmeet.School;

public class SchoolCoachesActivity extends AppCompatActivity {

    private String theSchool;
    private RecyclerView recyclerView;
    School sch = new School();
    SchoolCoachesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_coaches);


        recyclerView = findViewById(R.id.recyclerViewCoaches);
        Intent intent = getIntent();
        theSchool = (String)intent.getSerializableExtra("theSchool");
        setTitle(theSchool);
        if(AppData.schools.size()>0) {

            for (School s : AppData.schools) {
                if (s.getFull().equalsIgnoreCase(theSchool)) {
                    sch = s;
                    break;
                }
            }

            adapter = new SchoolCoachesAdapter(sch);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
            Drawable mDivider = ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.divider);
            dividerItemDecoration.setDrawable(mDivider);

            recyclerView.addItemDecoration(dividerItemDecoration);

            recyclerView.setAdapter(adapter);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add)
        {
        if (sch.getCoaches().contains(AppData.coach) || AppData.fullAccess)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Add Coach");
                alert.setMessage("Add the email of the coach you want to add");

// Set an EditText view to get user input
                final EditText input = new EditText(this);
                alert.setView(input);

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        sch.addCoach(value);
                        sch.updateFirebase();
                        adapter.notifyDataSetChanged();
                        System.out.println("Added email of " + value);
                        return;
                    }
                });

                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                return;
                            }
                        });
                alert.show();
                return true;
            }
            else {
                AlertDialog.Builder addFailure = new AlertDialog.Builder(this);
                addFailure.setTitle("Error!");
                addFailure.setMessage("You need to be a coach of this school to add a coach");
                addFailure.setPositiveButton("OK", null );
                addFailure.show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

}