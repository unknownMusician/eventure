package com.eventure.dao;

import com.eventure.model.User;

public interface UserDao extends AbstractDao<User> {

    User getByLogin(String login);

}
