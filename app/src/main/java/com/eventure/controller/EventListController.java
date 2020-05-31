package com.eventure.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.activities.*;
import com.eventure.model.Event;
import com.eventure.services.ServiceFactory;

import java.util.ArrayList;

public class EventListController extends FrontController {

    public static EventListController instance;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static EventListController getInstance() {
        if (instance == null) {
            return new EventListController();
        }
        return instance;
    }

    ////////// static line //////////

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected EventListController(){ super(); }

    ///// ///// controller ///// /////

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> getEventTitleList(){
        return ServiceFactory.getInstance().getEventService().getEventTitleList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToEventActivity(String eventTitle, AppCompatActivity self){
        Event event = ServiceFactory.getInstance().getEventService().search(eventTitle);
        EventController.getInstance().setEvent(event);
        this.goToActivity(self, EventActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToMenuActivity(AppCompatActivity self){
        this.goToActivity(self, MenuActivity.class);
    }
}
