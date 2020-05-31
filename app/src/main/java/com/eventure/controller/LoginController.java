package com.eventure.controller;

import android.widget.EditText;

import com.eventure.model.User;

public class LoginController extends FrontController {

    public boolean checkUserLoginAttributes(EditText mLogin, EditText mPassword) {
        String login = mLogin.getText().toString();
        String password = mPassword.getText().toString();
        User user = serviceFactory.getUserService().getByLogin(login);
        if (user == null) {
            return false;
        }
        return serviceFactory.getUserService().checkPassword(user, password);
    }
}
