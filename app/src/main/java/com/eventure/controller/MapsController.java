package com.eventure.controller;

import com.eventure.R;
import com.eventure.model.MyEvent;
import com.eventure.services.ServiceFactory;
import com.eventure.services.UserServiceImp;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MapsController extends FrontController {

    public LatLng getMyLocation(){
        return UserServiceImp.UserHolder.getLocation();
    }

    public ArrayList<MyEvent> getAllEvents(){
        return ServiceFactory.get().getEventService().getEventList();
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
}
