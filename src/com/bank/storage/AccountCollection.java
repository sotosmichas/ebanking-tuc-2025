package com.bank.storage;

import com.bank.model.BankAccount;
import com.bank.model.BusinessAccount;
import com.bank.model.PersonalAccount;
import com.bank.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountCollection implements Storable {
    private List<BankAccount> accounts;

    public AccountCollection(List<BankAccount> accounts) {
        this.accounts = new ArrayList<>();
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public boolean addAccount(BankAccount account) {
        return accounts.add(account);
    }

    @Override
    public String marshal() {
        StringBuilder sb = new StringBuilder();
        for (BankAccount acc : accounts) {
            acc.marshal();
            sb.append(acc.marshal());
            sb.append("\n");


        }
        return sb.toString();
    }

    @Override
    public void unmarshal(String data) {
        String[] lines = data.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(",");
            if (parts.length < 6) continue;

            String iban = parts[0].trim();
            double balance = Double.parseDouble(parts[1].trim());
            double interest = Double.parseDouble(parts[2].trim());
            String type = parts[3].trim();
            String ownerVat = parts[4].trim();
            LocalDate dateCreated = LocalDate.parse(parts[5].trim());

            BankAccount account = null;

            switch (type) {
                case "Personal" -> {
                    Set<String> coOwners = new HashSet<>();
                    if (parts.length > 6) {
                        for (int i = 6; i < parts.length; i++) {
                            coOwners.add(parts[i].trim());
                        }
                    }
                    account = new PersonalAccount(iban, balance, ownerVat, dateCreated, interest, coOwners);
                }
                case "Business" -> {
                    account = new BusinessAccount(iban, balance, ownerVat, dateCreated, interest);
                }
            }

            if (account != null) {
                addAccount(account);
            }
        }
    }
    }

