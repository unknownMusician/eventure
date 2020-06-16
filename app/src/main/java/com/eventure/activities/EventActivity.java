package com.eventure.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.eventure.R;
import com.eventure.model.MyEvent;
import com.eventure.model.Place;
import com.eventure.services.MapsService;
import com.eventure.services.ServiceFactory;
import com.eventure.services.UserServiceImp;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.ArrayList;
import java.util.List;

import static com.eventure.util.Constants.MAPVIEW_BUNDLE_KEY;

public class EventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "EventActivity" ;
    private CheckBox favoritesCheckBox;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CHECK_BOX = "checkBox";
    private boolean tempCheckBox;
    boolean currentState;
    MyEvent event;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        onMapCreate(savedInstanceState);
        
        event = (MyEvent) getIntent().getSerializableExtra(MyEvent.class.getSimpleName());
        Log.d(TAG, "onCreate: " + getIntent().getSerializableExtra(MyEvent.class.getSimpleName()).hashCode());

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
        currentState = UserServiceImp.UserHolder.getUser().getUserFavoriteEvents().contains(event);
        if(currentState){
            favoritesCheckBox.setChecked(true);
        }

        favoritesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentState = UserServiceImp.UserHolder.getUser().getUserFavoriteEvents().contains(event);
                if(!currentState){
                    ServiceFactory.get().getEventService().addToFavorites(event);
                    Toast.makeText(EventActivity.this,"Event has been added to your favorites",Toast.LENGTH_SHORT).show();
                }
                else{
                    ServiceFactory.get().getEventService().removeFromFavorites(event);
                    Toast.makeText(EventActivity.this,"Event has been removed from your favorites",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    ////////// Maps //////////
    //////////  ↓ ↓ //////////

    private MapView mMapView;
    private GoogleMap mMap;
    private GeoApiContext mGeoApiContext = null;

    private void onMapCreate(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);

        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder().apiKey(getString(R.string.google_maps_key)).build();
        }
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
        Place eventPlace = event.getPlace();
        LatLng eventLatLng = new LatLng(eventPlace.getLatitude(), eventPlace.getLongitude());
        mMap = map;

        Marker marker = addMarker(eventLatLng, ServiceFactory.get().getMapsService());
        logRoutes(marker);
        showUserLocation();
        moveCamera();
    }

    private Marker addMarker(LatLng eventLatLng, MapsService service){
        Marker marker = mMap
                .addMarker(new MarkerOptions()
                        .position(eventLatLng)
                        .title(event
                                .getTitle())
                        .icon(
                                service
                                        .bitmapDescriptorFromVector(getApplicationContext(), service.getIconByType(event))));
        return marker;
    }

    private void showUserLocation() {
        if (ActivityCompat.checkSelfPermission(EventActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(EventActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void moveCamera() {
        Place eventPlace = event.getPlace();
        Place userPlace = UserServiceImp.UserHolder.getLocation();
        Place straightDistance = new Place(
                Math.abs(eventPlace.getLatitude() - userPlace.getLatitude()),
                Math.abs(eventPlace.getLongitude() - userPlace.getLongitude()));
        Log.d(TAG, "Boundaries: straightDistance: " + straightDistance);
        LatLng leftBottom = new LatLng(
                Math.min(eventPlace.getLatitude(), userPlace.getLatitude()) - 0.1d * straightDistance.getLatitude(),
                Math.min(eventPlace.getLongitude(), userPlace.getLongitude()) - 0.1d * straightDistance.getLongitude());
        Log.d(TAG, "Boundaries: leftBottom: " + leftBottom);
        LatLng rightTop = new LatLng(
                Math.max(eventPlace.getLatitude(), userPlace.getLatitude()) + 0.1d * straightDistance.getLatitude(),
                Math.max(eventPlace.getLongitude(), userPlace.getLongitude()) + 0.1d * straightDistance.getLongitude());
        Log.d(TAG, "Boundaries: rightTop: " + rightTop);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(leftBottom, rightTop), 1));
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

    //////////    ↑ ↑     //////////
    //////////    Maps    //////////
    //----------------------------//
    ////////// Directions //////////
    ////////// ↓ ↓ ↓ ↓ ↓  //////////

    private void addPolylinesToMap(final DirectionsResult result){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: result routes: " + result.routes.length);

                for(DirectionsRoute route: result.routes){
                    Log.d(TAG, "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    // This loops through all the LatLng coordinates of ONE polyline.
                    for(com.google.maps.model.LatLng latLng: decodedPath){

//                        Log.d(TAG, "run: latlng: " + latLng.toString());

                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    polyline.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                    polyline.setClickable(true);

                }
            }
        });
    }

    private void calculateDirections(Marker marker){
        Log.d(TAG, "calculateDirections: calculating directions.");

        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                marker.getPosition().latitude,
                marker.getPosition().longitude
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(true);
        directions.origin(
                new com.google.maps.model.LatLng(
                        UserServiceImp.UserHolder.getLocation().getLatitude(),
                        UserServiceImp.UserHolder.getLocation().getLongitude()
                )
        );
        Log.d(TAG, "calculateDirections: destination: " + destination.toString());
        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d(TAG, "calculateDirections: routes: " + result.routes[0].toString());
                Log.d(TAG, "calculateDirections: duration: " + result.routes[0].legs[0].duration);
                Log.d(TAG, "calculateDirections: distance: " + result.routes[0].legs[0].distance);
                Log.d(TAG, "calculateDirections: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());

                addPolylinesToMap(result);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "calculateDirections: Failed to get directions: " + e.getMessage() );

            }
        });
    }

    private void logRoutes(Marker marker){
        calculateDirections(marker);
    }
}
