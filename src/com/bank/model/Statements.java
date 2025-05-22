package com.bank.model;

import java.time.LocalDate;

public class Statements {
    private LocalDate date;
    private String description;
    private double amount;
    private double balance;
    private double balanceAfter;


    public Statements(LocalDate date, String description, double amount, double balance) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.balance = balance;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public String toCsv() {
        return date + "," + description + "," + amount + "," + balance;
    }

    public static Statements fromCsv(String line) {
        String[] parts = line.split(",", 4);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid statement line: " + line);
        }
        return new Statements(
                LocalDate.parse(parts[0]),
                parts[1],
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3])
        );
    }

    @Override
    public String toString() {
        return "[" + date + "] " + description + ": " + amount + " → Balance: " + balance;
    }
}


