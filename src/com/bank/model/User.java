package com.bank.model;

import com.bank.storage.Storable;

public abstract class User implements Storable {
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
    public String toString() {
        return "username: " + ", password: " + password + ", fullName: " + fullName + ", vat:" + vat;
    }
    @Override
    public String marshal() {
        return getClass().getSimpleName() + "," + username + "," + password + "," + fullName + "," + vat;
    }

}
