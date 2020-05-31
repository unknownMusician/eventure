package com.eventure.model;

import java.util.Collection;

public class User {
    public User(Integer userId, String login, String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

    private Integer userId;
    private String login;
    private String password;
    private Collection userEvents;
    private Collection userFavoriteEvents;

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

    public Collection getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(Collection userEvents) {
        this.userEvents = userEvents;
    }

    public Collection getUserFavoriteEvents() {
        return userFavoriteEvents;
    }

    public void setUserFavoriteEvents(Collection userFavoriteEvents) {
        this.userFavoriteEvents = userFavoriteEvents;
    }

}
