package com.bank.managers;

import com.bank.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static UserManager instance;
    private final Map<String, User> users;

    private UserManager() {
        users = new HashMap<>();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User login(String username, String password) {
        User u = users.get(username);
        if (u != null && u.getPassword().equals(password))
            return u;
        else
            return null;
    }



    public Map<String, User> getAllUsers() {
        return users;
    }

}



