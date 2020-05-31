package com.eventure.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.activities.*;
import com.eventure.model.Event;
import com.eventure.services.ServiceFactory;

import java.util.ArrayList;

public class EventListController extends FrontController {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> getEventTitleList(){
        return ServiceFactory.getInstance().getEventService().getEventTitleList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToEventActivity(String eventTitle, AppCompatActivity self){
        Event event = ServiceFactory.getInstance().getEventService().search(eventTitle);
        ControllerFactory.get().getEventController().setEvent(event);
        this.goToActivity(self, EventActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToMenuActivity(AppCompatActivity self){
        this.goToActivity(self, MenuActivity.class);
    }
}
