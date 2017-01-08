package com.example.rustielin.firebasepeopledemo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Rustie Lin on 1/6/2017.
 *
 * Prepares list of Persons to be displayed within a RecyclerView
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Person> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView emailTextView;
        public TextView majorsTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.person_name);
            emailTextView = (TextView) itemView.findViewById(R.id.person_email);
            majorsTextView = (TextView) itemView.findViewById(R.id.person_majors);
        }

    }

    public RecyclerAdapter(List<Person> dataset) {
        mDataset = dataset;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_entry, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = mDataset.get(position);

        TextView name = holder.nameTextView;
        name.setText("Name: " + person.getName());
        TextView email = holder.emailTextView;
        email.setText("Email: " + person.getEmail());
        TextView majors = holder.majorsTextView;
        majors.setText("Majors: " + person.getMajors());
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
