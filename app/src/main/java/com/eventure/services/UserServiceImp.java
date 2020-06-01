package com.eventure.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.eventure.dao.DaoFactory;
import com.eventure.model.User;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

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
    public boolean checkPassword(User user, String password) {
        if (user.getPassword().equals(passwordHasher.apply(password))){
            UserHolder.set(user);
            return true;
        }
        return false;
    }

    @Override
    public void createUser(String login, String password) {
        User user = new User(0, login, password);
        daoFactory.getUserDao().insert(user, true);
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

        public static User get(){
            return user;
        }

        public static void set(User user){
            UserHolder.user = user;
        }
    }
}
