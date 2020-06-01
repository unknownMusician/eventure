package com.eventure.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.activities.MenuActivity;
import com.eventure.model.MyEvent;

public class EventController extends FrontController {

    private MyEvent event;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MyEvent getEvent() {
       return event;
    }

    public void setEvent(MyEvent event){
        this.event = event;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToMenuActivity(AppCompatActivity self){
        this.goToActivity(self, MenuActivity.class);
    }
}
