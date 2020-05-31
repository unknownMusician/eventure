package com.eventure.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.eventure.dao.DaoFactory;
import com.eventure.model.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class EventServiceImp implements EventService {

    private DaoFactory daoFactory;

    public EventServiceImp(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Event> getEventList() {
        ArrayList<Event> events =  new ArrayList<>(daoFactory.getEventDao().findAll());
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
}
