package com.eventure.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.eventure.R;
import com.eventure.controller.ControllerFactory;
import com.eventure.model.MyEvent;
import com.eventure.services.ServiceFactory;

import java.util.Objects;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EventActivity" ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        int idOfEvent = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("idOfEvent")));

        MyEvent event = ControllerFactory.get().getEventListController().getAllEvents().get(idOfEvent);

        TextView title = findViewById(R.id.eventTitleTextView);
        TextView description = findViewById(R.id.eventDescriptionTextView);
        TextView time = findViewById(R.id.eventTimeTextView);
        TextView type = findViewById(R.id.eventTypeTextView);

        String typeStr = ServiceFactory.get().getEventService().getEventStringType(event);
        int colorOfEvent = ServiceFactory.get().getEventService().getEventColor(event);

        type.setText(typeStr);
        type.setTextColor(colorOfEvent);

        title.setText(event.getTitle());
        description.setText(event.getDescription());
        time.setText(event.getTime().toString());




    }
}
