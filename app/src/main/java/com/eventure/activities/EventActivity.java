package com.eventure.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.eventure.R;
import com.eventure.controller.ControllerFactory;
import com.eventure.controller.MapsController;
import com.eventure.model.MyEvent;
import com.eventure.model.Place;
import com.eventure.services.ServiceFactory;
import com.eventure.services.UserServiceImp;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.eventure.util.Constants.ERROR_DIALOG_REQUEST;
import static com.eventure.util.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.eventure.util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class EventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "EventActivity" ;
    private CheckBox favoritesCheckBox;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CHECK_BOX = "checkBox";
    private boolean tempCheckBox;
    MyEvent event;
    boolean currentState;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onMapCreate();
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
        Log.d(TAG, "onCreate: " + UserServiceImp.UserHolder.getUser().getUserFavoriteEvents());
    }

    //////////  Map creating //////////
    //////////  ↓ ↓ ↓ ↓ ↓ ↓  //////////

    private void onMapCreate(){
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // GPS
        if (ActivityCompat.checkSelfPermission(EventActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(EventActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        addMarker();
        // ToDo
        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(new LatLng(44.2, 21.7), new LatLng(52.6, 40.6)), 50));
    }

    private void addMarker() {
        // ToDo
        MapsController controller = ControllerFactory.get().getMapsController();
            Place eventPlace = event.getPlace();
            LatLng eventLatLng = new LatLng(eventPlace.getLatitude(), eventPlace.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(eventLatLng).title(event.getTitle()).icon(bitmapDescriptorFromVector(controller.getIconByType(event))));
            marker.setTag(event);
    }

    //////////  ↑ ↑ ↑ ↑ ↑ ↑  //////////
    //////////  Map creating //////////
    //-------------------------------//
    ////////// Icon creating //////////
    //////////  ↓ ↓ ↓ ↓ ↓ ↓  //////////

    private BitmapDescriptor bitmapDescriptorFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(getApplicationContext(), vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    //////////  ↑ ↑ ↑ ↑ ↑ ↑  //////////
    ////////// Icon creating //////////
    //-------------------------------//

    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServices()) {
            if (mLocationPermissionGranted) {
                showTheWay();
                getLastKnownLocation();
            } else {
                getLocationPermission();
            }
        }
    }

    private void showTheWay() {

    }

    //////////     GPS-receiving      //////////
    //////////      ↓ ↓ ↓ ↓ ↓ ↓       //////////

    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation: called.");

        if (ActivityCompat.checkSelfPermission(EventActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(EventActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    LatLng geoPoint = new LatLng(location.getLatitude(), location.getLongitude());
                    //ToDo
                    Place place = new Place(geoPoint.latitude, geoPoint.longitude);
                    UserServiceImp.UserHolder.setLocation(place);
                    //
                    Log.d(TAG, "onComplete: latitude: " + geoPoint.latitude);
                    Log.d(TAG, "onComplete: longitude: " + geoPoint.longitude);
                }
            }
        });
    }

    //////////      ↑ ↑ ↑ ↑ ↑ ↑       //////////
    //////////     GPS-receiving      //////////
    //----------------------------------------//
    ////////// Services & GPS-showing //////////
    ////////// ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓  //////////

    boolean mLocationPermissionGranted = false;

    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    // somewhere ok
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    // ok
    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        assert manager != null;
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    //somewhere ok
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            showTheWay();
            getLastKnownLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    // ok
    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(EventActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(EventActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //somewhere ok
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    // somewhere ok
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    showTheWay();
                    getLastKnownLocation();
                } else {
                    getLocationPermission();
                }
            }
        }

    }
    //////////  //////////
}
