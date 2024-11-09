package test;

import controller.MembershipTypeController;
import model.MembershipType;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class MembershipTypeControllerTest {

    @Test
    public void testSaveMembershipTypes() {
        // Create an instance of MembershipTypeController
        MembershipTypeController membershipTypeController = new MembershipTypeController();

        // Create Gold Membership
        MembershipType goldMembership = new MembershipType();
        goldMembership.setMembership_name("Gold");
        goldMembership.setPrice(50); // Price in Rwf per day
        goldMembership.setMax_books(5); // Max books can be borrowed

        // Save Gold Membership
        membershipTypeController.saveMembershipType(goldMembership);

        // Create Silver Membership
        MembershipType silverMembership = new MembershipType();
        silverMembership.setMembership_name("Silver");
        silverMembership.setPrice(30); // Price in Rwf per day
        silverMembership.setMax_books(3); // Max books can be borrowed

        // Save Silver Membership
        membershipTypeController.saveMembershipType(silverMembership);

        // Create Striver Membership
        MembershipType striverMembership = new MembershipType();
        striverMembership.setMembership_name("Striver");
        striverMembership.setPrice(10); // Price in Rwf per day
        striverMembership.setMax_books(2); // Max books can be borrowed

        // Save Striver Membership
        membershipTypeController.saveMembershipType(striverMembership);

        // Test if the memberships are saved correctly
        // Check if saved Memberships are retrieved from the database
        MembershipType retrievedGold = membershipTypeController.getMembershipTypeById(goldMembership.getMembership_type_id());
        MembershipType retrievedSilver = membershipTypeController.getMembershipTypeById(silverMembership.getMembership_type_id());
        MembershipType retrievedStriver = membershipTypeController.getMembershipTypeById(striverMembership.getMembership_type_id());

        // Assert that the memberships are not null and match the input values
        assertNotNull("Gold membership should be saved", retrievedGold);
        assertEquals("Gold membership name should match", "Gold", retrievedGold.getMembership_name());
        assertEquals("Gold membership price should match", 50, retrievedGold.getPrice());
        assertEquals("Gold membership max books should match", 5, retrievedGold.getMax_books());

        assertNotNull("Silver membership should be saved", retrievedSilver);
        assertEquals("Silver membership name should match", "Silver", retrievedSilver.getMembership_name());
        assertEquals("Silver membership price should match", 30, retrievedSilver.getPrice());
        assertEquals("Silver membership max books should match", 3, retrievedSilver.getMax_books());

        assertNotNull("Striver membership should be saved", retrievedStriver);
        assertEquals("Striver membership name should match", "Striver", retrievedStriver.getMembership_name());
        assertEquals("Striver membership price should match", 10, retrievedStriver.getPrice());
        assertEquals("Striver membership max books should match", 2, retrievedStriver.getMax_books());
    }
}
