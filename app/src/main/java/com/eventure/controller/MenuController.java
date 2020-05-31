package com.eventure.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.activities.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuController extends FrontController {

    public static MenuController instance;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static MenuController getInstance() {
        if (instance == null) {
            return new MenuController();
        }
        return instance;
    }

    ////////// static line //////////

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected MenuController(){ super(); }

    ///// ///// controller ///// /////

    public ArrayList<String> getButtonList(){
        return new ArrayList<String>(Arrays.asList("Event list", "Calender", "Map", "Settings"));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
