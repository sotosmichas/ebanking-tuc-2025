package com.bank.model;

public class Admin extends User {
    public Admin(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    public String marshal() {
        return "Admin," + username + "," + password + "," + fullName;
    }

    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");
        if (parts.length >= 4) {
            this.username = parts[1].trim();
            this.password = parts[2].trim();
            this.fullName = parts[3].trim();
        }


    }
}

