package com.bank.model;

import java.time.LocalDate;

public abstract class Order {
    protected String orderId;
    protected String title;
    protected String description;
    protected String customerVat;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected boolean active = true;
    protected double fee;

    public Order(String orderId, String title, String description, String customerVat,
                 LocalDate startDate, LocalDate endDate, double fee) {
        this.orderId = orderId;
        this.title = title;
        this.description = description;
        this.customerVat = customerVat;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fee = fee;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    public double getFee() {
        return fee;
    }

    public boolean isActiveOn(LocalDate date) {
        return active && (date.isEqual(startDate) || date.isAfter(startDate)) && date.isBefore(endDate.plusDays(1));
    }

    public void deactivate() {
        this.active = false;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerVat() {
        return customerVat;
    }

    public abstract void execute(LocalDate date) throws Exception;
}

