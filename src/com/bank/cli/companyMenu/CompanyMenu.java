package com.bank.cli.companyMenu;
import com.bank.cli.BaseMenu;
import com.bank.cli.adminMenu.BillManagementMenu;
import com.bank.model.Company;


public class CompanyMenu extends BaseMenu {

    public CompanyMenu(Company company) {

        super(company);

    }



    @Override

    public void display() {

        showHeader("Company Menu");

        System.out.println("1.Account Overview");

        System.out.println("2.Manage Bills");

        System.out.println("3.View Transactions");

        System.out.println("4.Exit");

    }



    @Override

    public void handleChoice(int choice) {

        switch (choice) {

            case 1:

                new AccountOverviewMenu((Company) currentUser).displayAndHandle();

                break;

            case 2:

                new BillCompanyManagementMenu(currentUser).displayAndHandle();

                break;

            case 3:

                new TransactionHistoryMenu((Company) currentUser).displayAndHandle();

                break;

            case 4:

                System.exit(0);

            default:

                System.out.println("Invalid choice");

        }

    }

}