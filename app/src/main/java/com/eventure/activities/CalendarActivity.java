package com.eventure.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.eventure.R;
import com.eventure.services.ServiceFactory;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
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

        ServiceFactory.get().getEventService().addEventsOnCalendar(calendar);

        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                Integer year = dateClicked.getYear() + 1900; // To convert it to our time 120 + 1900 = 2020
                Integer month = dateClicked.getMonth() + 1; // Same
                Integer day = dateClicked.getDate();
                Log.d(TAG, "onDayClick: " + year + " " + month + " " + day);
                Intent intent = new Intent(CalendarActivity.this,EventListActivity.class);
                if(ServiceFactory.get().getEventService().hasDateAnEvents(year,month,day)){
                    intent.putExtra("year",year.toString());
                    intent.putExtra("month",month.toString());
                    intent.putExtra("day",day.toString());
                    Toast.makeText(context,"All events for this day",Toast.LENGTH_SHORT);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(context,"There are no events on this day",Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
        FloatingActionButton createEventBtn = findViewById(R.id.addEventBtn);
        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateEventActivity.class));
            }
        });

    }
}