package com.eventure.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.activities.*;
import com.eventure.model.MyEvent;

import com.eventure.services.EventService;
import com.eventure.services.ServiceFactory;

import java.util.ArrayList;
import java.util.Date;

public class EventListController extends FrontController {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> getEventTitleList(ArrayList<MyEvent> events){
        return serviceFactory.getEventService().getEventTitleList(events);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToEventActivity(Integer id, AppCompatActivity self){
        MyEvent event = (MyEvent)serviceFactory.getEventService().getEventList().toArray()[id];
        ControllerFactory.get().getEventController().setEvent(event);
        this.goToActivity(self, EventActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToMenuActivity(AppCompatActivity self){
        this.goToActivity(self, MenuActivity.class);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<MyEvent> getAllEvents(){
        return ServiceFactory.get().getEventService().getEventList();
    }

    public ArrayList<MyEvent> getAllEvents(String dayStr, String monthStr, String yearStr){
        int day = Integer.parseInt(dayStr);
        int month = Integer.parseInt(monthStr);
        int year = Integer.parseInt(yearStr);

        return serviceFactory.getEventService().getFilteredByDate(year,month,day);
    }
}
