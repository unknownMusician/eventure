package com.eventure.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.eventure.R;
import com.eventure.model.MyEvent;
import com.eventure.model.Place;
import com.eventure.services.ServiceFactory;
import com.eventure.services.UserServiceImp;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.eventure.util.Constants.MAPVIEW_BUNDLE_KEY;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private static final String TAG = "Create Activity" ;
    Integer type,year,month,day,hour,min;
    String title,description;
    EditText titleEditText;
    EditText descriptionEditText;
    Place place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        onMapCreate(savedInstanceState);

        Button setDateBtn = findViewById(R.id.setDateBtn);
        Button setTimeBtn = findViewById(R.id.setTimeBtn);

        String[] types = {"Lecture","Discussion","Party","Other"};
        Spinner spinnerTypes = findViewById(R.id.setTypeSpinner);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,types);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(stringArrayAdapter);
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
                    titleEditText = findViewById(R.id.setTitleEditText);
                    descriptionEditText = findViewById(R.id.setDescriptionEditText);
                    title = titleEditText.getText().toString();
                    description = descriptionEditText.getText().toString();
                    //ToDo
                    place = finPlace;
                    //place = new Place(50, 20);
                    //
                    if(title.equals("")||description.equals("")||place.equals("")){
                        Toast.makeText(CreateEventActivity.this,"You didn`t submit all fields",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(),EventActivity.class);
                        MyEvent newEvent = new MyEvent(0,title,description,year,month,day,hour,min,place,type + 1);
                        myRef.child("events").push().setValue(newEvent);
                        ServiceFactory.get().getEventService().addEventToData(newEvent);
                        ServiceFactory.get().getEventService().addToMyEvents(newEvent);
                        intent.putExtra(MyEvent.class.getSimpleName(),newEvent);
                        Toast.makeText(getApplicationContext(),"Your activity has been created!",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
                Log.d(TAG, "onClick: " + year + " " + month +  " " + day +" "+ title +" " + description + " " + place );
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

    ////////// Maps //////////
    //////////  ↓ ↓ //////////

    private MapView mMapView;
    private Place finPlace;

    private void onMapCreate(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if(mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // adding Markers
        Place eventPlace = UserServiceImp.UserHolder.getLocation();
        LatLng eventLatLng = new LatLng(eventPlace.getLatitude(), eventPlace.getLongitude());

        finPlace = eventPlace;
        addMarker(map, eventLatLng);
        showUserLocation(map);
        moveCamera(map, eventPlace);
    }

    private void addMarker(GoogleMap map, LatLng eventLatLng){
        Marker eventMarker = map.addMarker(new MarkerOptions()
                .position(eventLatLng)
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        map.setOnMarkerDragListener(this);
    }

    private void showUserLocation(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
    }

    private void moveCamera(GoogleMap map, Place eventPlace) {
        LatLng leftBottom = new LatLng(
                eventPlace.getLatitude() - 0.02d,
                eventPlace.getLongitude() - 0.02d);
        Log.d(TAG, "Boundaries: leftBottom: " + leftBottom);
        LatLng rightTop = new LatLng(
                eventPlace.getLatitude() + 0.02d,
                eventPlace.getLongitude() + 0.02d);
        Log.d(TAG, "Boundaries: rightTop: " + rightTop);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(leftBottom, rightTop), 1));
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        //
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        //
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng markerLatLng = marker.getPosition();
        finPlace = new Place(markerLatLng.latitude, markerLatLng.longitude);
    }
}
