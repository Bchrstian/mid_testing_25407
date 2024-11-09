package test;

import controller.UserController;
import model.Gender;
import model.Role;
import model.User;
import model.Location;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class UserControllerTest {

    @Test
    public void testSaveUserWithInput() {
        Scanner scanner = new Scanner(System.in);
        UserController userController = new UserController();

        // Prompt user for basic details
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter gender (MALE/FEMALE): ");
        String genderInput = scanner.nextLine();
        Gender gender = Gender.valueOf(genderInput.toUpperCase());

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter personal ID (national ID): ");
        String personalId = scanner.nextLine();

        // Prompt user for new fields
        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter role (STUDENT, MANAGER, TEACHER, DEAN, HOD, LIBRARIAN): ");
        String roleInput = scanner.nextLine();
        Role role = Role.valueOf(roleInput.toUpperCase());

        // Encrypt (hash) the password
        String hashedPassword = hashPassword(password);

        System.out.print("Enter village name: ");
        String villageName = scanner.nextLine();

        // Retrieve village object from the location table
        Location village = userController.getVillageByName(villageName); // Assuming this method exists

        if (village == null) {
            System.out.println("Village not found, please try again.");
            return; // Exit if the village is not found
        }

        // Create user object and set fields
        User user = new User();
        user.setFirst_name(firstName);
        user.setLast_name(lastName);
        user.setGender(gender);
        user.setPhone_number(phoneNumber);
        user.setEmail(email);
        user.setUser_id(UUID.randomUUID());
        user.setPerson_id(personalId);
        user.setLocation(village); // Set the entire Location object
        user.setUser_name(userName); // Set user name
        user.setPassword(hashedPassword); // Store the hashed password
        user.setRole(role); // Set role

        // Save the user
        userController.saveUser(user, village.getLocationName()); // Assuming saveUser handles the User object

        // Optional assertion if `saveUser` returns the saved User object or ID
        // assertNotNull(savedUserId);  // Uncomment if a saved user ID is returned
    }

    // Helper method to hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
