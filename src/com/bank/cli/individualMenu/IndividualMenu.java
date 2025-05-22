package com.bank.cli.individualMenu;

import com.bank.cli.BaseMenu;
import com.bank.cli.adminMenu.StandingOrderMenu;
import com.bank.cli.companyMenu.AccountOverviewMenu;
import com.bank.model.Individual;

public class IndividualMenu extends BaseMenu {

    public IndividualMenu(Individual individual) {

        super(individual);

    }



    @Override

    public void display() {

        showHeader("Customer Menu");

        System.out.println("1. Account Overview");

        System.out.println("2. Transactions");

        System.out.println("3. Pay Bill");

        System.out.println("4. Standing Orders");

        System.out.println("5. Exit");

    }



    @Override

    public void handleChoice(int choice) {

        switch (choice) {

            case 1:

                new AccountOverviewMenu(currentUser).displayAndHandle();

                break;

            case 2:

                new TransactionMenu(currentUser).displayAndHandle();

                break;

            case 3:

                new BillPaymentMenu(currentUser).displayAndHandle();

                break;

            case 4:

                new StandingOrderMenu(currentUser).displayAndHandle();

                break;

            case 5:

                System.exit(0);

            default:

                System.out.println("Invalid choice");

        }

    }



}


