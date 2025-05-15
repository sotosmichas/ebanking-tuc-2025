package com.bank.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PersonalAccount extends BankAccount {
    Set<String> secondaryHolders;

    public PersonalAccount(String iban, double balance, String ownerVat, LocalDate dateCreated, double interestRate, Set<String> secondaryHolders) {
        super(iban, balance, ownerVat, dateCreated, interestRate);
        this.secondaryHolders = new HashSet<>();
    }


    public Set<String> getSecondaryHolders() {
        return secondaryHolders;
    }

    public void addSecondaryHolder(String vat) {
        secondaryHolders.add(vat);
    }

    @Override
    public String marshal() {
        StringBuilder sb = new StringBuilder(super.marshal());
        for (String coOwner : secondaryHolders) {
            sb.append(",").append(coOwner);
        }
        return sb.toString();
    }

}
