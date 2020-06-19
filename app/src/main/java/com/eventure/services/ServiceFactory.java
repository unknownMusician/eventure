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
            instance = new ServiceFactory();
            return instance;
        }
        return instance;
    }

    //////////////////// static line ////////////////////

    private UserService userService;
    private EventService eventService;
    private MapsService mapsService;
    private FirebaseService firebaseService;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ServiceFactory(){
        InMemoryDatabase database = new InMemoryDatabase();
        InMemoryTestData.generateTo(database);

        this.userService = new UserServiceImp(database.getDaoFactory(), UnaryOperator.identity());
        this.eventService = new EventServiceImp(database.getDaoFactory());
        this.mapsService = new MapsServiceImp(database.getDaoFactory());
        this.firebaseService = new FirebaseServiceImp();
    }

    public UserService getUserService() {
        return userService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public MapsService getMapsService() {
        return mapsService;
    }

    public FirebaseService getFirebaseService() {
        return firebaseService;
    }
}
