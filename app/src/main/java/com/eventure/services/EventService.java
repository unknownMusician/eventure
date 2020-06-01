package com.eventure.services;

import com.eventure.model.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public interface EventService {

    ArrayList<Event> getEventList();

    ArrayList<String> getEventTitleList();

    Event search(String title);

    ArrayList<Event> getFilteredByDate(Date date);
    ArrayList<Event> getFilteredBy(FilterType sortType);

    static enum FilterType {
        Today,
        ThisWeek,
        ThisMonth,
        Favourite,
        My
    }

    ArrayList<Event> getSortedBy(SortType sortType);

    static enum SortType {
        TimeClosestFirst,
        TimeFurthestFirst,
        DistClosestFirst,
        DistFarthestFirst
    }
}
