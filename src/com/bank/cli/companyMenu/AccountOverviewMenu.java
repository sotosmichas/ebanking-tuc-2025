package com.bank.cli.companyMenu;

import com.bank.cli.BaseMenu;
import com.bank.managers.AccountManager;
import com.bank.managers.StatementManager;
import com.bank.model.BusinessAccount;
import com.bank.model.Company;
import com.bank.model.Statements;

import java.time.LocalDate;
import java.util.List;

public class AccountOverviewMenu extends BaseMenu {

    private final AccountManager accountManager;
    private final StatementManager statementManager;

    public AccountOverviewMenu(Company currentUser) {
        super(currentUser);
        this.accountManager = AccountManager.getInstance();
        this.statementManager = StatementManager.getInstance();
    }

    @Override
    public void display() {
        Company company = (Company) currentUser;
        BusinessAccount account = accountManager.getBusinessAccountByVat(company.getVat());

        System.out.println("\nAccount Overview");
        System.out.println("IBAN: " + account.getIban());
        System.out.println("Balance: " + account.getBalance());
        System.out.println("Maintenance Fee: " + account.getMaintenanceFee());
        System.out.println("Interest Rate: " + account.getInterestRate() + "%");

        List<Statements> recentStatements = statementManager.getRecentStatements(account.getIban(), 5);
        System.out.println("\nRecent Transactions");
        printStatementsTable(recentStatements);

        System.out.println("\n1. View Full Transaction History");
        System.out.println("2. View Monthly Statement");
        System.out.println("3. Back to Main Menu");

        int choice = getIntInput("Choice: ");
        handleChoice(choice);
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                viewFullHistory();
                break;
            case 2:
                viewMonthlyStatement();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void viewFullHistory() {
        Company company = (Company) currentUser;
        BusinessAccount account = accountManager.getBusinessAccountByVat(company.getVat());
        List<Statements> statements = statementManager.getStatementsForAccount(account.getIban());

        System.out.println("\nFull Transaction History");
        printStatementsTable(statements);
    }

    private void viewMonthlyStatement() {
        String monthYear = getStringInput("Enter month and year (MM/yyyy): ");
        Company company = (Company) currentUser;
        BusinessAccount account = accountManager.getBusinessAccountByVat(company.getVat());

        try {
            List<Statements> monthlyStatements = statementManager.getMonthlyStatements(account.getIban(), monthYear);
            System.out.println("\nMonthly Statement (" + monthYear + ")");
            printStatementsTable(monthlyStatements);

            double totalFees = 0;
            double totalInterest = 0;

            for (Statements s : monthlyStatements) {
                if (s.getDescription().toLowerCase().contains("fee")) {
                    totalFees += s.getAmount();
                }
                if (s.getDescription().toLowerCase().contains("interest")) {
                    totalInterest += s.getAmount();
                }
            }

            System.out.println("\nTotal Fees: " + totalFees);
            System.out.println("Total Interest: " + totalInterest);

        } catch (Exception e) {
            System.out.println("Error generating statement: " + e.getMessage());
        }
    }

    private void printStatementsTable(List<Statements> statements) {
        System.out.printf("%-12s %-30s %-10s %-15s\n", "Date", "Description", "Amount", "Balance");
        for (Statements stmt : statements) {
            System.out.printf("%-12s %-30s %-10.2f %-15.2f\n",
                    stmt.getDate(),
                    stmt.getDescription(),
                    stmt.getAmount(),
                    stmt.getBalanceAfter());
        }
    }
}
