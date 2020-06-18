package com.eventure.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.R;
import com.eventure.model.MyEvent;
import com.eventure.services.ServiceFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.eventure.services.EventService.SortType.TimeClosestFirst;
import static com.eventure.services.EventService.SortType.TimeFurthestFirst;

public class EventListActivityDemo extends AppCompatActivity {

    private static final String TAG = "Event List Activity" ;
    ArrayList<MyEvent> events;
    ArrayList<String> titles;
    String yearStr;
    String monthStr;
    String dayStr;
    ListAdapter adapter;
    ListView list;
    TextView title;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        logic();
    }

    private void logic() {


        myRef.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    titles.add(ds.getValue(MyEvent.class).getTitle());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        title.setText(titles.get(0));


    }
}
