package com.eventure.controller;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.services.ServiceFactory;

public class FrontController {

    protected static FrontController instance;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static FrontController getInstance() {
        if (instance == null) {
            return new FrontController();
        }
        return instance;
    }

    //////////////////// static line ////////////////////

    protected ServiceFactory serviceFactory;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected FrontController() {
        instance = this;

        serviceFactory = ServiceFactory.getInstance();
    }

    public void goToActivity(AppCompatActivity self, Class<?> activity) {
        self.startActivity(new Intent(self, activity));
    }
}
