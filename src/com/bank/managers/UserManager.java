package com.bank.managers;

import com.bank.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static UserManager instance;
    private Map<String, User> users;

    private UserManager(){
        users = new HashMap<>();
    }

    public static UserManager getInstance(){
        if(instance==null){
            instance = new UserManager();
        }
        return instance;
    }
    public void addUser(User user){
        users.put(user.getUsername(),user);
    }

    private User login(String username,String password){
        User u =users.get(username);
        if(u != null && u.getPassword().equals(password))
            return u;
        else
            return null;
    }
    public User getUserByVat(String vat) {
        for (User u : users.values()) {
            if (u.getVat().equals(vat))
                return u;
        }
        return null;
    }
    public Map<String, User> getAllUsers(){
        return users;
    }

}
