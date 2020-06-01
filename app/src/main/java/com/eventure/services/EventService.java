package com.eventure.services;

import com.eventure.model.MyEvent;

import java.util.ArrayList;
<<<<<<< HEAD

public interface EventService {

    ArrayList<MyEvent> getEventList();
    ArrayList<String> getEventTitleList();
    MyEvent search(String title);
=======
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
>>>>>>> 86bcdce0f54f4ae1431b673529b89f876cd10b11
}
