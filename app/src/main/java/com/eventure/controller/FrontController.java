package com.eventure.controller;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.eventure.activities.LoginActivity;
import com.eventure.activities.MainActivity;
import com.eventure.dao.DaoFactory;
import com.eventure.inmemory.InMemoryDaoFactory;
import com.eventure.inmemory.InMemoryDatabase;
import com.eventure.inmemory.InMemoryTestData;
import com.eventure.model.User;
import com.eventure.services.ServiceFactory;
import com.eventure.services.UserService;
import com.eventure.services.UserServiceImp;

import java.util.function.UnaryOperator;

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
