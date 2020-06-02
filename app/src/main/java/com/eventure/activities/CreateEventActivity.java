package com.eventure.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.eventure.R;
import com.eventure.controller.ControllerFactory;
import com.eventure.model.MyEvent;
import com.eventure.model.Place;
import com.eventure.services.ServiceFactory;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "Create Activity" ;
    Integer type,year,month,day,hour,min;
    String title,description;
    Place place;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Button setDateBtn = findViewById(R.id.setDateBtn);
        Button setTimeBtn = findViewById(R.id.setTimeBtn);
        String[] types = {"Lecture","Discussion","Party","Other"};
        Spinner spinnerTypes = findViewById(R.id.setTypeSpinner);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,types);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(stringArrayAdapter);
        EditText titleEditText = findViewById(R.id.setTitleEditText);
        EditText descriptionEditText = findViewById(R.id.setDescriptionEditText);
        EditText placeEditText = findViewById(R.id.setPlaceEditText);
        title = titleEditText.getText().toString();
        description = descriptionEditText.getText().toString();
        //ToDo
        //placeStr = placeEditText.getText().toString();
        place = new Place(14, 14);
        //

        Button createEventBtn = findViewById(R.id.createEventBtn);

        setDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dataPicker = new DatePickerFragment();
                dataPicker.show(getSupportFragmentManager(),"date picker");
            }
        });
        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });
        spinnerTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
                Log.d(TAG, "onCreate: " + type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.d(TAG, "onCreate: " + type);

        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(year == null || month == null || day == null || hour == null || min == null ){
                    Toast.makeText(CreateEventActivity.this,"You didn`t select the date or time",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(title.equals("")||description.equals("")||place.equals("")){
                        Toast.makeText(CreateEventActivity.this,"You didn`t submit all fields",Toast.LENGTH_SHORT);
                    }
                    else{
                        Intent intent = new Intent(CreateEventActivity.this,EventActivity.class);
                        MyEvent newEvent = new MyEvent(0,title,description,year,month,day,hour,min,place,type);
                        ServiceFactory.get().getEventService().addEventToData(newEvent);
                        intent.putExtra(MyEvent.class.getName(),newEvent);
                        startActivity(intent);

                    }
                }
            }
        });

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar s = Calendar.getInstance();
        s.set(Calendar.YEAR,year);
        s.set(Calendar.MONTH,month);
        s.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(s.getTime());
        TextView date = findViewById(R.id.setDateTextView);
        date.setText(currentDateString);
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView time = findViewById(R.id.setTimeTextView);
        if(minute >=0 && minute < 10) {
            time.setText(hourOfDay+":0"+minute);
        }
        else{
            time.setText(hourOfDay+":"+minute);
        }
        this.hour = hourOfDay;
        this.min = minute;
    }
}
