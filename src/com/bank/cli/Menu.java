package com.bank.cli;

import com.bank.model.User;

import java.util.Scanner;

public abstract class Menu {

    protected final Scanner scanner;

    protected final User currentUser;



    public Menu(User currentUser) {

        this.scanner=new Scanner(System.in);

        this.currentUser=currentUser;

    }



    public abstract void display();

    public abstract void handleChoice(int choice);



    protected void showHeader(String title) {

        System.out.println(title);

    }



    protected int getIntInput(String prompt) {

        System.out.print(prompt);

        while (!scanner.hasNextInt()) {

            System.out.println("Invalid input. Please enter a number.");

            scanner.next();

            System.out.print(prompt);

        }

        int choice = scanner.nextInt();

        scanner.nextLine();

        return choice;

    }



    protected String getStringInput(String prompt) {

        System.out.print(prompt);

        return scanner.nextLine();

    }



    protected double getDoubleInput(String prompt) {

        System.out.print(prompt);

        while (!scanner.hasNextDouble()) {

            System.out.println("Invalid input. Please enter a number.");

            scanner.next();

            System.out.print(prompt);

        }

        double amount = scanner.nextDouble();

        scanner.nextLine();

        return amount;

    }

}
