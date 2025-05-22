package com.bank.managers;

import com.bank.model.*;
import com.bank.storage.BillCollection;
import com.bank.storage.StorageManager;
import com.bank.storage.StorageManagerImpl;
import com.bank.util.DateUtils;
import com.bank.util.IdGenerator;

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

    public static BillManager getInstance() {
        if (instance == null) {
            instance = new BillManager();
        }
        return instance;
    }

    public List<Bill> getIssuedBills() {
        return issuedBills;
    }

    public List<Bill> getPaidBills() {
        return paidBills;
    }

    public List<Bill> getAllBills() {
        List<Bill> all = new ArrayList<>();
        all.addAll(issuedBills);
        all.addAll(paidBills);
        return all;
    }

    public Bill getBillByNumber(String billNumber) {
        for (Bill bill : issuedBills) {
            if (bill.getBillNumber().equals(billNumber)) {
                return bill;
            }
        }
        return null;
    }

    public List<Bill> getIssuedBillsByCompany(String companyVat) {
        List<Bill> result = new ArrayList<>();
        for (Bill bill : issuedBills) {
            if (bill.getIssuer().getVat().equals(companyVat)) {
                result.add(bill);
            }
        }
        return result;
    }

    public List<Bill> getPaidBillsByCompany(String companyVat) {
        List<Bill> result = new ArrayList<>();
        for (Bill bill : paidBills) {
            if (bill.getIssuer().getVat().equals(companyVat)) {
                result.add(bill);
            }
        }
        return result;
    }
    public Bill createBill(Company issuer, String customerVat, double amount, String description, LocalDate dueDate) {
        if (issuer == null) throw new IllegalArgumentException("Issuer company cannot be null.");
        if (customerVat == null || customerVat.isEmpty()) throw new IllegalArgumentException("Customer VAT is required.");
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
        if (dueDate == null || dueDate.isBefore(LocalDate.now())) throw new IllegalArgumentException("Due date must be in the future.");

        String billNumber = IdGenerator.generateTransactionId();
        String rfCode = IdGenerator.generateRfCode();

        Bill bill = new Bill(billNumber, rfCode, issuer, customerVat, amount, description, dueDate);

        issuedBills.add(bill);
        return bill;
    }



    private String getCompanyIban(Bill bill) {
        Company company = bill.getIssuer();

        for (BankAccount acc : AccountManager.getInstance().getAllAccounts().values()) {
            if (acc.getOwner().equals(company)) {
                return acc.getIban();
            }
        }

        throw new IllegalStateException("No bank account found for company " + company.getUsername());
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

        String companyIban = getCompanyIban(bill);

        try {
            TransactionManager.getInstance().submitPayment(
                    account.getIban(),
                    companyIban,
                    bill.getAmount(),
                    "BANK",
                    "Πληρωμή λογαριασμού (RF: " + bill.getRfCode() + ")",
                    "Είσπραξη από πελάτη για λογαριασμό"
            );

            bill.setPaid(true);
            issuedBills.remove(bill);
            paidBills.add(bill);

        } catch (Exception e) {
            throw new RuntimeException("Payment failed: " + e.getMessage(), e);
        }
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

