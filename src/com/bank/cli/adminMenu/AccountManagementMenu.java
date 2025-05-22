package com.bank.cli.adminMenu;


import com.bank.cli.BaseMenu;
import com.bank.managers.AccountManager;
import com.bank.managers.UserManager;
import com.bank.model.*;
import com.bank.util.IdGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccountManagementMenu extends BaseMenu {

    private final AccountManager accountManager;
    private final UserManager userManager;

    public AccountManagementMenu(User currentUser) {
        super(currentUser);
        this.accountManager = AccountManager.getInstance();
        this.userManager = UserManager.getInstance();
    }

    @Override
    public void display() {
        showHeader("Account Management");
        System.out.println("1. List All Accounts");
        System.out.println("2. View Account Details");
        System.out.println("3. Create New Account");
        System.out.println("4. Back to Main Menu");
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                listAllAccounts();
                break;
            case 2:
                viewAccountDetails();
                break;
            case 3:
                createNewAccount();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void listAllAccounts() {
        System.out.println("\nAll Accounts");
        System.out.printf("%-25s %-20s %-10s %-15s %-10s%n",
                "IBAN", "Owner", "Type", "Balance", "Currency");

        Map<String,BankAccount> accounts = accountManager.getAllAccounts();
        for (BankAccount account : accounts.values()) {
            String type = account instanceof PersonalAccount ? "Personal" : "Business";
            System.out.printf("%-25s %-20s %-10s %-15.2f",
                    account.getIban(),
                    account.getOwner().getFullName(),
                    type,
                    account.getBalance());

        }
    }

    private void viewAccountDetails() {
        String iban = getStringInput("Enter account IBAN: ");
        BankAccount account = accountManager.getAccountByIban(iban);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("\nAccount Details");
        System.out.println("IBAN: " + account.getIban());
        System.out.println("Owner: " + account.getOwner().getFullName());
        System.out.println("Type: " + (account instanceof PersonalAccount ? "Personal" : "Business"));
        System.out.printf("Balance: %.2f\n", account.getBalance());
        System.out.println("Interest Rate: " + account.getInterestRate() + "%");

        if (account instanceof BusinessAccount) {
            BusinessAccount businessAccount = (BusinessAccount) account;
            System.out.println("Maintenance Fee: " + businessAccount.getMaintenanceFee());
        } else if (account instanceof PersonalAccount) {
            PersonalAccount personalAccount = (PersonalAccount) account;
            Set<Individual> secondaries = personalAccount.getCoOwners();

            if (secondaries.isEmpty()) {
                System.out.println("Secondary Owners: None");
            } else {
                System.out.println("Secondary Owners:");
                for (Individual owner : secondaries) {
                    System.out.println("  - " + owner.getFullName());
                }
            }
        }
    }

    private void createNewAccount() {
        System.out.println("\nCreate New Account");
        System.out.println("1. Personal Account");
        System.out.println("2. Business Account");
        System.out.println("3. Cancel");

        int typeChoice = getIntInput("Select account type: ");
        if (typeChoice == 3) {
            return;
        }

        String ownerVat = getStringInput("Enter Owner VAT: ");
        User owner = userManager.getUserByVat(ownerVat);

        if (!(owner instanceof Customer)) {
            System.out.println("Invalid owner VAT or not a customer.");
            return;
        }

        double interestRate = getDoubleInput("Enter interest rate (%): ");

        try {
            BankAccount newAccount;

            if (typeChoice == 1) {
                // Δημιουργία προσωπικού λογαριασμού με IBAN και empty co-owners
                String iban = IdGenerator.generateIban("Personal");
                newAccount = new PersonalAccount(iban, 0.0, (Individual) owner, LocalDate.now(), interestRate, new HashSet<>());
            } else if (typeChoice == 2) {
                double maintenanceFee = getDoubleInput("Enter monthly maintenance fee: ");
                String iban = IdGenerator.generateIban("Business");
                newAccount = new BusinessAccount(iban, 0.0, (Company) owner, LocalDate.now(), interestRate, maintenanceFee);
            } else {
                System.out.println("Invalid selection.");
                return;
            }

            accountManager.addAccount(newAccount);
            System.out.println("Successfully created new account with IBAN: " + newAccount.getIban());

        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

}

