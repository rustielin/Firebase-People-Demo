package com.example.rustielin.firebasepeopledemo;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Rustie Lin on 1/6/2017.
 *
 * Fragment for the RecyclerView
 */

public class RecycleFragment extends Fragment {

    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private EditText name_prompt;
    private EditText email_prompt;
    private AutoCompleteTextView majors_prompt;

    private ArrayList<Person> personList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personList = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycle_layout, container, false);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // get the RecyclerView from the root view
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        this.getPeople();

        // prompts user to add a Person with an AlertDialog
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptUserInput();
            }
        });

        return v;
    }

    /**
     *  Prompts user input for new Person (name, email, majors) using AlertDialog
     *  Adds new Person to Firebase database using addPerson method
     */
    private void promptUserInput() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(promptView);
        name_prompt = (EditText) promptView.findViewById(R.id.name);
        email_prompt = (EditText) promptView.findViewById(R.id.email);
        majors_prompt = (AutoCompleteTextView) promptView.findViewById(R.id.majors);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                addPerson(name_prompt.getText().toString(),
                                        email_prompt.getText().toString(),
                                        majors_prompt.getText().toString());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     *  Constructs a new Person and adds to Firebase database
     */
    private void addPerson(String name, String email, String majors) {
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        person.setMajors(majors);

        mDatabase.child("People").push().setValue(person);
    }

    /**
     *  Listen for data change and get resulting list of People
     */
    private void getPeople() {
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                update(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                update(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                update(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                update(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     *  Resets and updates personList to whatever's on Firebase
     *  Displays personList via a RecyclerView
     */
    private void update(DataSnapshot dataSnapshot) {
        personList.clear();

        for (DataSnapshot ds: dataSnapshot.getChildren()) {
            Person person = new Person();
            person.setName(ds.getValue(Person.class).getName());
            person.setEmail(ds.getValue(Person.class).getEmail());
            person.setMajors(ds.getValue(Person.class).getMajors());
            personList.add(person);
        }

        // there's something to display
        if (personList.size() > 0) {
            mAdapter = new RecyclerAdapter(personList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // just for debugging
            Log.d("personlist", "" + personList);
        }
    }
}
