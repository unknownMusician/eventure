package com.eventure.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class MyEvent implements Serializable {

    private Integer eventId;
    private String title;
    private String description;
    private Date date;
    private Place place;
    private int type;
    private int status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyEvent)) return false;
        MyEvent event = (MyEvent) o;
        return getType() == event.getType() &&
                Objects.equals(getEventId(), event.getEventId()) &&
                Objects.equals(getTitle(), event.getTitle()) &&
                Objects.equals(getDescription(), event.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventId(), getTitle(), getDescription(), getType());
    }

    public int getStatus() {
        return status;
    }
    // 1 - Will be
    // 0 - Right now
    // -1 Ended




    public void setStatus(int status) {
        this.status = status;
    }

    public MyEvent(){

    }

    public MyEvent(Integer eventId, String title, String description, int year, int month, int date,
                   int hour, int min, Place place, int type) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        setDate(year,month,date,hour,min);
        this.place = place;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", time=" + date +
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

    public Date getDate() {
        return date;
    }

    public void setDate(int year, int month, int date, int hours, int min) {
        this.date = new Date(year,month,date,hours,min);
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}

