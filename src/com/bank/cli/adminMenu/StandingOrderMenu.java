package com.bank.cli.adminMenu;
import com.bank.cli.BaseMenu;
import com.bank.managers.StandingOrderManager;
import com.bank.model.Order;
import com.bank.model.PaymentOrder;
import com.bank.model.TransferOrder;
import com.bank.model.User;
import java.util.List;

public class StandingOrderMenu extends BaseMenu {

    private final StandingOrderManager standingOrderManager;

    public StandingOrderMenu(User currentUser) {
        super(currentUser);
        this.standingOrderManager = StandingOrderManager.getInstance();
    }

    @Override
    public void display() {
        showHeader("Standing Orders Management");
        System.out.println("1. View All Active Orders");
        System.out.println("2. View All Expired Orders");
        System.out.println("3. View Orders by Customer");
        System.out.println("4. Cancel Standing Order");
        System.out.println("5. Back to Admin Menu");
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1 -> viewActiveOrders();
            case 3 -> viewOrdersByCustomer();
            case 4 -> cancelStandingOrder();
            case 5 -> {
                return;
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void viewActiveOrders() {
        List<Order> activeOrders = standingOrderManager.getActiveOrders();
        printOrders("Active Standing Orders", activeOrders);
    }

    private void viewOrdersByCustomer() {
        String customerVat = getStringInput("Enter Customer VAT: ");
        List<Order> customerOrders = standingOrderManager.getOrdersByCustomer(customerVat);
        printOrders("Standing Orders for Customer " + customerVat, customerOrders);
    }

    private void cancelStandingOrder() {
        String orderId = getStringInput("Enter Order ID to cancel: ");
        try {
            standingOrderManager.cancelOrder(orderId);
            System.out.println("Standing order cancelled successfully.");
        } catch (Exception e) {
            System.out.println("Error cancelling order: " + e.getMessage());
        }
    }

    private void printOrders(String title, List<Order> orders) {
        System.out.println("\n" + title);

        for (Order order : orders) {
            String type = order instanceof PaymentOrder ? "Payment" : "Transfer";
            String status = order.isActiveOn(java.time.LocalDate.now()) ? "Active" : "Inactive";

            System.out.println("----------------------------");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Type: " + type);
            System.out.println("Status: " + status);
            System.out.println("Start Date: " + order.getStartDate());
            System.out.println("End Date: " + order.getEndDate());

            if (order instanceof PaymentOrder payment) {
                System.out.println("RF Code: " + payment.getPaymentCode());
                System.out.println("Max Amount: " + payment.getMaxAmount());
                System.out.println("Customer: " + payment.getCustomerVat());
            } else if (order instanceof TransferOrder transfer) {
                System.out.println("Amount: " + transfer.getAmount());
                System.out.println("Customer: " + transfer.getCustomerVat());
                System.out.println("From: " + transfer.getFromIban());
                System.out.println("To: " + transfer.getToIban());
                System.out.println("Frequency: every " + transfer.getFrequencyInMonths() + " month(s)");
                System.out.println("Day of Month: " + transfer.getExecutionDayOfMonth());
            }
        }

        System.out.println("----------------------------");
    }
}

