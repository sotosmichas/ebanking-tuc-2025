package com.bank.model;

public abstract class User {
protected String username;
protected String fullName;
protected String password;
protected String vat;

    public User(String username, String password,String fullName,String vat) {
        this.username = username;
        this.password = password;
        this.vat = vat;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getVat() {
        return vat;
    }

    public String getFullName() {
        return fullName;
    }
}
