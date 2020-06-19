package com.eventure.model;

import java.util.ArrayList;
import java.util.Collection;

public class User {
    public User(Integer userId, String login, String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        userFavoriteEvents = new ArrayList<MyEvent>();
        userEvents = new ArrayList<MyEvent>();
    }
    public User(String login,String password){
        this.login = login;
        this.password = password;
    }




    private Integer userId;
    private String login;
    private String password;
    private ArrayList<MyEvent> userEvents;
    private ArrayList<MyEvent> userFavoriteEvents;


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", userEvents=" + userEvents +
                ", userFavoriteEvents=" + userFavoriteEvents +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<MyEvent> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(ArrayList<MyEvent> userEvents) {
        this.userEvents = userEvents;
    }

    public ArrayList<MyEvent> getUserFavoriteEvents() {
        return userFavoriteEvents;
    }

    public void setUserFavoriteEvents(ArrayList<MyEvent> userFavoriteEvents) {
        this.userFavoriteEvents = userFavoriteEvents;
    }

}
