package com.bank.cli.companyMenu;

import com.bank.cli.BaseMenu;
import com.bank.managers.AccountManager;
import com.bank.managers.StatementManager;
import com.bank.model.BusinessAccount;
import com.bank.model.Company;
import com.bank.model.Statements;

import java.util.List;

public class TransactionHistoryMenu extends BaseMenu {

    private final AccountManager accountManager;
    private final StatementManager statementManager;

    public TransactionHistoryMenu(Company currentUser) {
        super(currentUser);
        this.accountManager = AccountManager.getInstance();
        this.statementManager = StatementManager.getInstance();
    }

    @Override
    public void display() {
        Company company = (Company) currentUser;
        BusinessAccount account = accountManager.getBusinessAccountByVat(company.getVat());

        System.out.println("\nTransaction History");
        System.out.println("Account: " + account.getIban());
        System.out.println("Current Balance: " + account.getBalance());

        List<Statements> recentStatements = statementManager.getRecentStatements(account.getIban(), 10);

        System.out.println("\nLast 10 Transactions");
        printStatementsTable(recentStatements);

        System.out.println("\n1. View All Transactions");
        System.out.println("2. Back to Main Menu");

        int choice = getIntInput("Choice: ");
        handleChoice(choice);
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                viewAllTransactions();
                break;
            case 2:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void viewAllTransactions() {
        Company company = (Company) currentUser;
        BusinessAccount account = accountManager.getBusinessAccountByVat(company.getVat());

        List<Statements> statements = statementManager.getStatementsForAccount(account.getIban());

        System.out.println("\nAll Transactions");
        printStatementsTable(statements);
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
