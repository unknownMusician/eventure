package com.eventure.services;

import android.os.Build;
import android.os.UserHandle;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.eventure.dao.DaoFactory;
import com.eventure.model.Place;
import com.eventure.model.User;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

import static android.content.ContentValues.TAG;

public class UserServiceImp implements UserService {

    DaoFactory daoFactory;
    UnaryOperator<String> passwordHasher;

    public UserServiceImp(DaoFactory daoFactory, UnaryOperator<String> passwordHasher) {
        this.daoFactory = daoFactory;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public User getByLogin(String login) {
        return daoFactory.getUserDao().getByLogin(login);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean checkPassword(String login, String password) {
        User user = ServiceFactory.get().getUserService().getByLogin(login);
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(passwordHasher.apply(password))){
            UserHolder.setUser(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean createUser(String login, String password) {
        if(login.replaceAll(" ", "").equals("") || password.replaceAll(" ", "").equals("") ||
                exist(login)) {
            return false;
        }
        User user = new User(0, login, password);
        daoFactory.getUserDao().insert(user, true);
        return true;
    }

    @Override
    public boolean exist(String name) {
        return getByLogin(name) != null;
    }

    public ArrayList<User> getAllUsers(){
        return new ArrayList<>(daoFactory.getUserDao().findAll());
    }

    public static class UserHolder {
        private static User user;
        private static Place location;

        public static User getUser(){
            return user;
        }

        public static void setUser(User user){
            UserHolder.user = user;
        }

        public static Place getLocation() {
            if(location == null){
                return new Place(50, 30);
            }
            return location;
        }

        public static void setLocation(Place location) {
            UserHolder.location = location;
        }
    }
    public boolean changePassword(String oldPassword,String newPassword){
        Log.d(TAG, "changePassword: " + UserHolder.getUser().getPassword());
        if(UserHolder.getUser().getPassword().equals(oldPassword)){
            UserHolder.getUser().setPassword(newPassword);
            return true;
        }
       else {
           return false;
        }
    }
}
