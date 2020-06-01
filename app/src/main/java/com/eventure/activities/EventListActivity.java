package com.eventure.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ListView list = findViewById(R.id.listOfEventsList);
        ArrayList<String> titles = ControllerFactory.get().getEventListController().getEventTitleList();

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
