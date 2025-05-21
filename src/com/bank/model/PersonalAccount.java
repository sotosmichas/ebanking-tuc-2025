package com.bank.model;

import com.bank.managers.UserManager;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PersonalAccount extends BankAccount {
    Set<Individual> coOwners;

    public PersonalAccount(String iban, double balance, Individual ownerVat, LocalDate dateCreated, double interestRate, Set<Individual> secondaryHolders) {
        super(iban, balance, ownerVat, dateCreated, interestRate);
        this.coOwners = new HashSet<>();
    }


    public Set<Individual> getSecondaryHolders() {
        return coOwners;
    }

    public void addSecondaryHolder(Individual individual) {
        coOwners.add(individual);
    }

    @Override
    public String marshal() {
        StringBuilder sb = new StringBuilder(super.marshal());
        for (Individual coOwner : coOwners) {
            sb.append(",").append(coOwner);
        }
        return sb.toString();
    }
    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");

        if (parts.length < 6) return;

        this.iban = parts[1].trim();
        this.balance = Double.parseDouble(parts[2].trim());
        String ownerUsername = parts[3].trim();
        this.interestRate = Double.parseDouble(parts[4].trim());
        this.dateCreated = LocalDate.parse(parts[5].trim());


        User user = UserManager.getInstance().getUser(ownerUsername);
        if (user instanceof Individual owner) {
            this.owner = owner;
        } else {
            throw new IllegalArgumentException("Invalid or missing Individual owner: " + ownerUsername);
        }

        this.coOwners = new HashSet<>();
        for (int i = 6; i < parts.length; i++) {
            if (parts[i].startsWith("coOwner:")) {
                String coUsername = parts[i].substring("coOwner:".length());
                User co = UserManager.getInstance().getUser(coUsername);
                if (co instanceof Individual ind) {
                    coOwners.add(ind);
                }
            }
        }
    }


}
