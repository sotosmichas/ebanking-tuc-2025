package com.bank.cli.adminMenu;


import com.bank.cli.BaseMenu;
import com.bank.managers.BillManager;
import com.bank.managers.UserManager;
import com.bank.model.Bill;
import com.bank.model.Company;
import com.bank.model.Customer;
import com.bank.model.User;
import java.time.LocalDate;
import java.util.List;


public class BillManagementMenu extends BaseMenu {

    private final BillManager billManager;

    private final UserManager userManager;



    public BillManagementMenu(User currentUser) {

        super(currentUser);

        this.billManager=BillManager.getInstance();

        this.userManager=UserManager.getInstance();

    }



    @Override

    public void display() {

        showHeader("Company Bills Management");

        System.out.println("1. View All Company Bills");

        System.out.println("2. View Bills by Company");

        System.out.println("3. Load Daily Bills");

        System.out.println("4. Pay Bill on Behalf");

        System.out.println("5. Back to Admin Menu");

    }



    @Override

    public void handleChoice(int choice) {

        switch (choice) {

            case 1:

                viewAllCompanyBills();

                break;

            case 2:

                viewBillsByCompany();

                break;

            case 3:

                loadDailyBills();

                break;

            case 4:

                payBillOnBehalf();

                break;

            case 5:

                return;

            default:

                System.out.println("Invalid choice. Please try again.");

        }

    }



    private void viewAllCompanyBills() {

        List<Bill> allBills=billManager.getAllBills();



        System.out.println("\nAll Company Bills");

        printBillsTable(allBills);

    }



    private void viewBillsByCompany() {

        String companyVat=getStringInput("Enter Company VAT: ");

        Company company=(Company) userManager.getUserByVat(companyVat);



        if (company==null) {

            System.out.println("Company not found.");

            return;

        }



        List<Bill> companyBills=billManager.getIssuedBillsByCompany(companyVat);

        System.out.printf("\nBills for %s\n", company.getFullName());

        printBillsTable(companyBills);

    }



    private void loadDailyBills() {

        String date=getStringInput("Enter date to load (yyyy-MM-dd): ");

        try {

            billManager.loadDailyBills(LocalDate.parse(date));

            System.out.println("Successfully loaded bills for " + date);

        } catch (Exception e) {

            System.out.println("Error loading bills: " + e.getMessage());

        }

    }



    private void payBillOnBehalf() {

        String billNumber=getStringInput("Enter Bill Number to pay: ");

        Bill bill=billManager.getBillByNumber(billNumber);



        if (bill==null) {

            System.out.println("Bill not found.");

            return;

        }



        try {

            billManager.payBill(bill);

            System.out.println("Bill paid successfully. Transaction recorded.");

        } catch (Exception e) {

            System.out.println("Error paying bill: "+e.getMessage());

        }

    }



    private void printBillsTable(List<Bill> bills) {

        System.out.printf("%-15s %-15s %-10s %-15s %-15s %-10s%n",

                "Bill Number", "RF Code", "Amount", "Company", "Customer", "Status");



        for (Bill bill:bills) {

            System.out.printf("%-15s %-15s %-10.2f %-15s %-15s %-10s%n",

                    bill.getBillNumber(),

                    bill.getRfCode(),

                    bill.getAmount(),

                    bill.getIssuer().getFullName(),

                    bill.getCustomerVat(),

                    bill.isPaid()?"PAID":"UNPAID");

        }

    }



}
