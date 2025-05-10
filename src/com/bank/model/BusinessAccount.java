package com.bank.model;

public class BusinessAccount extends BankAccount{
    private final double maintenaceFee;
    public BusinessAccount(String iban, String ownerVat, double interestRate,double maintanceFee) {
        super(iban, ownerVat, interestRate);
        this.maintenaceFee = maintanceFee;
    }

    public double getMaintenaceFee() {
        return maintenaceFee;
    }
}
