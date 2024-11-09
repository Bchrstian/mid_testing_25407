package controller;

import model.User;
import model.Location;
import service.LocationService;
import service.UserService;

import java.util.List;
import java.util.UUID;

public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    // Method to save a user, takes a User object and a village name
    public void saveUser(User user, String villageName) {
        userService.saveUser(user, villageName);
    }

    // Method to retrieve a user by their ID
    public User getUserById(UUID user_id) {
        return userService.getUserById(user_id);
    }

    // Method to retrieve all users
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Method to update an existing user
    public void updateUser(UUID user_id, User updatedUser) {
        userService.updateUser(user_id, updatedUser);
    }

    // Method to delete a user by their ID
    public void deleteUser(UUID user_id) {
        userService.deleteUser(user_id);
    }

    public Location getVillageByName(String villageName) {
        LocationService locationService = new LocationService();
        return locationService.getVillageByName(villageName);
    }
    
    public String authenticateUser(String email, String password) {
        return userService.authenticateUser(email, password);
    }
    
    public String getProvinceByPersonId(String personId) {
        return userService.getProvinceByPersonId(personId);
    }
    public UUID authenticateAndGetUserId(String email, String password) {
        return userService.authenticateAndGetUserId(email, password);
    }
}
