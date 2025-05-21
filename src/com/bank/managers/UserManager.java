package com.bank.managers;

import com.bank.model.Customer;
import com.bank.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static UserManager instance;
    private  Map<String, User> users;

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

    public User getUser(String username) {
        return users.get(username);
    }
    public User getUserByVat(String vat) {
        for (User u : users.values()) {
            if (u instanceof Customer c && c.getVat().equals(vat)) {
                return c;
            }
        }
        return null;
    }



}



