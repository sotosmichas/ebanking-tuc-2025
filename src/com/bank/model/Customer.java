package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer extends  User{
    protected List<String> ibans;
    public Customer(String username, String password,String fullName,String vat) {
        super(username, password,fullName,vat);
        this.ibans = new ArrayList<>();
    }

    public List<String> getIbans() {
        return ibans;
    }

    public void addIban(String iban) {
        ibans.add(iban);
    }
}
