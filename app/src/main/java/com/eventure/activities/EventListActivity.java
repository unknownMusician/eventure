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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.R;
import com.eventure.controller.ControllerFactory;
import com.eventure.model.MyEvent;
import com.eventure.services.EventService;
import com.eventure.services.ServiceFactory;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.eventure.services.EventService.SortType.TimeClosestFirst;

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
        list = findViewById(R.id.listOfEventsList);

        title = findViewById(R.id.listEventTitleTextView);
        yearStr = getIntent().getStringExtra("year");
        monthStr = getIntent().getStringExtra("month");
        dayStr = getIntent().getStringExtra("day");

        Spinner spinnerFilter = findViewById(R.id.eventFilterSpinner);
        Spinner spinnerSorter = findViewById(R.id.eventSorterSpinner);
        Log.d(TAG, "onCreate: " + spinnerFilter.getId());

        String[] filters = {"All","Today","On week","On month"};
        String[] sorters = {"Newest"};
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
                  //  events = ServiceFactory.get().getEventService().getSortedBy(TimeClosestFirst,events);
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
                Intent intent = new Intent(EventListActivity.this,EventActivity.class);
                intent.putExtra(MyEvent.class.getName(),events.get(position));
                startActivity(intent);
            }
        });

    }

}
