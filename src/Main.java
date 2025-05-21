
import com.bank.model.*;
import com.bank.storage.*;

import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        StorageManager storage = new StorageManagerImpl();
        UserCollection userCollection = new UserCollection();
        AccountCollection accountCollection = new AccountCollection();
        BillCollection billCollection = new BillCollection();

        try {

            storage.load(billCollection, "data/bills/2025-05-01.csv");


            for (Bill bill : billCollection.getBills()) {
                System.out.println("Type: "+bill.getClass().getSimpleName()+" Payment Code:"+bill.getPaymentCode()+"📌 Bill → " +
                        bill.getBillNumber() + " | Customer: " + bill.getCustomerVat() +
                        " | Amount: " + bill.getAmount() + "€ | Due: " + bill.getDueDate());
            }

        } catch (Exception e) {
            System.err.println("❌ Failed to load bills: " + e.getMessage());
            e.printStackTrace();
        }



        try {
            storage.load(userCollection, "data/users/users.csv");



            for (User user : userCollection.getUsers()) {
                System.out.println(user.getClass().getSimpleName() + ": " + user.getFullName() + " (" + user.getUsername() + ")");
            }

            // ✅ Πρόσβαση σε έναν συγκεκριμένο χρήστη
            User first = userCollection.getUsers().get(0);
            System.out.println("\n--- First user loaded ---");
            System.out.println("Username: " + first.getUsername());
            System.out.println("Full Name: " + first.getFullName());
            System.out.println("Password: "+first.getPassword());


        } catch (Exception e) {
            System.err.println("Failed to load users: " + e.getMessage());
            e.printStackTrace();
        }
    }
    }
