package com.eventure.controller;

import androidx.appcompat.app.AppCompatActivity;

import com.eventure.activities.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuController extends FrontController {

    public ArrayList<String> getButtonList(){
        return new ArrayList<String>(Arrays.asList("Event list", "Calender", "Map", "Settings"));
    }

    public void goToEventListActivity(AppCompatActivity self){
        this.goToActivity(self, EventListActivity.class);
    }/*

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToMapActivity(AppCompatActivity self){
        this.goToActivity(self, MapActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToCalendarActivity(AppCompatActivity self){
        this.goToActivity(self, CalendarActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goToSettingsActivity(AppCompatActivity self){
        this.goToActivity(self, SettingsActivity.class);
    }*/
}
