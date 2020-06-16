package com.eventure.services;

import android.content.Context;

import com.eventure.model.MyEvent;
import com.eventure.model.Place;
import com.google.android.gms.maps.model.BitmapDescriptor;

public interface MapsService {

    Place getMyLocation();
    int getIconByType(MyEvent event);
    BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId);
}
