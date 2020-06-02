package com.eventure.model;


import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class MyEvent implements Serializable {

    private Integer eventId;
    private String title;
    private String description;
    private Date time;
    private LatLng place;
    private int type;
    private int status;

    public int getStatus() {
        return status;
    }
    // 1 - Will be
    // 0 - Right now
    // -1 Ended

    public void setStatus(int status) {
        this.status = status;
    }

    public MyEvent(Integer eventId, String title, String description, int year, int month, int date,
                   int hour, int min, LatLng place, int type) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        setDate(year,month,date,hour,min);
        this.place = place;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyEvent)) return false;
        MyEvent myEvent = (MyEvent) o;
        return getType() == myEvent.getType() &&
                getStatus() == myEvent.getStatus() &&
                Objects.equals(getEventId(), myEvent.getEventId()) &&
                Objects.equals(getTitle(), myEvent.getTitle()) &&
                Objects.equals(getDescription(), myEvent.getDescription()) &&
                Objects.equals(getDate(), myEvent.getDate()) &&
                Objects.equals(getPlace(), myEvent.getPlace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventId(), getTitle(), getDescription(), getDate(), getPlace(), getType(), getStatus());
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

    public LatLng getPlace() {
        return place;
    }

    public void setPlace(LatLng place) {
        this.place = place;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}

