package com.quickmeet.androidquicktrackmeet.meets.themeet.meetathletes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.Athlete;
import com.quickmeet.androidquicktrackmeet.Event;
import com.quickmeet.androidquicktrackmeet.Meet;
import com.quickmeet.androidquicktrackmeet.R;

import java.util.ArrayList;

public class AthleteEventsFromMeets extends AppCompatActivity {

    Athlete selectedAthlete;
    ArrayList<Event> displayedEvents = new ArrayList<Event>();
    //RecyclerView recyclerView;
    private RecyclerView recyclerView;
    AthleteEventsFromMeetsAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Onresume from AthleteEventsFromMeets");
         displayedEvents.clear();
         for(Athlete a : AppData.allAthletes){
             if(a.equals(selectedAthlete)){
                 selectedAthlete = a;
                 break;
             }
         }
        for(Event e : selectedAthlete.showEvents()) {
            if (e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName())) {
                displayedEvents.add(e);
                System.out.println(e.getName());
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_events_from_meets);

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        selectedAthlete = (Athlete) intent.getSerializableExtra("selectedAthlete");
        //System.out.println(selectedAthlete.getLast() + "was accepted");


        for (Event e : selectedAthlete.showEvents()) {
            if (e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName())) {
                displayedEvents.add(e);
            }
        }


        adapter = new AthleteEventsFromMeetsAdapter(displayedEvents, selectedAthlete);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable mDivider = ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.divider);
        dividerItemDecoration.setDrawable(mDivider);

        recyclerView.addItemDecoration(dividerItemDecoration);


        recyclerView.setAdapter(adapter);
        // listView=(ListView)findViewById(R.id.athleteEventsFromMeets);
        // listView.setAdapter(adapter);
        setTitle(selectedAthlete.getFirst() + " " + selectedAthlete.getLast());
        //attachListener();
        //eventPosition = 0;

        if (Meet.canManage || AppData.mySchool.equalsIgnoreCase(selectedAthlete.getSchoolFull())) {

            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                @Override
                public boolean isItemViewSwipeEnabled() {
                    return true;
                }

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    System.out.println("Trying to delete event from athlete");
                    int spot =  viewHolder.getAdapterPosition();
                    adapter.deleteItem(spot);
                    //adapter.notifyDataSetChanged();
                    //events.remove(position);
                    //adapter.notifyDataSetChanged();

                }
            };

            ItemTouchHelper touchHelper = new ItemTouchHelper(itemTouchHelperCallback);
            touchHelper.attachToRecyclerView(recyclerView);
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
        if (id == R.id.action_add && (Meet.canManage || AppData.mySchool.equalsIgnoreCase(selectedAthlete.getSchoolFull()))) {
            Intent intent = new Intent(this, AddEventsToAthlete.class);

            intent.putExtra("selectedAthlete", selectedAthlete);
            intent.putExtra("events", displayedEvents);


//        message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}