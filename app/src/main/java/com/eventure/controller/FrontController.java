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
import com.eventure.services.UserService;
import com.eventure.services.UserServiceImp;

import java.util.function.UnaryOperator;

public class FrontController {

    private static FrontController instance;

    private UserService userService;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FrontController() {
        if (instance == null) {
            instance = this;

            InMemoryDatabase database = new InMemoryDatabase();
            InMemoryTestData.generateTo(database);
            DaoFactory daoFactory = new InMemoryDaoFactory(database);

            userService = new UserServiceImp(daoFactory, UnaryOperator.identity());
        }
    }

    public void print(String str) {
        System.out.println(str);
    }

    public static FrontController getInstance() {
        return instance;
    }

    public boolean checkUserLoginAttributes(EditText mLogin, EditText mPassword){
        String login = mLogin.getText().toString();
        String password = mPassword.getText().toString();

        User user = userService.getByLogin(login);

        if(user == null){
            return false;
        }

        return userService.checkPassword(userService.getByLogin(login), password);
    }
}
