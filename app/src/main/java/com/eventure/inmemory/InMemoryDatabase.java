package com.eventure.inmemory;

import com.eventure.dao.DaoFactory;
import com.eventure.model.MyEvent;
import com.eventure.model.User;

import java.util.Map;
import java.util.TreeMap;

public class InMemoryDatabase {

    public Map<Integer,User> users;
    Map<Integer, MyEvent> events;

    public InMemoryDatabase(){
        users = new TreeMap<>();
        events = new TreeMap<>();
    }
    public DaoFactory getDaoFactory(){
        return new InMemoryDaoFactory(this);
    }

}
