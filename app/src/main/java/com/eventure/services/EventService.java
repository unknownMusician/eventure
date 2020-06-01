package com.eventure.services;

import com.eventure.model.MyEvent;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Date;

public interface EventService {

    ArrayList<MyEvent> getEventList();

    ArrayList<String> getEventTitleList();

    MyEvent search(String title);

    ArrayList<MyEvent> getFilteredByDate(Date date);
    ArrayList<MyEvent> getFilteredBy(FilterType sortType);

    static enum FilterType {
        Today,
        ThisWeek,
        ThisMonth,
        Favourite,
        My
    }

    ArrayList<MyEvent> getSortedBy(SortType sortType);

    static enum SortType {
        TimeClosestFirst,
        TimeFurthestFirst,
        DistClosestFirst,
        DistFarthestFirst
    }
}
