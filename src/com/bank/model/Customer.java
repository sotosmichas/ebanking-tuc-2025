package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer extends User {
    protected List<BankAccount> ibans;
    private String vat;

    public Customer(String username, String password, String fullName, String vat) {
        super(username, password, fullName);
        this.ibans = new ArrayList<>();
        this.vat = vat;
    }

    public List<BankAccount> getIbans() {
        return ibans;
    }

    /*public void add(String iban) {
        ibans.add(iban);
    }*/

    public String marshal() {
        return getClass().getSimpleName() + "," + username + "," + password + "," + fullName + "," + vat;
    }

    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");
        if (parts.length >= 5) {
            this.username = parts[1].trim();
            this.password = parts[2].trim();
            this.fullName = parts[3].trim();
            this.vat = parts[4].trim();
        }
    }

    public String getVat() {
        return vat;
    }
}

