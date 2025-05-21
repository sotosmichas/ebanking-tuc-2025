package com.bank.model;

import com.bank.storage.Storable;

import java.time.LocalDate;

public abstract class BankAccount implements Storable {
    protected String iban;
    protected User owner;
    protected double balance;
    protected double interestRate;
    protected LocalDate dateCreated;

    public BankAccount(String iban, double balance, User owner, LocalDate dateCreated, double interestRate) {
        this.iban = iban;
        this.owner = owner;
        this.balance = balance;
        this.interestRate = interestRate;
        this.dateCreated = dateCreated;
    }

    public String getIban() {
        return iban;
    }

    public User getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
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
        return "IBAN: " + iban + ", Balance: " + balance + ", Owner VAT: " + owner.getUsername();
    }

    @Override
    public String marshal() {
        return getClass().getSimpleName() + "," + iban + "," + balance + "," + owner + "," + interestRate + "," + dateCreated;
    }




}
