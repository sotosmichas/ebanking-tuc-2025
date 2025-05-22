package com.bank.managers;

import com.bank.model.BankAccount;
import com.bank.model.BusinessAccount;
import com.bank.model.Customer;
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
    public BusinessAccount getBusinessAccountByVat(String vat) {
        for (BankAccount acc : accounts.values()) {
            if (acc instanceof BusinessAccount) {
                User owner = acc.getOwner();
                if (owner instanceof Customer customer && customer.getVat().equals(vat)) {
                    return (BusinessAccount) acc;
                }
            }
        }
        throw new IllegalArgumentException("No business account found for VAT: " + vat);
    }

    public void addAccount(BankAccount account){
        accounts.put(account.getIban(),account);
    }
    public Map<String, BankAccount> getAllAccounts(){
        return accounts;
    }

}
