package com.eventure.controller;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.activities.*;
import com.eventure.services.ServiceFactory;

public class ControllerFactory {

    protected static ControllerFactory instance;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ControllerFactory get() {
        if (instance == null) {
            return new ControllerFactory();
        }
        return instance;
    }

    //////////////////// static line ////////////////////

    protected ServiceFactory serviceFactory;

    private FrontController frontController;
    private MenuController menuController;
    private LoginController loginController;
    private EventListController eventListController;
    private EventController eventController;
    private CalendarController calendarController;
    private RegisterController registerController;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected ControllerFactory() {
        instance = this;

        serviceFactory = ServiceFactory.get();

        frontController = new FrontController();
        menuController = new MenuController();
        loginController = new LoginController();
        eventListController = new EventListController();
        eventController = new EventController();
        calendarController = new CalendarController();
        registerController = new RegisterController();
    }

    public FrontController getFrontController() {
        return frontController;
    }

    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public EventListController getEventListController() {
        return eventListController;
    }

    public EventController getEventController() {
        return eventController;
    }

    public CalendarController getCalendarController() {
        return calendarController;
    }

    public RegisterController getRegisterController() {
        return registerController;
    }

    ////////// SOME FUNCS FOR NIKITA //////////

    public EventController getMy(EventActivity self){
        return getEventController();
    }public EventListController getMy(EventListActivity self){
        return getEventListController();
    }public LoginController getMy(LoginActivity self){
        return getLoginController();
    }public MenuController getMy(MenuActivity self){
        return getMenuController();
    }public FrontController getMy(MainActivity self){
        return getFrontController();
    }public RegisterController getMy(RegisterActivity self){
        return getRegisterController();
    }/*public CalendarController getMy(CalendarActivity self){
        return getCalendarController();
    }*/
}
