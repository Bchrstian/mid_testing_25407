package test;

import controller.UserController;
import org.junit.Test;

import java.util.Scanner;

public class AuthenticateUserTest {  // Renamed the test class

    @Test
    public void authenticateUserTest() {  // Renamed the test method
        Scanner scanner = new Scanner(System.in);
        UserController userController = new UserController();

        // Prompt user for login credentials
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Authenticate user
        String result = userController.authenticateUser(email, password);

        
    }
}
