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
    ArrayList<MyEvent> getFilteredByToday();
    ArrayList<MyEvent> getFilteredByThisWeek();
    ArrayList<MyEvent> getFilteredByThisMonth();
    ArrayList<MyEvent> getFilteredByFavourite();
    ArrayList<MyEvent> getFilteredByMyEvents();
    ArrayList<MyEvent> getSortedByTimeFurthest(ArrayList<MyEvent> events);
    ArrayList<String> getListOfEventsStatuses(ArrayList<MyEvent> events);
    String getEventsStatusString(MyEvent event);
    ArrayList<MyEvent> getUserFavoritesEvents();
    ArrayList<MyEvent> getUserCreatedEvents();
    void addToFavorites(MyEvent event);
    boolean isEventInFavorites(MyEvent event);
    void removeFromFavorites(MyEvent event);
    void addEventToData(MyEvent event);
    void addToMyEvents(MyEvent event);
    ArrayList<MyEvent> getSortedByStatus(String status,ArrayList<MyEvent> events);
    ArrayList<MyEvent> getSortedByType(String type,ArrayList<MyEvent> events);






    ArrayList<MyEvent> getFilteredByDate(int year,int month,int day);
    ArrayList<MyEvent> getFilteredBy(FilterType sortType);

    static enum FilterType {
        Today,
        ThisWeek,
        ThisMonth,
        Favourite,
        My
    }

    ArrayList<MyEvent> getSortedBy(SortType sortType,ArrayList<MyEvent> events);

    static enum SortType {
        TimeClosestFirst,
        TimeFurthestFirst,
        DistClosestFirst,
        DistFarthestFirst
    }
}
