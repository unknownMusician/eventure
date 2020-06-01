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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.R;
import com.eventure.controller.ControllerFactory;
import com.eventure.model.MyEvent;
import com.eventure.services.ServiceFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {
    private static final String TAG = "Event List Activity" ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ListView list = findViewById(R.id.listOfEventsList);

        String yearStr = getIntent().getStringExtra("year");
        String monthStr = getIntent().getStringExtra("month");
        String dayStr = getIntent().getStringExtra("day");

        ArrayList<MyEvent> allEvents = ServiceFactory.get().getEventService().getEventList();
        ArrayList<String> titles = null;
        if(yearStr != null && monthStr != null && dayStr  != null){

            Integer year = Integer.parseInt(yearStr);
            Integer month = Integer.parseInt(monthStr);
            Integer day = Integer.parseInt(dayStr);
            titles = ControllerFactory.get().getEventListController().getEventTitleList(ServiceFactory.get()
            .getEventService().getFilteredByDate(year,month,day));
            Log.d(TAG, "onCreate: " + ServiceFactory.get()
                    .getEventService().getFilteredByDate(year,month,day));
            Log.d(TAG, "onCreate: " + year + " " + month + " " + day);
        }
        else {
            titles = ControllerFactory.get().getEventListController().getEventTitleList(allEvents);
        }


        ListAdapter adapter = new ArrayAdapter(this,R.layout.event_list_items_layout,
                titles);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(EventListActivity.this,EventActivity.class);
                intent.putExtra("idOfEvent",Integer.toString(position));
                startActivity(intent);

            }
        });


    }
}
