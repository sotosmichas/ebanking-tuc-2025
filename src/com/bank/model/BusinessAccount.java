package com.bank.model;

import com.bank.managers.UserManager;

import java.time.LocalDate;

public class BusinessAccount extends BankAccount {
    private double maintenanceFee;



    public BusinessAccount(String iban, double balance, Company ownerVat, LocalDate dateCreated, double interestRate,double maintenanceFee) {
        super(iban, balance, ownerVat, dateCreated, interestRate);
        this.maintenanceFee=maintenanceFee;

    }

    public double getMaintenanceFee() {
        return maintenanceFee;
    }

    @Override
    public String marshal() {
        return getClass().getSimpleName() + "," +
                iban + "," +
                balance + "," +
                owner.getUsername() + "," +
                interestRate + "," +
                dateCreated;
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


        User u = UserManager.getInstance().getUser(ownerUsername);
        if (u instanceof Company c) {
            this.owner = c;
        } else {
            throw new IllegalArgumentException("Invalid company owner: " + ownerUsername);
        }
    }
}
