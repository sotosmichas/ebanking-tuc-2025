package com.bank.model;

import com.bank.storage.Storable;

public abstract class User implements Storable {
    protected String username;
    protected String fullName;
    protected String password;


    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }


    @Override
    public String toString() {
        return "username: " + username + ", password: " + password + ", fullName: " + fullName;
    }


    @Override
    public String marshal() {
        return getClass().getSimpleName() + "," + username + "," + password + "," + fullName;
    }

}
