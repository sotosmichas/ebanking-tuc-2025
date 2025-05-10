package com.bank.model;

import java.util.HashSet;
import java.util.Set;

public class PersonalAccount extends BankAccount{
    Set<String> secondaryHolders;
    public PersonalAccount(String iban, String ownerVat, double interestRate) {
        super(iban, ownerVat, interestRate);
        this.secondaryHolders = new HashSet<>();
    }

    public Set<String> getSecondaryHolders() {
        return secondaryHolders;
    }
    public void addSecondaryHolder(String vat){
        secondaryHolders.add(vat);
    }
}
