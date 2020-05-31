package com.eventure.controller;

import android.widget.EditText;

import com.eventure.model.User;

public class RegisterController extends FrontController {

    public boolean registerUser(EditText mLogin, EditText mPassword){
        String login = mLogin.getText().toString();
        String password = mPassword.getText().toString();

        if(login.replaceAll("", "").equals("") || password.replaceAll("", "").equals("") ||
        serviceFactory.getUserService().exist(login)) {
            return false;
        }

        serviceFactory.getUserService().createUser(login, password);
        return true;
    }
}
