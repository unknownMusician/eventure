package com.eventure.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.eventure.dao.DaoFactory;
import com.eventure.model.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class EventServiceImp implements EventService {

    private DaoFactory daoFactory;

    public EventServiceImp(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Event> getEventList() {
        ArrayList<Event> events = new ArrayList<>(daoFactory.getEventDao().findAll());
        return events;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public ArrayList<String> getEventTitleList() {
        return getEventList().stream().map(Event::getTitle).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Event search(String title) {
        ArrayList<Event> events = new ArrayList<>(getEventList());
        int size = events.size();
        for (int i = 0; i < size; i++) {
            if (events.get(i).getTitle().equals(title)) {
                return events.get(i);
            }
        }
        return null;
    }

    ////////// filter //////////
    ////////// ↓↓↓↓↓↓ //////////

    @Override
    public ArrayList<Event> getFilteredByDate(Date date) {
        return null;
    }

    @Override
    public ArrayList<Event> getFilteredBy(FilterType sortType) {
        switch (sortType) {
            case Today:
                return getFilteredByToday();
            case ThisWeek:
                return getFilteredByThisWeek();
            case ThisMonth:
                return getFilteredByThisMonth();
            case Favourite:
                return getFilteredByFavourite();
            case My:
                return getFilteredByMy();
        }
        return null;
    }

    protected ArrayList<Event> getFilteredByToday() {
        return null;
    }

    protected ArrayList<Event> getFilteredByThisWeek() {
        return null;
    }

    protected ArrayList<Event> getFilteredByThisMonth() {
        return null;
    }

    protected ArrayList<Event> getFilteredByFavourite() {
        return null;
    }

    protected ArrayList<Event> getFilteredByMy() {
        return null;
    }

    ////////// ↑↑↑↑↑↑ //////////
    ////////// filter //////////
    //------------------------//
    //////////  sort  //////////
    ////////// ↓↓↓↓↓↓ //////////

    @Override
    public ArrayList<Event> getSortedBy(SortType sortType) {
        switch (sortType) {
            case TimeClosestFirst:
                return getSortedByTimeClosest();
            case TimeFurthestFirst:
                return getSortedByTimeFurthest();
            case DistClosestFirst:
                return getSortedByDistClosest();
            case DistFarthestFirst:
                return getSortedByDistFarthest();
        }
        return null;
    }

    protected ArrayList<Event> getSortedByTimeClosest() {
        return null;
    }

    protected ArrayList<Event> getSortedByTimeFurthest() {
        return null;
    }

    protected ArrayList<Event> getSortedByDistClosest() {
        return null;
    }

    protected ArrayList<Event> getSortedByDistFarthest() {
        return null;
    }
}
