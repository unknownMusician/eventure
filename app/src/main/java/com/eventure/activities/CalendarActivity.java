package com.eventure.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.eventure.R;
import com.eventure.model.MyEvent;
import com.eventure.services.ServiceFactory;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "Calendar Activity" ;
    CompactCalendarView calendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        calendar = findViewById(R.id.compactcalendar_view);
        calendar.setUseThreeLetterAbbreviation(true);

        Event ev1 = new Event(Color.RED, 1591011144000L, "Teachers' Professional Day");
        calendar.addEvent(ev1);

        // Lection type - 1
        // Disscution type - 2
        // Party type - 3
        // Other type - 4

        ArrayList<MyEvent> events = ServiceFactory.get().getEventService().getEventList();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for(int i = 0;i < events.size();i++){
            long timeOfEvent = 0;
            try {
                timeOfEvent = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        .parse(format1.format(events.get(i).getTime())).getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            int colorOfEvent = Color.BLACK; // default color
            switch (events.get(i).getType()){
                case 1:
                    colorOfEvent = Color.BLUE;
                    break;
                case 2:
                    colorOfEvent = Color.GREEN;
                    break;
                case 3:
                    colorOfEvent = Color.YELLOW;
                    break;
                case 4:
                    colorOfEvent = Color.WHITE;
            }

            Event event = new Event(colorOfEvent,timeOfEvent,"Bitches");
            Log.d(TAG, "onCreate: " + timeOfEvent);
            Log.d(TAG, "onCreate: " + calendar);
            calendar.addEvent(event);
        }





        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                Toast.makeText(context,"Hello!",Toast.LENGTH_SHORT).show();
                int year = dateClicked.getYear();
                int month = dateClicked.getMonth();
                int day = dateClicked.getDate();
                Log.d(TAG, "onDayClick: " + year + " " + month + " " + day);


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }
}