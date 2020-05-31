package com.eventure.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

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
    protected MenuController(){
        super();
    }

    public ArrayList<String> getButtonList(){
        return new ArrayList<String>(Arrays.asList("Event list", "Calender", "Map", "Settings"));
    }
}
