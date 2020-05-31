package com.eventure.inmemory;

import com.eventure.dao.DaoFactory;
import com.eventure.dao.EventDao;
import com.eventure.dao.UserDao;

public class InMemoryDaoFactory implements DaoFactory {
    InMemoryDatabase database;
    UserDao userDao;
    EventDao eventDao;

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public EventDao getEventDao() {
        return eventDao;
    }

    public InMemoryDaoFactory(InMemoryDatabase database){
        this.database =database;
        userDao = new InMemoryUserDao(database);
        eventDao = new InMemoryEventDao(database);
    }
}
