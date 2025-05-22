package com.bank.cli;



import com.bank.cli.BaseMenu;
import com.bank.cli.adminMenu.AdminMenu;
import com.bank.cli.companyMenu.CompanyMenu;
import com.bank.managers.UserManager;
import com.bank.model.Admin;
import com.bank.model.Company;
import com.bank.model.Individual;
import com.bank.model.User;

import java.util.Scanner;

public class CLI {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        User currentUser = authenticateUser();

        if (currentUser == null) {
            System.out.println("Authentication failed. Exiting...");
            System.exit(0);
        }

        Menu menu = createMenuForUser(currentUser);

        while (true) {
            menu.display();
            int choice = ((BaseMenu) menu).getIntInput("Choice: ");
            menu.handleChoice(choice);
        }
    }

    private User authenticateUser() {
        System.out.println("Welcome to Bank Of TUC");
        String username = getInput("Username: ");
        String password = getInput("Password: ");
        return UserManager.getInstance().login(username, password);
    }

    private Menu createMenuForUser(User user) {
        if (user instanceof Admin) {
            return new AdminMenu((Admin) user);
        } else if (user instanceof Individual) {
            return new IndividualMenu(user);
        } else if (user instanceof Company) {
            return new CompanyMenu((Company) user);
        } else {
            throw new IllegalArgumentException("Unknown user type: " + user.getClass());
        }
    }

    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

