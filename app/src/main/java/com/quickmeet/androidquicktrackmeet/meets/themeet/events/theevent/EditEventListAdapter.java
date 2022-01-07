package com.quickmeet.androidquicktrackmeet.meets.themeet.events.theevent;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ImageView;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quickmeet.androidquicktrackmeet.AppData;
import com.quickmeet.androidquicktrackmeet.Athlete;
import com.quickmeet.androidquicktrackmeet.R;
import com.quickmeet.androidquicktrackmeet.Meet;
import com.quickmeet.androidquicktrackmeet.Event;
import com.quickmeet.androidquicktrackmeet.meets.themeet.events.EventsActivity;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class EditEventListAdapter extends RecyclerView.Adapter<EditEventListAdapter.ViewHolderEditEvent> {

    //private final Activity context;
    // private final String[] names;
    //private final Integer[] grades;

    private EditEventListAdapter adapter = this;
    private ArrayList<Athlete> athletes;
    private String event;
   // private Meet meet;
    private LayoutInflater mInflater;
    private Button processButton;
    private TextView sectionName;
    private RecyclerView recyclerView;


    // private final Integer[] imgid;

    public EditEventListAdapter(ArrayList<Athlete> athletes, String event, Button processButton, TextView sectionName) {
        //super(context, R.layout.custom_editeventlist, athletes);
        // TODO Auto-generated constructor stub
        //this.mInflater = LayoutInflater.from(context);
        //this.context=context;
        // this.names=names;
        // this.grades=grades;
        //this.imgid=imgid;

        this.athletes = athletes;
        this.event = event;
       // this.meet = meet;
        this.processButton = processButton;
        this.sectionName = sectionName;


        //System.out.println(this.athletes);
        }

        public ArrayList<Athlete> getAthletes(){return athletes;}
        public void  setAthletes(ArrayList<Athlete> athletes){this.athletes = athletes;}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;

    }

    // inflates the row layout from xml when needed
    @Override
    public EditEventListAdapter.ViewHolderEditEvent onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.custom_editeventlist, parent, false);

        return new EditEventListAdapter.ViewHolderEditEvent(view);
    }

    @Override
    public void onBindViewHolder(EditEventListAdapter.ViewHolderEditEvent holder, int position) {
        System.out.println("Onbindbeing called");
        Athlete a;

        if(athletes.size() == 0){
            holder.subTitleText.setText("");
            holder.titleText.setText("");
            holder.place.setText("");
            holder.place.setHint("");
            holder.mark.setHint("");
            holder.mark.setText("");
            holder.points.setText("");
            holder.cell.setTag(-1);

        }
        else {
            a = athletes.get(position);
//            holder.markListener = new MyMarkTextListener();
//            holder.placeListener = new MyPlaceTextListener();
//            holder.mark.addTextChangedListener(holder.markListener);
//            holder.place.addTextChangedListener(holder.placeListener);


            //imageView.setImageResource(imgid[position]);
            holder.subTitleText.setText(a.getSchool() + "  " + a.getGrade());
            holder.titleText.setText(a.getLast() + ", " + a.getFirst());

            //System.out.println(athletes.get(position).showEvents().size());

            for (Event e : a.showEvents()) {
                //System.out.println("attemptng to set mark");
                if (e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName())) {
                    holder.mark.setText(e.getMarkString());
                    System.out.println("printing mark for " + a.getLast() + e.getMarkString());
                    if (e.getPlace() != null) {
                        holder.place.setText(("" + e.getPlace()));
                    } else {
                        holder.place.setText("");
                    }

                    if (e.getPoints() != null) {
                        holder.points.setText("" + e.getPoints());

                    } else {
                        holder.points.setText("");

                    }
                    holder.points.setBackgroundColor(Color.TRANSPARENT);
                    if (processButton.getText().toString().equalsIgnoreCase("Processed")) {
                        if (e.getPoints() > 0) {
                            holder.points.setBackgroundColor(Color.GREEN);
                        }
                    }
                    break;

                }
            }
            if (Meet.canManage && holder != null) {
                holder.markListener = new MyMarkTextListener();
                holder.placeListener = new MyPlaceTextListener();
                holder.mark.addTextChangedListener(holder.markListener);
                holder.place.addTextChangedListener(holder.placeListener);
                holder.markListener.updateAthlete(a);
                holder.markListener.updatePosition(position);
                holder.placeListener.updateAthlete(a);
                holder.placeListener.updatePosition(position);

                holder.mark.setFocusable(true);
                holder.place.setFocusable(true);



            }
            else if (holder != null){
                holder.mark.setFocusable(false);
                holder.place.setFocusable(false);
            }




        }
        holder.cell.setTag(position);

            holder.cell.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Activity focussed = (Activity)view.getContext();
                    if(focussed.getCurrentFocus() != null) {
                        focussed.getCurrentFocus().clearFocus();
                    }
                    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.INVISIBLE);
                    return true;
                }
            });
            holder.cell.setOnDragListener(new DragListener());

    }

    @Override
    public int getItemCount() {
        if(athletes.size() >0)
        return athletes.size();
        else return 1;
    }

    public TextView getSectionName(){return sectionName;}


    //Never used?
    public void moveItem(int from, int to){
        Athlete temp = athletes.get(to);
        athletes.set(to, athletes.get(from));
        athletes.set(from, temp);
       // adapter.notifyDataSetChanged();
    }

    public boolean deleteItem(int position){
        if(Meet.canCoach) {
            if (position < athletes.size()) {
                Athlete a = athletes.get(position);
                System.out.println("Trying to delete athleete " + a.getLast());
                if(a.getSchoolFull().equalsIgnoreCase(AppData.mySchool) || Meet.canManage) {
                    for (int i = 0; i < a.showEvents().size(); i++) {
                        if (a.showEvents().get(i).getName().equals(event) && a.showEvents().get(i).getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName())) {
                            System.out.println("Place of athlete " + a.showEvents().get(i).getPlace());
                            if (a.showEvents().get(i).getMarkString().equalsIgnoreCase("") && a.showEvents().get(i).getPlace() == null) {
                                // a.showEvents().remove(i);
                                a.deleteEventFirebase(a.showEvents().get(i).getUid());
                                athletes.remove(position);
                                recyclerView.setAdapter(this);

                                notifyItemRemoved(position);
                                //notifyDataSetChanged();
                                processButton.setBackgroundColor(Color.GRAY);
                                processButton.setText("Process Event");

                                AppData.selectedMeet.setBeenScored(EventsActivity.eventPosition, false);
                                AppData.selectedMeet.updatebeenScoredFirebase();

                                return true;

                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public void changeEvent(String newEvent){
        event = newEvent;
    }

    public class ViewHolderEditEvent extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleText, points, subTitleText;
        public EditText mark, place;
        public LinearLayout cell;
        public MyMarkTextListener markListener;
        public MyPlaceTextListener placeListener;


        public ViewHolderEditEvent(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);


            titleText = (TextView) itemView.findViewById(R.id.title);
            points = (TextView) itemView.findViewById(R.id.points);
             mark = (EditText)itemView.findViewById((R.id.editMark));
             place = (EditText)itemView.findViewById(R.id.editPlace);
            cell = itemView.findViewById(R.id.cell);
            subTitleText = itemView.findViewById(R.id.subtitle);

            // Moved to onBindViewHolder
//            if(!Meet.canManage){
//                mark.setFocusable(false);
//                place.setFocusable(false);
//            }
//            else{
//                mark.setFocusable(true);
//                place.setFocusable(true);
//            this.markListener = new MyMarkTextListener();
//            this.placeListener = new MyPlaceTextListener();
//            this.mark.addTextChangedListener(markListener);
//            this.place.addTextChangedListener(placeListener);
            if(Meet.canManage){
                mark.setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        //key listening stuff
                        processButton.setBackgroundColor(Color.GRAY);
                        processButton.setText("Process Event");

                        AppData.selectedMeet.setBeenScored(EventsActivity.eventPosition, false);
                        AppData.selectedMeet.updatebeenScoredFirebase();

                        return false;
                    }
                });


// Attempting to keep everything in view when click on a mark
//                mark.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if(MotionEvent.ACTION_UP == event.getAction()) {
//                            Activity a = (Activity)recyclerView.getContext();
//                            a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                            a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//                            System.out.println("Trying to scroll to position" + (adapter.getItemCount() -1));
//
//                           //recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
//                            //recyclerView.scrollToPosition(getLayoutPosition());
//                        }
//
//                        return false; // return is important...
//                    }
 //               });


                place.setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        //key listening stuff
                        processButton.setBackgroundColor(Color.GRAY);
                        processButton.setText("Process Event");

                        AppData.selectedMeet.setBeenScored(EventsActivity.eventPosition, false);
                        AppData.selectedMeet.updatebeenScoredFirebase();
                        return false;
                    }
                });

            }

        }


        @Override
        public void onClick(View v) {
            if(event.contains("4x")) {
                System.out.println("Clicked on a relay team");
                Activity activity = (Activity) v.getContext();
                Intent intent = new Intent(activity, RelayActivity.class);
                intent.putExtra("eventName", event);
                intent.putExtra("relayAthlete", athletes.get(getAdapterPosition()));

                activity.startActivity(intent);
            }


        }
    }

    private class MyMarkTextListener implements TextWatcher {
        private int position;
        private Athlete a;

        public void updatePosition(int position) {
            this.position = position;
        }

        public void updateAthlete(Athlete a) {
            this.a = a;

        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                if (adapter.getAthletes().get(position).getLast().equalsIgnoreCase(a.getLast())) {
                        a = adapter.getAthletes().get(position);
                    System.out.println("after mark text changed fired on athlete ? ");
                    if (a != null) {
                        System.out.println(a.getLast());
                        for (Event e : a.showEvents()) {
                            if (e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName())) {
                                e.setMarkString(s.toString());

                                a.updateFirebase();
                                break;
                            }
                        }
                    }

                   // adapter.notifyDataSetChanged();
                }
            } catch (Exception ex) {
                System.out.println("Exception in markListener" + ex);
            }
        }
    }

    // PlaceListener Class
    private class MyPlaceTextListener implements TextWatcher {
        private int position;
        private Athlete a;

        public void updatePosition(int position) {
            this.position = position;

        }
        public void updateAthlete(Athlete a){
            this.a = a;
        }

        public void afterTextChanged(Editable s) {

            try {
                if (adapter.getAthletes().get(position).getLast().equalsIgnoreCase(a.getLast())) {
                    a = adapter.getAthletes().get(position);
                    System.out.println("after place text changed fired on athlete " + a.getLast());
                    if (a != null) {
                        System.out.println(a.getLast());
                        for (Event e : a.showEvents()) {
                            if (e.getName().equalsIgnoreCase(event) && e.getMeetName().equalsIgnoreCase(AppData.selectedMeet.getName())) {
                                try {
                                    e.setPlace(Integer.parseInt(s.toString()));
                                    a.updateFirebase();
                                    break;
                                } catch (Exception excep) {
                                    e.setPlace(null);
                                }
                            }
                        }
                    }
                    //adapter.notifyDataSetChanged();
                }
            }catch(Exception exe){
                System.out.println("Exception in PlaceTextListener " + exe.toString());
            }

                //adapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {



            }
    }



    public class DragListener implements View.OnDragListener {

        boolean isDropped = false;
//        Listener mListener;
//
//        public DragListener(Listener listener) {
//            this.mListener = listener;
//        }


        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            LinearLayout dropZoneView = (LinearLayout) v;

            //RecyclerView outer = (RecyclerView)((View)event.getLocalState()).getParent().getParent();


            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    System.out.println("Action Drag Started");

                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(Color.GRAY);
                    System.out.println("Action Drag Entered");
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(Color.WHITE);

//                    try{
//                        System.out.println(v.getId() + v.getClass().toString());
//                        System.out.println(R.id.topButtonsRow + v.getClass().toString());
//                       if(v.getId() == R.id.topButtonsRow){
//                           View viewSource = (View) event.getLocalState();
//                           ((RecyclerView)viewSource.getParent()).scrollBy(0,30);
//                       }
//                    }
//                    catch(Exception e){
//                        System.out.println(e);
//                    }
//
                    System.out.println("ActionDragExited ");
//                    //outer.scrollBy(0,30);



                    break;

                    case DragEvent.ACTION_DRAG_LOCATION:
                    System.out.println("ActionDragLocation");
                        RecyclerView mainScrollView = (RecyclerView)v.getParent().getParent().getParent();
                        //System.out.println("Recycler view on top of ID " + mainScrollView.getId());
                        RecyclerView itemRecyclerView = mainScrollView.findViewById(R.id.item_recycler_view);
                        //System.out.println("itemRcyclerView ID " + itemRecyclerView.getId());


                        //int topOfDropZone = dropZoneView.getTop();
                        //int bottomOfDropZone = dropZoneView.getBottom();
                        int[] location = new int[2];
                        dropZoneView.getLocationOnScreen(location);
                        //System.out.println("location on screen y " + location[1]);

                        //int scrollY = mainScrollView.getScrollY();
                        int scrollViewTop = mainScrollView.getTop();
                        int scrollViewBottom = mainScrollView.getBottom();
                        //System.out.println("Mainscrollview top " + scrollViewTop);
                        //System.out.println("MainScrollview bottom "  + scrollViewBottom );

                        //System.out.println("location: Scroll Y: "+ scrollY + " Scroll Y+Height: "+(scrollY + scrollViewHeight));
                        //System.out.println(" top of Drop Zone: "+ topOfDropZone +" bottom of Drop Zone: "+bottomOfDropZone);

                        if(location[1] > (scrollViewBottom - 100)) {
                            //System.out.println("Trying to scroll down");
                            //System.out.println("Item ScrollY " + mainScrollView.getScrollY());
                            mainScrollView.smoothScrollBy(0, 60);
                        }

                        if (location[1] - dropZoneView.getHeight() < (scrollViewTop + 200)) {
                            System.out.println("trying to scroll up");
                            mainScrollView.smoothScrollBy(0, -60);
                        }


                        break;


                case DragEvent.ACTION_DROP:
                   System.out.println("Action Drop Happening");
                    isDropped = true;
                    View viewSource = (View) event.getLocalState();
                    Activity focussed = (Activity)viewSource.getContext();
                    if(focussed.getCurrentFocus() != null) {
                        focussed.getCurrentFocus().clearFocus();
                    }


                    int positionSource = -1;
                    int positionTarget = -1;
                    v.setBackgroundColor(Color.WHITE);
                    //View viewSource = (View) event.getLocalState();
                    //((EditText)viewSource.findViewById(R.id.editMark)).removeTextChangedListener();
                    // Stop them from dropping a blank row
                    if((int)viewSource.getTag() == -1) break;
                    //viewSource.findViewById(R.id.editMark);

                        RecyclerView target = (RecyclerView) v.getParent();
                        RecyclerView source = (RecyclerView) viewSource.getParent();


                        EditEventListAdapter adapterSource = (EditEventListAdapter) source.getAdapter();
                        positionSource = (int) viewSource.getTag();

                        // Remove Athlete and update view
                        Athlete athleteMove = adapterSource.getAthletes().get(positionSource);

                        ArrayList<Athlete> theAthletes = adapterSource.getAthletes();
                        theAthletes.remove(positionSource);
                        source.setAdapter(adapterSource);
                      // source.getAdapter().notifyDataSetChanged();

                        //adapterSource.notifyDataSetChanged();


                        //List<CustomList> customListSource = adapterSource.getCustomList();
//                        customListSource.remove(positionSource);
//                        adapterSource.updateCustomList(customListSource);
//                        adapterSource.notifyDataSetChanged();
                        // get target location
                         EditEventListAdapter adapterTarget = (EditEventListAdapter) target.getAdapter();
                         positionTarget = (int) v.getTag();
                         System.out.println("position Target "+ positionTarget);
                         // update heat of event
                    if(adapterTarget.getSectionName().getText().toString().equalsIgnoreCase("heat 1")){
                        athleteMove.findEvent(AppData.selectedMeet.getName(), EditEventListAdapter.this.event).setHeat(1);
                    }
                    else if(adapterTarget.getSectionName().getText().toString().equalsIgnoreCase("heat 2")){
                        athleteMove.findEvent(AppData.selectedMeet.getName(), EditEventListAdapter.this.event).setHeat(2);
                    }
                    else{
                        athleteMove.findEvent(AppData.selectedMeet.getName(), EditEventListAdapter.this.event).setHeat(0);
                    }


                        // add athlete to target array
                         if(positionTarget >=0) {

                             adapterTarget.getAthletes().add(positionTarget, athleteMove);

                         }
                         else {
                             adapterTarget.getAthletes().add(athleteMove);
                         }
                        athleteMove.updateFirebase();


                        recyclerView.setAdapter(adapter);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    source.getAdapter().notifyDataSetChanged();

                         //recyclerView.getAdapter().notifyDataSetChanged();
                        //adapterSource.notifyDataSetChanged();
                         //adapterTarget.notifyDataSetChanged();
                         v.setVisibility(View.VISIBLE);

                         // unprocess event
                    processButton.setBackgroundColor(Color.GRAY);
                    processButton.setText("Process Event");

                    AppData.selectedMeet.setBeenScored(EventsActivity.eventPosition, false);
                    AppData.selectedMeet.updatebeenScoredFirebase();
                         break;

//                        CustomListAdapter adapterTarget = (CustomListAdapter) target.getAdapter();
//                        List<CustomList> customListTarget = adapterTarget.getCustomList();
//                        if (positionTarget >= 0) {
//                            customListTarget.add(positionTarget, customList);
//                        } else {
//                            customListTarget.add(customList);
//                        }
//                        adapterTarget.updateCustomList(customListTarget);
//                        adapterTarget.notifyDataSetChanged();
//                        v.setVisibility(View.VISIBLE);
//
//                        if (source.getId() == R.id.recyclerPendingList
//                                && adapterSource.getItemCount() < 1) {
//                            mListener.setEmptyList(true);
//                        }
//
//                        if (v.getId() == R.id.textEmptyList) {
//                            mListener.setEmptyList(false);
//                        }
//                    }
//
 //                   break;

                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundColor(0);
                    System.out.println("Action Drag Ended");

                    break;

                default :
                    break;
            }

            if (!isDropped) {
                View vw = (View) event.getLocalState();
                vw.setVisibility(View.VISIBLE);
            }

            return true;
        }

    }



}

