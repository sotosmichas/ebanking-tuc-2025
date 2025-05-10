package com.bank.managers;

import com.bank.model.BankAccount;
import com.bank.model.User;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private static AccountManager instance;
    private Map<String, BankAccount> accounts;

    private AccountManager() {
        accounts = new HashMap<>();
    }
    public static AccountManager getInstance(){
        if(instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public BankAccount getAccountByIban(String iban) {
        return accounts.get(iban);
    }

    public void addAccount(BankAccount account){
        accounts.put(account.getIban(),account);
    }
    public Map<String, BankAccount> getAllAccounts(){
        return accounts;
    }/*des mhpws kaneis lista giati borei na exei panw apo 1 account kathe anthrwopos
    public BankAccount getAccountsByVat(String vat){
        for(BankAccount b:accounts.values()) {
            if (b.getOwnerVat().equals(vat))
                return b;
        }
        return null;
    }*/

}
