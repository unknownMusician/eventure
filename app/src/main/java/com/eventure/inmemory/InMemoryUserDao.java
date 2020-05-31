package com.eventure.inmemory;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.eventure.dao.UserDao;
import com.eventure.model.User;

public class InMemoryUserDao extends InMemoryAbstractDao<User> implements UserDao {

    InMemoryUserDao(InMemoryDatabase database)  {
        super(database.users, User::getUserId, User::setUserId, database);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public User getByLogin(String login) {
        return database.users.values()
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

}
