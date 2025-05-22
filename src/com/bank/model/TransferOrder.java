package com.bank.model;

import java.time.LocalDate;

public class TransferOrder extends Order {
    private double amount;
    private String chargeAccount;
    private String creditAccount;
    private int frequencyInMonths;
    private int dayOfMonth;

    public TransferOrder(String orderId, String title, String description, String customerVat,
                         LocalDate startDate, LocalDate endDate, double fee,
                         double amount, String chargeAccount, String creditAccount,
                         int frequencyInMonths, int dayOfMonth) {
        super(orderId, title, description, customerVat, startDate, endDate, fee);
        this.amount = amount;
        this.chargeAccount = chargeAccount;
        this.creditAccount = creditAccount;
        this.frequencyInMonths = frequencyInMonths;
        this.dayOfMonth = dayOfMonth;
    }

    public double getAmount() {
        return amount;
    }

    public String getFromIban() {
        return chargeAccount;
    }

    public String getToIban() {
        return creditAccount;
    }

    public int getFrequencyInMonths() {
        return frequencyInMonths;
    }

    public int getExecutionDayOfMonth() {
        return dayOfMonth;
    }

    @Override
    public void execute(LocalDate date) throws Exception {
        // Placeholder: logic for actual transfer can be implemented here if needed.
        if (!isActiveOn(date)) return;
        if (date.getDayOfMonth() != dayOfMonth) return;

        // Frequency-based logic (e.g. monthly intervals) can be implemented here if needed.
        // For now, this is a stub method to fulfill abstract method requirement.
    }
}
