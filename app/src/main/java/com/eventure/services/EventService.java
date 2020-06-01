package com.eventure.services;

import com.eventure.model.MyEvent;

import java.util.ArrayList;

public interface EventService {

    ArrayList<MyEvent> getEventList();
    ArrayList<String> getEventTitleList();
    MyEvent search(String title);
}
