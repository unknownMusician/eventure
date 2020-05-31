package com.eventure.controller;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.activities.EventActivity;
import com.eventure.services.ServiceFactory;

public class FrontController {

    protected ServiceFactory serviceFactory;

    public FrontController() {
        serviceFactory = ServiceFactory.getInstance();
    }

    public void goToActivity(AppCompatActivity self, Class<?> activity) {
        self.startActivity(new Intent(self, activity));
    }
}
