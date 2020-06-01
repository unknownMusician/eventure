package com.eventure.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

        ServiceFactory.get().getEventService().addEventsOnCalendar(calendar);

        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                Integer year = dateClicked.getYear();
                Integer month = dateClicked.getMonth();
                Integer day = dateClicked.getDate();
                Intent intent = new Intent(CalendarActivity.this,EventListActivity.class);
                if(ServiceFactory.get().getEventService().hasDateAnEvents(year,month,day)){
                    intent.putExtra("year",year.toString());
                    intent.putExtra("month",month.toString());
                    intent.putExtra("day",day.toString());
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
    }
}