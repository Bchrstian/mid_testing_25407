package test;


import org.junit.Test;
import service.UserService;
import service.MembershipService;
import service.MembershipTypeService;
import model.Membership;
import model.MembershipType;
import model.Status;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.Assert.*;

public class MembershipControllerTest {

    private final UserService userService = new UserService();
    private final MembershipTypeService membershipTypeService = new MembershipTypeService();
    private final MembershipService membershipService = new MembershipService();

    @Test
    public void testRegisterMembership() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Prompt user for email and password
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Authenticate user and get UUID
        UUID userId = userService.authenticateAndGetUserId(email, password);
        assertNotNull("User authentication failed or user not found.", userId);

        // Step 2: Get list of membership types and prompt the user to choose one
        List<MembershipType> membershipTypes = membershipTypeService.getAllMembershipTypes();
        assertFalse("No membership types available.", membershipTypes.isEmpty());

        System.out.println("Available Membership Types:");
        for (int i = 0; i < membershipTypes.size(); i++) {
        	// Replace getName() with getMembership_name() in the test code
        	System.out.println((i + 1) + ". " + membershipTypes.get(i).getMembership_name());

        }
        System.out.print("Select a membership type by entering the corresponding number: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        // Validate the user's choice
        if (choice < 1 || choice > membershipTypes.size()) {
            fail("Invalid membership type selection.");
        }

        MembershipType chosenType = membershipTypes.get(choice - 1); // Select the chosen type
        Date registrationDate = new Date();
        membershipService.registerMembership(userId, chosenType.getMembership_type_id(), registrationDate);

        // Step 4: Verify the membership was registered
        Membership membership = membershipService.getMembershipById(userId); 
        assertNotNull("Membership registration failed.", membership);
        assertEquals(chosenType.getMembership_type_id(), membership.getMembership_type().getMembership_type_id());
        assertEquals(registrationDate, membership.getRegistration_date());
        assertEquals(Status.PENDING, membership.getMembership_status());
    }
}
