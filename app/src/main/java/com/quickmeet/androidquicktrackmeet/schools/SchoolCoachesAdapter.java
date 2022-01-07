package com.quickmeet.androidquicktrackmeet.schools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quickmeet.androidquicktrackmeet.R;
import com.quickmeet.androidquicktrackmeet.School;

import androidx.recyclerview.widget.RecyclerView;

public class SchoolCoachesAdapter extends RecyclerView.Adapter<SchoolCoachesAdapter.ViewHolderEditEvent> {
    School sch;

    public SchoolCoachesAdapter(School sch)
    {
        this.sch = sch;
    }





    @Override
    public SchoolCoachesAdapter.ViewHolderEditEvent onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.adapter_school_coaches, parent, false);

        return new SchoolCoachesAdapter.ViewHolderEditEvent(view);


    }

    @Override
    public void onBindViewHolder(SchoolCoachesAdapter.ViewHolderEditEvent holder, int position) {
        System.out.println("Calling onBind for SchoolCoachesAdapter");
        holder.emailView.setText(sch.getCoaches().get(position));
    }

    @Override
    public int getItemCount() {
        System.out.println("Coaches Size is " + sch.getCoaches().size());
        return sch.getCoaches().size();
    }


    public class ViewHolderEditEvent extends RecyclerView.ViewHolder {
        TextView emailView;

        public ViewHolderEditEvent(View itemView)
        {
            super(itemView);
            emailView = itemView.findViewById(R.id.emailText);
        }




    }

}
