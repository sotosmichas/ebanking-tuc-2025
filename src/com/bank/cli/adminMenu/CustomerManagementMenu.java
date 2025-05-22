package com.bank.cli.adminMenu;

import com.bank.cli.BaseMenu;
import com.bank.managers.UserManager;
import com.bank.model.Company;
import com.bank.model.Customer;
import com.bank.model.Individual;
import com.bank.model.User;

import java.util.ArrayList;
import java.util.List;

public class CustomerManagementMenu extends BaseMenu {

    private final UserManager userManager;

    public CustomerManagementMenu(User currentUser) {
        super(currentUser);
        this.userManager = UserManager.getInstance();
    }

    @Override
    public void display() {
        System.out.println("Customer Management");
        System.out.println("1. List All Customers");
        System.out.println("2. View Customer Details");
        System.out.println("3. Create New Customer");
        System.out.println("4. Back to Main Menu");
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1 -> listAllCustomers();
            case 2 -> viewCustomerDetails();
            case 3 -> createNewCustomer();
            case 4 -> {
                return;
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void listAllCustomers() {
        System.out.println("\nCustomers:");

        List<User> users = new ArrayList<>(userManager.getAllUsers().values());
        for (User user : users) {
            if (user instanceof Customer customer) {
                System.out.println("----------------------------");
                System.out.println("VAT: " + customer.getVat());
                System.out.println("Name: " + customer.getFullName());
                System.out.println("Username: " + customer.getUsername());
                System.out.println("Type: " + (user instanceof Individual ? "Individual" : "Company"));
            }
        }
        System.out.println("----------------------------");
    }

    private void viewCustomerDetails() {
        String vat = getStringInput("Enter customer VAT: ");
        User user = userManager.getUserByVat(vat);

        if (!(user instanceof Customer customer)) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("\nCustomer Details");
        System.out.println("VAT: " + customer.getVat());
        System.out.println("Full Name: " + customer.getFullName());
        System.out.println("Username: " + customer.getUsername());
        System.out.println("Type: " + customer.getClass().getSimpleName());



    }

    private void createNewCustomer() {
        System.out.println("\n=== Create New Customer ===");
        System.out.println("1. Create Individual");
        System.out.println("2. Create Company");
        System.out.println("3. Cancel");

        int typeChoice = getIntInput("Select customer type: ");
        if (typeChoice == 3) return;

        String vat = getStringInput("VAT: ");
        String username = getStringInput("Username: ");
        String password = getStringInput("Password: ");
        String fullName = getStringInput("Full Name: ");

        try {
            User newUser;

            if (typeChoice == 1) {
                newUser = new Individual(vat, username, password, fullName);
            } else if (typeChoice == 2) {
                newUser = new Company(vat, username, password, fullName);
            } else {
                System.out.println("Invalid choice.");
                return;
            }

            userManager.addUser(newUser);
            System.out.println("Successfully created new customer: " + newUser.getFullName());

        } catch (Exception e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
    }
}

