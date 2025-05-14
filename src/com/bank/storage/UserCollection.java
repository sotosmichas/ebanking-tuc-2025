package com.bank.storage;

import com.bank.model.Admin;
import com.bank.model.Company;
import com.bank.model.Individual;
import com.bank.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserCollection implements Storable {
    private List<User> users;
    public UserCollection() {
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }
    public boolean addUser(User user){
        return users.add(user);
    }

    @Override
    public String marshal() {
        StringBuilder sb = new StringBuilder();
        for(User u:users){
            u.marshal();
            sb.append(u.marshal());
            sb.append("\n");



        }
        return sb.toString();
    }
    @Override
    public void unmarshall(String line) {
        String[] parts = line.split(",");
        if (parts.length < 4) return;

        String type = parts[0];
        String username = parts[1];
        String password = parts[2];
        String fullName = parts[3];

        User user = null;

        switch (type) {
            case "Individual" -> {
                if (parts.length >= 5)
                    user = new Individual(username, password, fullName, parts[4]);
            }
            case "Company" -> {
                if (parts.length >= 5)
                    user = new Company(username, password, fullName, parts[4]);
            }
            case "Admin" -> user = new Admin(username, password, fullName);
        }

        if (user != null) {
            addUser(user);
        }
    }
}
