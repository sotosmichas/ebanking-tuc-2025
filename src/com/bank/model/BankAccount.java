package com.bank.model;

public abstract class  BankAccount {
protected String iban;
protected String ownerVat;
protected double balance;
protected double  interestRate;

    public BankAccount(String iban, String ownerVat,double interestRate) {
        this.iban = iban;
        this.ownerVat = ownerVat;
        this.balance = 0;
        this.interestRate = interestRate;
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
    public void deposit(double amount){
        balance+= amount;
    }
    public boolean withdraw(double amount){
        if (amount <= balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public String toString() {
        return "IBAN: " + iban + ", Balance: " + balance + ", Owner VAT: " + ownerVat;
    }

}
