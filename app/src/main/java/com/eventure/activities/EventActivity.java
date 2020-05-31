package com.eventure.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.eventure.R;
import com.eventure.model.Event;
import com.eventure.services.ServiceFactory;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EventActivity" ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Integer idOfEvent = Integer.parseInt(getIntent().getStringExtra("idOfEvent"));

        Event event = ServiceFactory.getInstance().getEventService().getEventList().get(idOfEvent);

        TextView title = findViewById(R.id.eventTitleTextView);
        TextView description = findViewById(R.id.eventDescriptionTextView);
        TextView time = findViewById(R.id.eventTimeTextView);

        title.setText(event.getTitle());
        description.setText(event.getDescription());
        time.setText(event.getTime().toString());




    }
}
