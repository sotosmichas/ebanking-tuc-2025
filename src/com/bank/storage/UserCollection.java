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

    public boolean addUser(User user) {
        return users.add(user);
    }

    @Override
    public String marshal() {
        StringBuilder sb = new StringBuilder();
        for (User u : users) {
            u.marshal();
            sb.append(u.marshal());
            sb.append("\n");


        }
        return sb.toString();
    }

    @Override
    public void unmarshal(String data) {
        String[] lines = data.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] fields = line.split(",");
            String type = null, username = null, password = null, fullName = null, vatOrSsn = null;

            for (String field : fields) {
                String[] parts = field.split(":", 2);
                if (parts.length < 2) continue;
                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "type" -> type = value;
                    case "userName" -> username = value;
                    case "password" -> password = value;
                    case "legalName" -> fullName = value;
                    case "vatNumber" -> vatOrSsn = value;
                }
            }

            User user = null;
            if ("Admin".equals(type)) {
                user = new Admin(username, password, fullName);
            } else if ("Individual".equals(type)) {
                user = new Individual(username, password, fullName, vatOrSsn);
            } else if ("Company".equals(type)) {
                user = new Company(username, password, fullName, vatOrSsn);
            }

            if (user != null) {
                addUser(user);
            }
        }
    }


}
