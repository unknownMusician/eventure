package com.eventure.services;

import com.eventure.model.MyEvent;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Date;

public interface EventService {

    ArrayList<MyEvent> getEventList();

    ArrayList<String> getEventTitleList(ArrayList<MyEvent> events);

    void addEventsOnCalendar(CompactCalendarView calendar);
    MyEvent search(String title);
    int getEventColor(MyEvent event);
    String getEventStringType(MyEvent event);
    boolean hasDateAnEvents(int year,int month,int day);


    ArrayList<MyEvent> getFilteredByDate(int year,int month,int day);
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
