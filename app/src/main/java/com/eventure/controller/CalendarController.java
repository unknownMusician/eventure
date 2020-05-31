package com.eventure.controller;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class CalendarController extends FrontController {

    public static CalendarController instance;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static CalendarController getInstance() {
        if (instance == null) {
            return new CalendarController();
        }
        return instance;
    }

    ////////// static line //////////

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected CalendarController(){ super(); }

    ///// ///// controller ///// /////

    // public Map<Date, Collection<Color>> getColors(Date
}
