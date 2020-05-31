package com.eventure.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.activities.MenuActivity;
import com.eventure.model.Event;
import com.eventure.services.EventService;
import com.eventure.services.ServiceFactory;

public class EventController extends FrontController {

    private Event event;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Event getEvent() {
       return event;
    }

    public void setEvent(Event event){
        this.event = event;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToMenuActivity(AppCompatActivity self){
        this.goToActivity(self, MenuActivity.class);
    }
}
