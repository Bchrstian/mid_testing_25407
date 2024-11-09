package test;

import controller.LocationController;
import model.Location;
import model.Location_type;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class LocationTest {

    private LocationController locationController;

    @Before
    public void setUp() {
        locationController = new LocationController();
    }

    @Test
    public void testInsertLocationFromJson() {
        String filePath = "C:\\Users\\HP\\eclipse-workspace\\library_management_system\\src\\main\\java\\resources\\LocationData.json";  // Adjust the file path if needed
        Map<String, List<Location>> locationMap = loadLocations(filePath);

        assertNotNull("Location map should not be null", locationMap);
        assertFalse("Location map should contain data", locationMap.isEmpty());

        // Verify that we inserted the correct number of locations
        locationMap.forEach((provinceName, locations) -> {
            assertFalse("Location list for province " + provinceName + " should not be empty", locations.isEmpty());
            locations.forEach(location -> {
                assertNotNull("Location should have a valid ID", location.getLocationId());
                System.out.println("Inserted Location: " + location.getLocationName());
            });
        });
    }

    private Map<String, List<Location>> loadLocations(String filePath) {
        Map<String, List<Location>> locationMap = new HashMap<>();
        
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            // Parse JSON file
            JSONObject jsonObject = new JSONObject(new JSONTokener(fileInputStream));
            JSONArray locationArray = jsonObject.getJSONArray("Table 1");

            for (int i = 0; i < locationArray.length(); i++) {
                JSONObject locationData = locationArray.getJSONObject(i);

                // Extract location data
                String provinceName = locationData.getString("Province");
                String districtName = locationData.getString("District");
                String sectorName = locationData.getString("Sector");
                String cellName = locationData.getString("Cellule");
                String villageName = locationData.getString("Village");
                
                // Get Vill_Code as numeric value
                int villageCode = locationData.getInt("Vill_Code");

                // Create or retrieve each level of location hierarchy
                Location province = createOrFindLocation(null, provinceName, Location_type.PROVINCE, null);
                Location district = createOrFindLocation(null, districtName, Location_type.DISTRICT, province);
                Location sector = createOrFindLocation(null, sectorName, Location_type.SECTOR, district);
                Location cell = createOrFindLocation(null, cellName, Location_type.CELL, sector);

                // Convert villageCode to String for Location object if needed
                Location village = createOrFindLocation(String.valueOf(villageCode), villageName, Location_type.VILLAGE, cell);

                // Add village to the map for further usage if needed
                locationMap.computeIfAbsent(provinceName, k -> new ArrayList<>()).add(village);
            }

            System.out.println("Completed Location Load");
            return locationMap;

        } catch (Exception e) {
            System.out.println("Error loading locations: " + e.getMessage());
            return null;
        }
    }

    private Location createOrFindLocation(String locationCode, String locationName, Location_type type, Location parent) {
        // Using the LocationController to find or create the location
        Location location = locationController.findOrCreateLocation(locationCode, locationName, type, parent);
        return location;
    }
}
