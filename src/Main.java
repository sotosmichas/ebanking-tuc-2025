import com.bank.managers.AccountManager;
import com.bank.managers.UserManager;
import com.bank.model.*;

import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        User user = new Company("giannis","2006","giannis","12345");
        AccountManager accountManager = AccountManager.getInstance();
        UserManager userManager = UserManager.getInstance();

        PersonalAccount gioldasis = new PersonalAccount("GR100","3665",2.4);
        BankAccount nova = new BusinessAccount("GR200","3675",2.0,3.0);
        accountManager.addAccount(nova);
        accountManager.addAccount(gioldasis);
        System.out.println(accountManager.getAllAccounts());
        System.out.println(user.getUsername());
        userManager.addUser(user);
        System.out.println(userManager.getAllUsers());
        UserManager.getInstance().login("giannis","2006");

                String workingDir = System.getProperty("user.dir");
                System.out.println("Project path: " + workingDir);



    }
    }
