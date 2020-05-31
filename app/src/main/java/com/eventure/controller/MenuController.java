package com.eventure.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuController extends FrontController {

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected MenuController(){
        super();
    }

    public ArrayList<String> getButtonList(){
        return new ArrayList<String>(Arrays.asList("Event list", "Calender", "Map", "Settings"));
    }
}
