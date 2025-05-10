package com.bank.model;

import java.time.LocalDateTime;

public class Statements {
    private String type;
    private double amount;
    private String description;
    private LocalDateTime timestamp;

    public Statements(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    // για να τυπώνεται ωραία
    public String toString() {
        return "[" + timestamp + "] " + type + ": €" + amount + " (" + description + ")";
    }
}

