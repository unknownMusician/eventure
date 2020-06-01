package com.eventure.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.R;
import com.eventure.controller.ControllerFactory;
import com.eventure.model.MyEvent;
import com.eventure.services.ServiceFactory;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "Event List Activity" ;
    ArrayList<MyEvent> events;
    ArrayList<String> titles;
    String yearStr;
    String monthStr;
    String dayStr;
    ListAdapter adapter;
    ListView list;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        list = findViewById(R.id.listOfEventsList);

        yearStr = getIntent().getStringExtra("year");
        monthStr = getIntent().getStringExtra("month");
        dayStr = getIntent().getStringExtra("day");
        Spinner spinner = findViewById(R.id.eventFilterSpinner);
        String[] strings = {"All","Today","On week","On month"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,strings);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventListActivity.this,EventActivity.class);
                intent.putExtra("idOfEvent",Integer.toString(position));
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0) {
            if(yearStr != null && monthStr != null && dayStr  != null){
                Integer year = Integer.parseInt(yearStr);
                Integer month = Integer.parseInt(monthStr);
                Integer day = Integer.parseInt(dayStr);
                events = ServiceFactory.get().getEventService().getFilteredByDate(year,month,day);
            }
            else{
                events = ServiceFactory.get().getEventService().getEventList();
            }
        }
        if(position == 1) {
            events = ServiceFactory.get().getEventService().getFilteredByToday();
        }
        if(position == 2) {
            events = ServiceFactory.get().getEventService().getFilteredByThisWeek();
        }
        if(position == 3) {
            events = ServiceFactory.get().getEventService().getFilteredByThisMonth();
        }
        titles = ServiceFactory.get().getEventService().getEventTitleList(events);
        list = findViewById(R.id.listOfEventsList);
        adapter = new ArrayAdapter(this,R.layout.event_list_items_layout, titles);
        list.setAdapter(adapter);
        yearStr = null;
        monthStr = null;
        dayStr = null;
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
