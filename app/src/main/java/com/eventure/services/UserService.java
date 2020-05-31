package com.eventure.services;

import com.eventure.model.User;

import java.util.ArrayList;

public interface UserService {

    User getByLogin(String login);
    boolean checkPassword(User user, String password);
    void createUser(String name, String password);
    boolean exist(String name);
    ArrayList<User> getAllUsers();
}
