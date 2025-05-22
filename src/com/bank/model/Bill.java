package com.bank.model;

import com.bank.storage.Storable;
import com.bank.util.DateUtils;

import java.time.LocalDate;

public class Bill implements Storable {
    private String RfCode;
    private String billNumber;
    private String issuerVat;
    private String customerVat;
    private double amount;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private boolean paid;
    private Company issuer;

    public Bill() {

    }

    public Bill(String billNumber, String rfCode, Company issuer, String customerVat, double amount, String description, LocalDate dueDate) {
        this.RfCode = RfCode;
        this.billNumber = billNumber;
        this.issuerVat = issuerVat;
        this.customerVat = customerVat;
        this.amount = amount;
        this.issueDate = issueDate;
        this.dueDate = dueDate;

    }
    public Company getIssuer() {
        return issuer;
    }

    public String getRfCode() {
        return RfCode;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public String getIssuerVat() {
        return issuerVat;
    }

    public String getCustomerVat() {
        return customerVat;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String marshal() {
        return "type:Bill" +
                ",paymentCode:" + RfCode +
                ",billNumber:" + billNumber +
                ",issuer:" + issuerVat +
                ",customer:" + customerVat +
                ",amount:" + amount +
                ",issueDate:" + DateUtils.toFilename(issueDate).replace(".csv", "") +
                ",dueDate:" + DateUtils.toFilename(dueDate).replace(".csv", "");

    }
    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");
        for (String part : parts) {
            String[] keyVal = part.split(":", 2);
            if (keyVal.length < 2) continue;

            String key = keyVal[0].trim();
            String val = keyVal[1].trim();

            switch (key) {
                case "paymentCode" -> this.RfCode = val;
                case "billNumber" -> this.billNumber = val;
                case "issuer" -> this.issuerVat = val;
                case "customer" -> this.customerVat = val;
                case "amount" -> this.amount = Double.parseDouble(val);
                case "issueDate" -> this.issueDate = DateUtils.fromFilename(val + ".csv");
                case "dueDate" -> this.dueDate = DateUtils.fromFilename(val + ".csv");

            }
        }
    }
    @Override
    public String toString() {
        return "[BILL] #" + billNumber + " → " + amount + "€ | Issuer: " + issuerVat +
                ", Customer: " + customerVat + ", Due: " + dueDate;
    }
}
