package com.eventure.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.eventure.R;
import com.eventure.dao.DaoFactory;
import com.eventure.model.MyEvent;
import com.eventure.model.Place;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class MapsServiceImp implements MapsService {
    DaoFactory daoFactory;

    public MapsServiceImp(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Place getMyLocation(){
        return UserServiceImp.UserHolder.getLocation();
    }

    public int getIconByType(MyEvent event){
        switch (event.getType()) {
            case 1:
                return R.drawable.ic_point_blue;
            case 2:
                return R.drawable.ic_point_green;
            case 3:
                return R.drawable.ic_point_yellow;
            case 4:
                return R.drawable.ic_point_red;
        }
        return 0;
    }

    public BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
