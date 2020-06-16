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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.R;
import com.eventure.model.MyEvent;
import com.eventure.services.ServiceFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.eventure.services.EventService.SortType.TimeClosestFirst;
import static com.eventure.services.EventService.SortType.TimeFurthestFirst;

public class EventListActivity extends AppCompatActivity {
    private static final String TAG = "Event List Activity" ;
    ArrayList<MyEvent> events;
    ArrayList<String> titles;
    String yearStr;
    String monthStr;
    String dayStr;
    ListAdapter adapter;
    ListView list;
    TextView title;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        logic();
        FloatingActionButton addEventButton = findViewById(R.id.addEventBtn);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateEventActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        logic();
    }

    private void logic(){
        list = findViewById(R.id.listOfEventsList);
      //  statusesListView = findViewById(R.id.listOfEventsStatusesList);

        title = findViewById(R.id.listEventTitleTextView);
        yearStr = getIntent().getStringExtra("year");
        monthStr = getIntent().getStringExtra("month");
        dayStr = getIntent().getStringExtra("day");

        Spinner spinnerFilter = findViewById(R.id.eventFilterSpinner);
        Spinner spinnerSorter = findViewById(R.id.eventSorterSpinner);
        Spinner spinnerType = findViewById(R.id.typeSpinner);

        String[] filters = {"All","Today","On week","On month","Favorites","My Events","Default"};
        String[] sorters = {"Newest","Oldest","Active","UpComing","Finished"};
        String[] types = {"Any Type","Lecture","Discussion","Party","Other"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,filters);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(stringArrayAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    if(yearStr != null && monthStr != null && dayStr  != null){
                        Integer year = Integer.parseInt(yearStr);
                        Integer month = Integer.parseInt(monthStr);
                        Integer day = Integer.parseInt(dayStr);
                        events = ServiceFactory.get().getEventService().getFilteredByDate(year,month,day);
                        title.setText("Events on " + day +"/" + month + "/"+year );
                    }
                    else{
                        events = ServiceFactory.get().getEventService().getEventList();
                        title.setText("All events");
                        spinnerFilter.setSelection(0);
                        spinnerSorter.setSelection(0);
                        spinnerType.setSelection(0);
                    }
                }
                if(position == 1) {
                    events = ServiceFactory.get().getEventService().getFilteredByToday();
                    title.setText("Today events" );
                }
                if(position == 2) {
                    events = ServiceFactory.get().getEventService().getFilteredByThisWeek();
                    title.setText("On week events" );
                }
                if(position == 3) {
                    events = ServiceFactory.get().getEventService().getFilteredByThisMonth();
                    title.setText("On month events" );
                }
                if(position == 4){
                    events = ServiceFactory.get().getEventService().getFilteredByFavourite();
                    title.setText("Favorites");
                }
                if(position == 5){
                    events = ServiceFactory.get().getEventService().getFilteredByMyEvents();
                    title.setText("My Events");
                }
                if(position==6){
                    events = ServiceFactory.get().getEventService().getEventList();
                    events = ServiceFactory.get().getEventService().getSortedBy(TimeClosestFirst,events);
                    events = ServiceFactory.get().getEventService().getSortedByType("Any type",events);
                    spinnerFilter.setSelection(0);
                    spinnerSorter.setSelection(0);
                    spinnerType.setSelection(0);
                }

                titles = ServiceFactory.get().getEventService().getEventTitleList(events);
                list = findViewById(R.id.listOfEventsList);
                adapter = new ArrayAdapter(EventListActivity.this,R.layout.event_list_items_layout,titles);
                list.setAdapter(adapter);
                yearStr = null;
                monthStr = null;
                dayStr = null;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> stringArrayAdapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sorters);
        stringArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSorter.setAdapter(stringArrayAdapter1);

        spinnerSorter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    events = ServiceFactory.get().getEventService().getSortedBy(TimeClosestFirst,events);
                }
                if(position == 1){
                    events = ServiceFactory.get().getEventService().getSortedBy(TimeFurthestFirst,events);
                }
                if(position == 2){
                    events = ServiceFactory.get().getEventService().getSortedByStatus("Active",events);
                }
                if(position == 3){
                    events = ServiceFactory.get().getEventService().getSortedByStatus("UpComing",events);
                }
                if(position == 4){
                    events = ServiceFactory.get().getEventService().getSortedByStatus("Finished",events);
                }
                titles = ServiceFactory.get().getEventService().getEventTitleList(events);
                list = findViewById(R.id.listOfEventsList);
                adapter = new ArrayAdapter(EventListActivity.this,R.layout.event_list_items_layout,titles);
                list.setAdapter(adapter);

            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,types);
        stringArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(stringArrayAdapter2);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    events = ServiceFactory.get().getEventService().getSortedByType("Any Type",events);
                }
                if(position == 1){
                    events = ServiceFactory.get().getEventService().getSortedByType("Lecture",events);
                }
                if(position == 2){
                    events = ServiceFactory.get().getEventService().getSortedByType("Discussion",events);
                }
                if(position == 3){
                    events = ServiceFactory.get().getEventService().getSortedByType("Party",events);
                }
                if(position == 4){
                    events = ServiceFactory.get().getEventService().getSortedByType("Other",events);
                }
                titles = ServiceFactory.get().getEventService().getEventTitleList(events);
                list = findViewById(R.id.listOfEventsList);
                adapter = new ArrayAdapter(EventListActivity.this,R.layout.event_list_items_layout,titles);
                list.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(),EventActivity.class);
            intent.putExtra(MyEvent.class.getSimpleName(),events.get(position));
            startActivity(intent);
                Log.d(TAG, "onItemClick: " + events.get(position).hashCode());
        }
    });

    }
}
