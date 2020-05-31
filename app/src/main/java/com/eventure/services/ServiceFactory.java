package com.eventure.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.eventure.inmemory.InMemoryDatabase;
import com.eventure.inmemory.InMemoryTestData;

import java.util.function.UnaryOperator;

public class ServiceFactory {

    public static ServiceFactory instance;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ServiceFactory get(){
        if(instance == null){
            return new ServiceFactory();
        }
        return instance;
    }

    //////////////////// static line ////////////////////

    private UserService userService;
    private EventService eventService;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ServiceFactory(){
        InMemoryDatabase database = new InMemoryDatabase();
        InMemoryTestData.generateTo(database);

        this.userService = new UserServiceImp(database.getDaoFactory(), UnaryOperator.identity());
        this.eventService = new EventServiceImp(database.getDaoFactory());
    }

    public UserService getUserService() {
        return userService;
    }

    public EventService getEventService() {
        return eventService;
    }
}
