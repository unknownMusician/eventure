package com.eventure.services;

import com.eventure.model.Event;

import java.util.ArrayList;
import java.util.Collection;

public interface EventService {

    Collection<Event> getEventList();
    ArrayList<String> getEventTitleList();
    Event search(String title);
}
