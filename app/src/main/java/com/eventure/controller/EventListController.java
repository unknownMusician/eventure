package com.eventure.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class EventListController {

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
    protected EventListController(){
        super();
    }
}
