package com.bank.model;

import java.time.LocalDate;

public class BusinessAccount extends BankAccount {


    public BusinessAccount(String iban, double balance, String ownerVat, LocalDate dateCreated, double interestRate) {
        super(iban, balance, ownerVat, dateCreated, interestRate);

    }
}
