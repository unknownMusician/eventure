package com.eventure.services;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.eventure.dao.DaoFactory;
import com.eventure.model.MyEvent;
import com.eventure.model.User;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static android.view.View.inflate;
import static androidx.constraintlayout.widget.Constraints.TAG;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ArrayList<String> getEventTitleList(ArrayList<MyEvent> events) {
        ArrayList<String> titles = new ArrayList<>();
        String title = "";
        for (MyEvent event:events) {
            title = event.getTitle() + "    ("+getEventsStatusString(event)+")";
            titles.add(title);
        }
        return titles;
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
            if (event.getDate().getYear() == year - 1900 && event.getDate().getMonth() == month - 1
                    && event.getDate().getDate() == day) {
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
        return getFilteredByDate(year, month, day);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<MyEvent> getFilteredByThisWeek() {
        ArrayList<MyEvent> eventOnThisWeek = new ArrayList<>();
        LocalDate currentDate = java.time.LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int date = currentDate.getDayOfMonth();
        for (int i = 0; i < 8; i++) {
            eventOnThisWeek.addAll(getFilteredByDate(year, month, date + i));
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
            if (event.getDate().getYear() == year - 1900 && event.getDate().getMonth() == month - 1) {
                eventsOnThisMonth.add(event);
            }
        }
        return eventsOnThisMonth;
    }

    public ArrayList<MyEvent> getFilteredByFavourite() {
        return UserServiceImp.UserHolder.getUser().getUserFavoriteEvents();
    }

    @Override
    public ArrayList<MyEvent> getFilteredByMyEvents() {
        return UserServiceImp.UserHolder.getUser().getUserEvents();
    }

    protected ArrayList<MyEvent> getFilteredByMy() {
        return new ArrayList<MyEvent>(UserServiceImp.UserHolder.getUser().getUserEvents());
    }

    ////////// ↑↑↑↑↑↑ //////////
    ////////// filter //////////
    //------------------------//
    //////////  sort  //////////
    ////////// ↓↓↓↓↓↓ //////////

    @Override
    public ArrayList<MyEvent> getSortedBy(SortType sortType, ArrayList events) {
        switch (sortType) {
            case TimeClosestFirst:
                return getSortedByTimeClosest(events);
            case TimeFurthestFirst:
                return getSortedByTimeFurthest(events);
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
                        .parse(format1.format(events.get(i).getDate())).getTime();

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
                colorOfEvent = Color.rgb(32, 165, 35);
                break;
            case 3:
                colorOfEvent = Color.rgb(207, 204, 29);
                break;
            case 4:
                colorOfEvent = Color.RED;
                break;
        }
        return colorOfEvent;
    }

    // QuickSort


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

    public boolean hasDateAnEvents(int year, int month, int day) {
        return !getFilteredByDate(year, month, day).isEmpty();
    }


    public ArrayList<MyEvent> getSortedByTimeClosest(ArrayList<MyEvent> events) {
        events.sort(new Comparator<MyEvent>() {
            @Override
            public int compare(MyEvent o1, MyEvent o2) {
                if (o1.getDate().getTime() > o2.getDate().getTime()) {
                    return -1;
                }
                if (o1.getDate().getTime() < o2.getDate().getTime()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return events;
    }

    public ArrayList<MyEvent> getUserFavoritesEvents(){
        return new ArrayList<>(UserServiceImp.UserHolder.getUser().getUserFavoriteEvents());
    }
    public void addEventToData(MyEvent event){
        daoFactory.getEventDao().insert(event,true);
    }

    @Override
    public void addToMyEvents(MyEvent event) {
        UserServiceImp.UserHolder.getUser().getUserEvents().add(event);
    }

    @Override
    public ArrayList<MyEvent> getSortedByStatus(String status,ArrayList<MyEvent> events) {
        ArrayList<MyEvent> eventsSortedByStatus = new ArrayList<>();
        for (MyEvent event:events) {
            if(ServiceFactory.get().getEventService().getEventsStatusString(event).equals(status)){
                eventsSortedByStatus.add(event);
            }
        }
        return eventsSortedByStatus;
    }

    @Override
    public ArrayList<MyEvent> getSortedByType(String type, ArrayList<MyEvent> events) {
        if(type.equals("Any Type")){
            return events;
        }
        ArrayList<MyEvent> sortedEvents = new ArrayList<>();

        for (MyEvent event:events) {
            if(ServiceFactory.get().getEventService().getEventStringType(event).equals(type)){
                sortedEvents.add(event);
            }
        }
        return sortedEvents;

    }

    @Override
    public ArrayList<MyEvent> getUserCreatedEvents() {
        return null;
    }

    public ArrayList<MyEvent> getSortedByTimeFurthest(ArrayList<MyEvent> events) {
        events.sort(new Comparator<MyEvent>() {
            @Override
            public int compare(MyEvent o1, MyEvent o2) {
                if (o1.getDate().getTime() > o2.getDate().getTime()) {
                    return 1;
                }
                if (o1.getDate().getTime() < o2.getDate().getTime()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return events;

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getEventsStatusString(MyEvent event){
        ArrayList<MyEvent> events = new ArrayList<>();
        events.add(event);
        return getListOfEventsStatuses(events).get(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> getListOfEventsStatuses(ArrayList<MyEvent> events) {
        ArrayList<String> statuses = new ArrayList<>();
        if(events.isEmpty()){
            return statuses;
        }
        LocalDate date = java.time.LocalDate.now();
        LocalTime time = java.time.LocalTime.now();
        int year = date.getYear() - 1900;
        int month = date.getMonthValue() - 1;
        int day = date.getDayOfMonth();
        int hours = time.getHour();
        int minutes = time.getMinute();
        for (MyEvent event : events) {
            if (event.getDate().getYear() == year && event.getDate().getMonth() == month
                    && event.getDate().getDate() == day) {
                if(event.getDate().getHours() < hours) {
                    statuses.add("Active");
                }
                if(event.getDate().getHours()==hours){
                    if(event.getDate().getMinutes() <= minutes){
                        statuses.add("Active");
                    }
                    else{
                        statuses.add("UpComing");
                    }
                }
                if(event.getDate().getHours() > hours){
                    statuses.add("UpComing");
                }
            }
            else{
                Log.d(TAG, "getListOfEventsStatuses: " + event.getDate().getYear() + " " + event.getDate().getMonth() + "\n" + year + " " +month);
                if(event.getDate().getYear()>year){
                    statuses.add("UpComing");
                }
                if(event.getDate().getYear()==year){
                    if(event.getDate().getMonth() > month){
                        statuses.add("UpComing");
                    }
                    if(event.getDate().getMonth()==month){
                        if(event.getDate().getDate()>day){
                            statuses.add("Upcoming");
                        }
                        if(event.getDate().getDate()<day){
                            statuses.add("Finished");
                        }


                    }


                    if(event.getDate().getMonth() < month){
                        statuses.add("Finished");
                    }
                }
                if(event.getDate().getYear() < year){
                    statuses.add("Finished");
                }

            }
        }
        return statuses;
    }


    protected ArrayList<MyEvent> getSortedByDistClosest() {
        return null;
    }

    protected ArrayList<MyEvent> getSortedByDistFarthest() {
        return null;
    }
    public boolean isEventInFavorites(MyEvent event){
        if(UserServiceImp.UserHolder.getUser().getUserFavoriteEvents() == null){
            return false;
        }
        ArrayList<MyEvent> favorites = UserServiceImp.UserHolder.getUser().getUserFavoriteEvents();
        return favorites.contains(event);
    }
    public void addToFavorites(MyEvent event){
        UserServiceImp.UserHolder.getUser().getUserFavoriteEvents().add(event);
    }
    public void removeFromFavorites(MyEvent event){
        UserServiceImp.UserHolder.getUser().getUserFavoriteEvents().remove(event);
    }


}
