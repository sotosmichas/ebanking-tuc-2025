package com.bank.managers;

import com.bank.model.BankAccount;

public class TransactionManager {
    private static TransactionManager instance;

    public static TransactionManager getInstance(){
        if(instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    public void deposit(String iban, double amount, String description){
        BankAccount account = AccountManager.getInstance().getAccountByIban(iban);
        if(account!=null){
            account.deposit(amount);
        }
    }
    public void withdraw(String iban, double amount, String description){
        BankAccount account = AccountManager.getInstance().getAccountByIban(iban);
        if(account!=null)
            account.withdraw(amount);
    }
    public void transfer(String fromIban, String toIban, double amount, String description){
        BankAccount fromAcc = AccountManager.getInstance().getAccountByIban(fromIban);
        BankAccount toAcc = AccountManager.getInstance().getAccountByIban(toIban);
        if(fromAcc!=null && toAcc!=null && fromAcc.getBalance()>amount){
            fromAcc.withdraw(amount);
            toAcc.deposit(amount);
        }
    }
}
