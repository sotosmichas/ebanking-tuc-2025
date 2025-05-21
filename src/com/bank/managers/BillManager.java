package com.bank.managers;

import com.bank.model.*;
import com.bank.storage.BillCollection;
import com.bank.storage.StorageManager;
import com.bank.storage.StorageManagerImpl;
import com.bank.util.DateUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillManager {
    private static BillManager instance;
    private final List<Bill> issuedBills;
    private final List<Bill> paidBills;

    private BillManager() {
        paidBills = new ArrayList<>();
        issuedBills = new ArrayList<>();
    }

    public List<Bill> getIssuedBills() {
        return issuedBills;
    }

    public List<Bill> getPaidBills() {
        return paidBills;
    }

    public static BillManager getInstance() {
        if (instance == null) {
            instance = new BillManager();
        }
        return instance;
    }

    public void payBill(Bill bill) {
        if (bill == null) {
            throw new IllegalArgumentException("bill is null");
        }

        if (bill.isPaid()) {
            throw new IllegalStateException("bill already paid");
        }

        String vat = bill.getCustomerVat();
        User user = UserManager.getInstance().getUserByVat(vat);
        if (user == null) {
            throw new IllegalStateException("no user found with this VAT");
        }

        BankAccount account = null;
        Map<String, BankAccount> allAccounts = AccountManager.getInstance().getAllAccounts();
        for (BankAccount acc : allAccounts.values()) {
            if (acc.getOwner().equals(user)) {
                account = acc;
                break;
            }
        }

        if (account == null) {
            throw new IllegalStateException("no account found for this user");
        }

        if (account.getBalance() < bill.getAmount()) {
            throw new IllegalStateException("Insuficient funds for this payment");
        }

        account.withdraw(bill.getAmount());
        bill.setPaid(true);

        Statements s = new Statements(
                LocalDate.now(),
                "Bill pay",
                -bill.getAmount(),
                account.getBalance()
        );
        StatementManager.getInstance().addStatement(account.getIban(), s);

        issuedBills.remove(bill);
        paidBills.add(bill);
    }

    public void loadDailyBills(LocalDate date) {
        String filename = "data/bills/" + DateUtils.toFilename(date);
        Path path = Paths.get(filename);

        if (!Files.exists(path)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                Bill bill = new Bill();
                bill.unmarshal(line);
                issuedBills.add(bill);
            }
        } catch (IOException e) {
            throw new RuntimeException("Δεν μπόρεσα να φορτώσω το αρχείο: " + filename, e);
        }
    }

    public void saveAll() {
        StorageManager storage = new StorageManagerImpl();


        BillCollection issuedCollection = new BillCollection();
        for (Bill b : issuedBills) {
            issuedCollection.addBill(b);
        }


        BillCollection paidCollection = new BillCollection();
        for (Bill b : paidBills) {
            paidCollection.addBill(b);
        }

        try {
            storage.save(issuedCollection, "data/bills/issued.csv", false);
            storage.save(paidCollection, "data/bills/paid.csv", false);
        } catch (IOException e) {
            throw new RuntimeException("Αποτυχία αποθήκευσης bills", e);
        }
    }


}

