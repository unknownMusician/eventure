package com.eventure.services;

import com.eventure.model.User;

public interface UserService {
    User getByLogin(String login);
    boolean checkPassword(User user, String password);
}
