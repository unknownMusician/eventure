package com.eventure.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.R;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ListView list = findViewById(R.id.listOfEventsList);

        ArrayList<String> choices = new ArrayList<>();
        
        ListAdapter adapter = new ArrayAdapter(this,R.layout.event_list_items_layout,choices);
        list.setAdapter(adapter);

    }
}
