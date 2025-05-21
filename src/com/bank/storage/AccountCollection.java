package com.bank.storage;

import com.bank.managers.UserManager;
import com.bank.model.*;

import java.time.LocalDate;
import java.util.*;

public class AccountCollection implements Storable {
    private List<BankAccount> accounts;

    public AccountCollection() {
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
            System.out.println("📥 Reading line: " + line);

            if (line.trim().isEmpty()) continue;

            String[] fields = line.split(",");
            Map<String, String> fieldMap = new HashMap<>();

            for (String field : fields) {
                String[] parts = field.split(":", 2);
                if (parts.length == 2) {
                    fieldMap.put(parts[0].trim(), parts[1].trim());
                }
            }
            System.out.println("🧾 Parsed fields: " + fieldMap);
            String type = fieldMap.get("type");
            String iban = fieldMap.get("iban");
            double balance = Double.parseDouble(fieldMap.get("balance"));
            double interest = Double.parseDouble(fieldMap.get("rate")); // renamed from 'interest'
            LocalDate dateCreated = LocalDate.parse(fieldMap.get("dateCreated"));

            BankAccount account = null;

            if ("PersonalAccount".equalsIgnoreCase(type)) {
                String primaryVat = fieldMap.get("primaryOwner");
                User u = UserManager.getInstance().getUserByVat(primaryVat);
                if (!(u instanceof Individual owner)) continue;

                Set<Individual> coOwners = new HashSet<>();
                for (String key : fieldMap.keySet()) {
                    if (key.startsWith("coOwner")) {
                        String coVat = fieldMap.get(key);
                        User co = UserManager.getInstance().getUserByVat(coVat);
                        if (co instanceof Individual indCo) {
                            coOwners.add(indCo);
                        }
                    }
                }

                account = new PersonalAccount(iban, balance, owner, dateCreated, interest, coOwners);
            } else if ("BusinessAccount".equalsIgnoreCase(type)) {
                String companyVat = fieldMap.get("primaryOwner");
                User u = UserManager.getInstance().getUserByVat(companyVat);
                if (!(u instanceof Company owner)) continue;

                account = new BusinessAccount(iban, balance, owner, dateCreated, interest);



        }


            if (account != null) {
                addAccount(account);
                System.out.println("✅ Added account: " + account.getIban());
            }
        }
    }

}


