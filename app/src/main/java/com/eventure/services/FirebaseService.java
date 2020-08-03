package com.eventure.services;

import com.eventure.model.MyEvent;
import com.eventure.model.User;

import java.util.ArrayList;

public interface FirebaseService {

    void write(String reference, Object value);

    void addUser(User user);

    User getUser(String login);

    ArrayList<User> getAllUsers();

    void changeUser(String login, User newUser);


    void addEvent(MyEvent event);

    MyEvent getEvent(String title);

    ArrayList<MyEvent> getAllEvents();

    void removeEvent(String title);
}
