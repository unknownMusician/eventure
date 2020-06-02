package com.eventure.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.eventure.R;
import com.eventure.model.MyEvent;
import com.eventure.services.ServiceFactory;
import com.eventure.services.UserServiceImp;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EventActivity" ;
    private CheckBox favoritesCheckBox;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CHECK_BOX = "checkBox";
    private boolean tempCheckBox;
    MyEvent event;
    boolean currentState;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        event = (MyEvent) getIntent().getSerializableExtra(MyEvent.class.getName());

        TextView title = findViewById(R.id.eventTitleTextView);
        TextView description = findViewById(R.id.eventDescriptionTextView);
        TextView time = findViewById(R.id.eventTimeTextView);
        TextView type = findViewById(R.id.eventTypeTextView);
        TextView status = findViewById(R.id.eventStatusTextView);
        status.setText(ServiceFactory.get().getEventService().getEventsStatusString(event));
        String typeStr = ServiceFactory.get().getEventService().getEventStringType(event);
        int colorOfEvent = ServiceFactory.get().getEventService().getEventColor(event);
        type.setText(typeStr);
        type.setTextColor(colorOfEvent);
        title.setText(event.getTitle());
        description.setText(event.getDescription());
        time.setText(event.getDate().toString());
        favoritesCheckBox = findViewById(R.id.eventFavoritsCheckBox);
        if(ServiceFactory.get().getEventService().isEventInFavorites(event)){
            favoritesCheckBox.setChecked(true);
        }
        currentState = ServiceFactory.get().getEventService().isEventInFavorites(event);
        favoritesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentState == false){
                    ServiceFactory.get().getEventService().addToFavorites(event);
                    Toast.makeText(EventActivity.this,"Event has been added to your favorites",Toast.LENGTH_SHORT);
                }
                else{
                    ServiceFactory.get().getEventService().removeFromFavorites(event);
                    Toast.makeText(EventActivity.this,"Event has been removed from your favorites",Toast.LENGTH_SHORT);
                }

            }
        });
        Log.d(TAG, "onCreate: " + UserServiceImp.UserHolder.get().getUserFavoriteEvents());
    }
}
