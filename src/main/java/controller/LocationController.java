package controller;

import model.Location;
import model.Location_type;
import service.LocationService;

import java.util.List;
import java.util.UUID;

public class LocationController {

    private final LocationService locationService;

    public LocationController() {
        this.locationService = new LocationService();
    }

    public Location findOrCreateLocation(String locationCode, String locationName, Location_type type, Location parent) {
        return locationService.findOrCreateLocation(locationCode, locationName, type, parent);
    }

    public Location getLocationById(UUID locationId) {
        return locationService.getLocationById(locationId);
    }

    public Location getLocationByName(String locationName) {
        return locationService.getLocationByName(locationName);
    }

    public void createLocation(String locationCode, String locationName, Location_type locationType, UUID parentId) {
        locationService.createLocation(locationCode, locationName, locationType, parentId);
    }

    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    public List<Location> getAllProvinces() {
        return locationService.getAllProvinces();
    }

    public List<Location> getAllDistricts() {
        return locationService.getAllDistricts();
    }

    public List<Location> getAllSectors(UUID districtId) {
        return locationService.getAllSectors(districtId);
    }

    public List<Location> getAllCells(UUID sectorId) {
        return locationService.getAllCells(sectorId);
    }

    public List<Location> getAllVillages(UUID cellId) {
        return locationService.getAllVillages(cellId);
    }

    public void updateLocation(UUID locationId, Location updatedLocation) {
        locationService.updateLocation(locationId, updatedLocation);
    }

    public void deleteLocation(UUID locationId) {
        locationService.deleteLocation(locationId);
    }

    public Location getProvinceByVillage(String villageName) {
        return locationService.getProvinceByVillage(villageName);
    }

    public Location getVillageByName(String villageName) {
        return locationService.getVillageByName(villageName);
    }

    public boolean doesVillageExist(String villageName) {
        return locationService.doesVillageExist(villageName);
    }
}
