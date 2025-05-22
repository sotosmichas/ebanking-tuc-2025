package com.bank.cli.companyMenu;
import com.bank.cli.BaseMenu;
import com.bank.managers.BillManager;
import com.bank.managers.UserManager;
import com.bank.model.Bill;
import com.bank.model.Company;
import com.bank.model.Customer;
import com.bank.model.User;

import java.time.LocalDate;
import java.util.List;





public class BillCompanyManagementMenu extends BaseMenu {

    private final BillManager billManager;

    private final UserManager userManager;



    public BillCompanyManagementMenu(User currentUser) {

        super(currentUser);

        this.billManager=BillManager.getInstance();

        this.userManager=UserManager.getInstance();

    }



    @Override

    public void display() {

        System.out.println("Bill Management");

        System.out.println("1. Issue New Bill");

        System.out.println("2. View Issued Bills");

        System.out.println("3. View Paid Bills");

        System.out.println("4. Back to Main Menu");

    }



    @Override

    public void handleChoice(int choice) {

        switch (choice) {

            case 1:

                issueNewBill();

                break;

            case 2:

                viewIssuedBills(false); // Show unpaid bills

                break;

            case 3:

                viewPaidBills();

                break;
            case 4:

                return;

            default:

                System.out.println("Invalid choice. Please try again.");

        }

    }



    private void issueNewBill() {

        Company company=(Company) currentUser;

        String customerVat=getStringInput("Enter customer VAT: ");



        Customer customer=(Customer) userManager.getUserByVat(customerVat);

        if (customer==null) {

            System.out.println("Customer not found.");

            return;

        }



        double amount=getDoubleInput("Enter amount: ");

        if (amount<=0) {

            System.out.println("Amount must be positive.");

            return;

        }



        String description=getStringInput("Enter description: ");

        LocalDate dueDate=getDateInput("Enter due date (yyyy-MM-dd): ");



        try {

            Bill bill=billManager.createBill(company, customerVat, amount, description, dueDate);

            System.out.println("\nBill issued successfully:");

            System.out.println("Bill Number: "+bill.getBillNumber());

            System.out.println("RF Code: "+bill.getRfCode());

            System.out.println("Amount: "+bill.getAmount());

            System.out.println("Customer: "+customer.getFullName());

            System.out.println("Due Date: "+bill.getDueDate());

        } catch (Exception e){

            System.out.println("Error issuing bill: "+e.getMessage());

        }

    }



    private void viewIssuedBills(boolean showPaid) {

        Company company=(Company) currentUser;

        List<Bill> bills=billManager.getIssuedBillsByCompany(company.getVat());



        System.out.println("\n" + (showPaid?"All Issued Bills":"Unpaid Bills"));

        System.out.printf("%-12s %-15s %-10s %-20s %-12s %-10s%n",

                "Bill Number", "RF Code", "Amount", "Customer", "Due Date", "Status");



        for (Bill bill:bills) {

            if(!showPaid&&bill.isPaid()) continue;



            Customer customer=(Customer)userManager.getUserByVat(bill.getCustomerVat());

            String customerName=customer!=null?customer.getFullName():bill.getCustomerVat();



            System.out.printf("%-12s %-15s %-10.2f %-20s %-12s %-10s%n",

                    bill.getBillNumber(),

                    bill.getRfCode(),

                    bill.getAmount(),

                    customerName,

                    bill.getDueDate(),

                    bill.isPaid()?"PAID":"PENDING");

        }



        double totalAmount=bills.stream()

                .filter(b->!showPaid||!b.isPaid())

                .mapToDouble(Bill::getAmount)

                .sum();



        System.out.printf("\nTotal Amount: %.2f\n", totalAmount);

    }



    private void viewPaidBills() {

        Company company=(Company) currentUser;

        List<Bill> paidBills=billManager.getPaidBillsByCompany(company.getVat());



        System.out.println("\nPaid Bill");

        System.out.printf("%-12s %-15s %-10s %-20s\n",

                "Bill Number", "RF Code", "Amount", "Customer");



        for (Bill bill:paidBills) {

            Customer customer=(Customer) userManager.getUserByVat(bill.getCustomerVat());

            String customerName=customer!=null?customer.getFullName():bill.getCustomerVat();



            System.out.printf("%-12s %-15s %-10.2f %-20s ",

                    bill.getBillNumber(),

                    bill.getRfCode(),

                    bill.getAmount(),

                    customerName);



        }



        double totalPaid=paidBills.stream()

                .mapToDouble(Bill::getAmount)

                .sum();



        System.out.printf("\nTotal Paid: %.2f\n", totalPaid);

    }










    private LocalDate getDateInput(String prompt) {

        while (true) {

            try {

                String dateStr=getStringInput(prompt);

                return LocalDate.parse(dateStr);

            } catch (Exception e) {

                System.out.println("Invalid date format. Please use yyyy-MM-dd.");

            }

        }

    }

}
