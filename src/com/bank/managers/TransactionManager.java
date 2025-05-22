package com.bank.managers;

import com.bank.model.BankAccount;
import com.bank.model.Statements;

import java.time.LocalDate;

public class TransactionManager {
    private static TransactionManager instance;

    public static TransactionManager getInstance() {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    public void deposit(String iban, double amount, String description) {
        BankAccount account = AccountManager.getInstance().getAccountByIban(iban);
        if (account == null) throw new IllegalArgumentException("Account not found");
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive");

        account.deposit(amount);

        Statements statement = new Statements(LocalDate.now(), description, amount, account.getBalance());
        StatementManager.getInstance().addStatement(iban, statement);
    }

    public void withdraw(String iban, double amount, String description) {
        BankAccount account = AccountManager.getInstance().getAccountByIban(iban);
        if (account == null) throw new IllegalArgumentException("Account not found");
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive");
        if (account.getBalance() < amount) throw new IllegalStateException("Insufficient funds");

        account.withdraw(amount);

        Statements statement = new Statements(LocalDate.now(), description, -amount, account.getBalance());
        StatementManager.getInstance().addStatement(iban, statement);
    }

    public void transfer(String fromIban, String toIban, double amount, String description) {
        BankAccount fromAcc = AccountManager.getInstance().getAccountByIban(fromIban);
        BankAccount toAcc = AccountManager.getInstance().getAccountByIban(toIban);

        if (fromAcc == null || toAcc == null)
            throw new IllegalArgumentException("Invalid IBAN(s)");
        if (amount <= 0) throw new IllegalArgumentException("Transfer amount must be positive");
        if (fromAcc.getBalance() < amount) throw new IllegalStateException("Insufficient funds");

        fromAcc.withdraw(amount);
        toAcc.deposit(amount);

        LocalDate now = LocalDate.now();

        // Create statements for both sides
        Statements fromStatement = new Statements(now, "Transfer to " + toIban + ": " + description, -amount, fromAcc.getBalance());
        Statements toStatement = new Statements(now, "Transfer from " + fromIban + ": " + description, amount, toAcc.getBalance());

        StatementManager.getInstance().addStatement(fromIban, fromStatement);
        StatementManager.getInstance().addStatement(toIban, toStatement);
    }

    public void submitPayment(String fromIban,String toIban,double amount,String transactor,String senderDescription,String receiverDescription) {

        BankAccount fromAcc = AccountManager.getInstance().getAccountByIban(fromIban);
        BankAccount toAcc = AccountManager.getInstance().getAccountByIban(toIban);

        if (fromAcc == null || toAcc == null) {
            throw new IllegalArgumentException("Invalid IBAN(s)");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        if (fromAcc.getBalance() < amount) {
            throw new IllegalStateException("Insufficient funds");
        }

        fromAcc.withdraw(amount);
        toAcc.deposit(amount);

        LocalDate now = LocalDate.now();

        Statements fromStatement = new Statements(
                now,
                senderDescription + " [By: " + transactor + "]",
                -amount,
                fromAcc.getBalance()
        );

        Statements toStatement = new Statements(
                now,
                receiverDescription + " [By: " + transactor + "]",
                amount,
                toAcc.getBalance()
        );

        StatementManager.getInstance().addStatement(fromIban, fromStatement);
        StatementManager.getInstance().addStatement(toIban, toStatement);
    }
}

