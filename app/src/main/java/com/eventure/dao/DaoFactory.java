package com.eventure.dao;

public interface DaoFactory {

    UserDao getUserDao();
    EventDao getEventDao();
}
