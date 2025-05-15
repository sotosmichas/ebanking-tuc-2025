package com.bank.model;

import com.bank.storage.Storable;

import java.time.LocalDate;

public abstract class BankAccount implements Storable {
    protected String iban;
    protected String ownerVat;
    protected double balance;
    protected double interestRate;
    protected LocalDate dateCreated;

    public BankAccount(String iban, double balance, String ownerVat, LocalDate dateCreated, double interestRate) {
        this.iban = iban;
        this.ownerVat = ownerVat;
        this.balance = 0;
        this.interestRate = interestRate;
        this.dateCreated = dateCreated;
    }

    public String getIban() {
        return iban;
    }

    public String getOwnerVat() {
        return ownerVat;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public String toString() {
        return "IBAN: " + iban + ", Balance: " + balance + ", Owner VAT: " + ownerVat;
    }

    @Override
    public String marshal() {
        return getClass().getSimpleName() + "," + iban + "," + balance + "," + ownerVat + "," + interestRate + "," + dateCreated;
    }

    @Override
    public void unmarshal(String line) {
        String[] parts = line.split(",");
        if (parts.length >= 6) {
            this.iban = parts[1];
            this.balance = Double.parseDouble(parts[2]);
            this.ownerVat = parts[3];
            this.interestRate = Double.parseDouble(parts[4]);
            this.dateCreated = LocalDate.parse(parts[5]);
        }

    }
}
