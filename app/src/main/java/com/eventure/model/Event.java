package com.eventure.model;



import java.util.Date;

public class Event {

    private Integer eventId;
    private String title;
    private String description;
    private Date time;
    private String place;
    private String type;

    public Event(Integer eventId, String title, String description,int year,int month,int date,
                 int hour,int min, String place, String type) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        setTime(year,month,date,hour,min);
        this.place = place;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", place='" + place + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(int year,int month,int date,int hours,int min) {
        this.time = new Date(year,month,date,hours,min);
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}

