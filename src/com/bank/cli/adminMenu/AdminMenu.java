package com.bank.cli.adminMenu;

import com.bank.cli.BaseMenu;
import com.bank.model.Admin;

public class AdminMenu extends BaseMenu {

    public AdminMenu(Admin admin) {
        super(admin);
    }

    @Override
    public void display() {
        System.out.println("Admin Menu");
        System.out.println("1. Manage Customers");
        System.out.println("2. Manage Bank Accounts");
        System.out.println("3. Manage Company Bills");
        System.out.println("4. List Standing Orders");
        System.out.println("5. Simulate Time Passing");
        System.out.println("6. Exit");
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                new CustomerManagementMenu(currentUser).displayAndHandle();
                break;
            case 2:
                new AccountManagementMenu(currentUser).displayAndHandle();
                break;
            case 3:
                new BillManagementMenu(currentUser).displayAndHandle();
                break;
            case 4:
                new StandingOrderMenu(currentUser).displayAndHandle();
                break;
            case 5:
                new TimeSimulationMenu(currentUser).displayAndHandle();
                break;
            case 6:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
}

