package com.bank.model;

public class Admin extends User{
    public Admin(String username, String password,String fullName) {
        super(username, password,fullName);
    }
    @Override
    public String marshal() {
        return "Admin," + username + "," + password + "," + fullName;
    }


}
