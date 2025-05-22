package com.bank.managers;

import com.bank.model.*;
import com.bank.storage.StorageManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class StandingOrderManager {
    private static StandingOrderManager instance;

    private final List<Order> activeOrders;
    private final Map<String, Integer> failureCounts;


    private StandingOrderManager() {
        this.activeOrders = new ArrayList<>();
        this.failureCounts = new HashMap<>();

    }

    public static StandingOrderManager getInstance() {
        if (instance == null)
            instance = new StandingOrderManager();
        return instance;
    }

    public List<Order> getActiveOrders() {
        List<Order> active = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Order o : activeOrders) {
            if (o.isActiveOn(today)) active.add(o);
        }
        return active;
    }

    public List<Order> getOrdersByCustomer(String vat) {
        List<Order> result = new ArrayList<>();
        for (Order o : activeOrders) {
            if (o.getCustomerVat().equals(vat)) result.add(o);
        }
        return result;
    }

    public void cancelOrder(String orderId) {
        for (Order order : activeOrders) {
            if (order.getOrderId().equals(orderId)) {
                order.deactivate();
                return;
            }
        }
        throw new IllegalArgumentException("Order not found: " + orderId);
    }


    public void loadOrders(String csvPath) throws IOException {
        activeOrders.clear();
        List<String> lines = Files.readAllLines(Path.of(csvPath));

        for (String line : lines) {
            Map<String, String> data = parseCsvLine(line);

            String type = data.get("type");
            String orderId = data.get("orderId");
            String title = data.get("title");
            String description = data.getOrDefault("description", "");
            String customerVat = data.get("customer");
            LocalDate startDate = LocalDate.parse(data.get("startDate"));
            LocalDate endDate = LocalDate.parse(data.get("endDate"));
            double fee = Double.parseDouble(data.get("fee"));

            if (type.equals("TransferOrder")) {
                double amount = Double.parseDouble(data.get("amount"));
                String from = data.get("chargeAccount");
                String to = data.get("creditAccount");
                int freq = Integer.parseInt(data.get("frequencyInMonths"));
                int day = Integer.parseInt(data.get("dayOfMonth"));

                activeOrders.add(new TransferOrder(orderId, title, description, customerVat,
                        startDate, endDate, fee, amount, from, to, freq, day));

            } else if (type.equals("PaymentOrder")) {
                String paymentCode = data.get("paymentCode");
                double maxAmount = Double.parseDouble(data.get("maxAmount"));
                String chargeAccount = data.get("chargeAccount");

                activeOrders.add(new PaymentOrder(orderId, title, description, customerVat,
                        startDate, endDate, fee, paymentCode, maxAmount, chargeAccount));
            }
        }
    }

    public void executeOrdersForDate(LocalDate date) {
        for (Order order : activeOrders) {
            if (!order.isActiveOn(date)) continue;

            try {
                order.execute(date);
                failureCounts.remove(order.getOrderId());
            } catch (Exception e) {
                String orderId = order.getOrderId();
                int count = failureCounts.getOrDefault(orderId, 0) + 1;
                failureCounts.put(orderId, count);
                if (count >= 3) {
                    System.err.println("Order failed 3 times: " + orderId);
                    // logFailure(orderId, date, e); // optional
                }
            }
        }
    }

    private Map<String, String> parseCsvLine(String line) {
        Map<String, String> map = new HashMap<>();
        for (String part : line.split(",")) {
            String[] kv = part.split(":", 2);
            if (kv.length == 2) map.put(kv[0].trim(), kv[1].trim());
        }
        return map;
    }
}
