package com.bank.cli;


import com.bank.model.Customer;
import com.bank.model.User;

public abstract class BaseMenu extends Menu {

    public BaseMenu(User currentUser) {

        super(currentUser);

    }



    public void displayAndHandle() {

        display();

        int choice = getIntInput("Choice: ");

        handleChoice(choice);

    }

}
