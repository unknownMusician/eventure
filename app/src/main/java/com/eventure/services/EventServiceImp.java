package com.eventure.services;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.eventure.dao.DaoFactory;
import com.eventure.model.MyEvent;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
    public ArrayList<String> getEventTitleList(ArrayList<MyEvent> events) {
        return events.stream().map(MyEvent::getTitle).collect(Collectors.toCollection(ArrayList::new));
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
    public ArrayList<MyEvent> getFilteredByDate(int year, int month, int day) {
        ArrayList<MyEvent> events = ServiceFactory.get().getEventService().getEventList();
        ArrayList<MyEvent> eventOnThisDate = new ArrayList<>();
        for (MyEvent event : events) {
            if (event.getTime().getYear() == year-1900 && event.getTime().getMonth() == month - 1
                    && event.getTime().getDate() == day) {
                eventOnThisDate.add(event);
            }
        }
        return eventOnThisDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<MyEvent> getFilteredByToday() {
        LocalDate currentDate = java.time.LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();
        return getFilteredByDate(year,month,day);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<MyEvent> getFilteredByThisWeek() {
        ArrayList<MyEvent> eventOnThisWeek = new ArrayList<>();
        LocalDate currentDate = java.time.LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int date = currentDate.getDayOfMonth();
        for (int i = 0; i < 8 ; i++) {
            eventOnThisWeek.addAll(getFilteredByDate(year,month,date+i));
        }
        return eventOnThisWeek;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<MyEvent> getFilteredByThisMonth() {
        ArrayList<MyEvent> events = ServiceFactory.get().getEventService().getEventList();
        ArrayList<MyEvent> eventsOnThisMonth = new ArrayList<>();
        LocalDate currentDate = java.time.LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        for (MyEvent event : events) {
            if (event.getTime().getYear() == year - 1900 && event.getTime().getMonth() == month - 1) {
                eventsOnThisMonth.add(event);
            }
        }
        return eventsOnThisMonth;
    }

    public ArrayList<MyEvent> getFilteredByFavourite() {
        return new ArrayList<MyEvent>(UserServiceImp.UserHolder.get().getUserFavoriteEvents());
    }

    protected ArrayList<MyEvent> getFilteredByMy() {
        return new ArrayList<MyEvent>(UserServiceImp.UserHolder.get().getUserEvents());
    }

    ////////// ↑↑↑↑↑↑ //////////
    ////////// filter //////////
    //------------------------//
    //////////  sort  //////////
    ////////// ↓↓↓↓↓↓ //////////

    @Override
    public ArrayList<MyEvent> getSortedBy(SortType sortType,ArrayList events) {
        switch (sortType) {
            case TimeClosestFirst:
                return getSortedByTimeClosest(events);
            case TimeFurthestFirst:
                return getSortedByTimeFurthest();
            case DistClosestFirst:
                return getSortedByDistClosest();
            case DistFarthestFirst:
                return getSortedByDistFarthest();
        }
        return null;
    }

    public void addEventsOnCalendar(CompactCalendarView calendar) {
        // Lection type - 1
        // Disscution type - 2
        // Party type - 3
        // Other type - 4
        ArrayList<MyEvent> events = ServiceFactory.get().getEventService().getEventList();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (int i = 0; i < events.size(); i++) {
            long timeOfEvent = 0;
            try {
                timeOfEvent = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        .parse(format1.format(events.get(i).getTime())).getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            int colorOfEvent = getEventColor(events.get(i));


            Event event = new Event(colorOfEvent, timeOfEvent, "Bitches");
            calendar.addEvent(event);
        }

    }

    public int getEventColor(MyEvent event) {
        int colorOfEvent = 0;
        switch (event.getType()) {
            case 1:
                colorOfEvent = Color.BLUE;
                break;
            case 2:
                colorOfEvent = Color.rgb(32,165,35);
                break;
            case 3:
                colorOfEvent = Color.rgb(207,204,29);
                break;
            case 4:
                colorOfEvent = Color.RED;
                break;
        }
        return colorOfEvent;
    }

    public String getEventStringType(MyEvent event) {
        String type = "";
        switch (event.getType()) {
            case 1:
                type = "Lecture";
                break;
            case 2:
                type = "Discussion";
                break;
            case 3:
                type = "Party";
                break;
            case 4:
                type = "Other";
                break;
        }
        return type;
    }

    public boolean hasDateAnEvents(int year,int month,int day){
        return !getFilteredByDate(year, month, day).isEmpty();
    }


    public ArrayList<MyEvent> getSortedByTimeClosest(ArrayList<MyEvent> events) {
        ArrayList<MyEvent> sortedEvents = new ArrayList<>();
       /* long[] times = new long[events.size()];

        for (int i = 0; i < times.length ; i++) {
            times[i] = events.get(i).getTime().getTime();
        }
        Arrays.sort(times);
        ArrayList<MyEvent> sortedEvents = new ArrayList<>();
        for (int i = 0; i <times.length ; i++) {
                if (events.get(i).getTime().getTime()==times[i]){
                    sortedEvents.add(events.get(i));
            }
        }

        */
        return sortedEvents;
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
