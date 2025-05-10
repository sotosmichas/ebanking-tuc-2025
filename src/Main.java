import com.bank.managers.UserManager;
import com.bank.model.Company;
import com.bank.model.Individual;
import com.bank.model.User;

import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        User user = new Company("giannis","2006","giannis","12345");

        System.out.println(user.getUsername());

        }
    }
