package com.eventure.controller;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuController extends FrontController {

    public ArrayList<String> getButtonList(){
        return new ArrayList<String>(Arrays.asList("Event list", "Calender", "Map", "Settings"));
    }
}
