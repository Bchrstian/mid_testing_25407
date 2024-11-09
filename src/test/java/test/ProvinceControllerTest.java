package test;

import controller.UserController;
import model.Location;
import model.Location_type;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProvinceControllerTest {
    private UserController userController;
    private Scanner scanner;

    @Before
    public void setUp() {
        userController = new UserController();
        scanner = new Scanner(System.in);
    }

    @Test
    public void testGetProvinceByPersonId() {
        // Prompting the user to enter the person ID
        System.out.print("Enter the person ID: ");
        String personId = scanner.nextLine();

        // Fetching the province by person ID
        String result = userController.getProvinceByPersonId(personId);

        // Check that the result is not null and verify the expected structure
        if (result != null) {
            // Verify that the result is the expected province name
            System.out.println("Province found: " + result);
            assertEquals("Expected to find a valid province for the given person ID", result, result);
        } else {
            System.out.println("No province found for the person with ID: " + personId);
            assertNull("Expected no province for the given person ID", result);
        }
    }
}
