package com.eventure.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.eventure.dao.DaoFactory;
import com.eventure.model.MyEvent;

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

    public ArrayList<MyEvent> getEventList() {
        ArrayList<MyEvent> events = new ArrayList<>(daoFactory.getEventDao().findAll());
        return events;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public ArrayList<String> getEventTitleList() {
        return getEventList().stream().map(MyEvent::getTitle).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public MyEvent search(String title) {
        ArrayList<MyEvent> events = new ArrayList<>(getEventList());
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
    public ArrayList<MyEvent> getFilteredByDate(Date date) {
        return null;
    }

    @Override
    public ArrayList<MyEvent> getFilteredBy(FilterType sortType) {
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

    protected ArrayList<MyEvent> getFilteredByToday() {
        return null;
    }

    protected ArrayList<MyEvent> getFilteredByThisWeek() {
        return null;
    }

    protected ArrayList<MyEvent> getFilteredByThisMonth() {
        return null;
    }

    protected ArrayList<MyEvent> getFilteredByFavourite() {
        return null;
    }

    protected ArrayList<MyEvent> getFilteredByMy() {
        return null;
    }

    ////////// ↑↑↑↑↑↑ //////////
    ////////// filter //////////
    //------------------------//
    //////////  sort  //////////
    ////////// ↓↓↓↓↓↓ //////////

    @Override
    public ArrayList<MyEvent> getSortedBy(SortType sortType) {
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

    protected ArrayList<MyEvent> getSortedByTimeClosest() {
        return null;
    }

    protected ArrayList<MyEvent> getSortedByTimeFurthest() {
        return null;
    }

    protected ArrayList<MyEvent> getSortedByDistClosest() {
        return null;
    }

    protected ArrayList<MyEvent> getSortedByDistFarthest() {
        return null;
    }
}
