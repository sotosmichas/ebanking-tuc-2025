package com.bank.model;

import java.time.LocalDate;

public class PaymentOrder extends Order {
    private String paymentCode;
    private double maxAmount;
    private String chargeAccount;

    public PaymentOrder(String orderId, String title, String description, String customerVat,
                        LocalDate startDate, LocalDate endDate, double fee,
                        String paymentCode, double maxAmount, String chargeAccount) {
        super(orderId, title, description, customerVat, startDate, endDate, fee);
        this.paymentCode = paymentCode;
        this.maxAmount = maxAmount;
        this.chargeAccount = chargeAccount;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public String getChargeAccount() {
        return chargeAccount;
    }

    @Override
    public void execute(LocalDate date) throws Exception {}
}
