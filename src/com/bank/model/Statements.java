package com.bank.model;

import java.time.LocalDateTime;

public class Statements {
    private final String type;
    private final double amount;
    private final String description;
    private final LocalDateTime timestamp;

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

