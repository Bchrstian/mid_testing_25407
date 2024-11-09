package test;

import model.Location;
import model.Location_type;
import org.junit.Before;
import org.junit.Test;
import service.LocationService;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LocationServiceProvinceTest {
    private LocationService locationService;
    private Scanner scanner;

    @Before
    public void setUp() {
        locationService = new LocationService();
        scanner = new Scanner(System.in);
    }

    @Test
    public void testGetProvinceByVillage() {
        // Prompting the user to enter the village name
        System.out.print("Enter the village name: ");
        String villageName = scanner.nextLine();

        // Fetch the province based on the village name entered
        Location result = locationService.getProvinceByVillage(villageName);

        // Check that the result is not null and verify the expected structure
        if (result != null) {
            assertNotNull("Expected to find a province for the given village", result);
            assertEquals("Expected location type to be PROVINCE", Location_type.PROVINCE, result.getLocationType());

            // Output the name of the province
            System.out.println("Province found: " + result.getLocationName());
        } else {
            System.out.println("No province found for the village: " + villageName);
        }
    }
}
