package com.eventure.controller;

import android.os.Build;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.eventure.model.User;
import com.eventure.services.ServiceFactory;

public class LoginController extends FrontController {

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected LoginController(){
        super();
    }

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
