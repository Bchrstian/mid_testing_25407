package service;

import model.User;
import ui.Main;
import model.Location; // Import the Location model
import model.Location_type;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.persistence.NoResultException;
import org.hibernate.query.Query;

import util.HibernateUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public class UserService {

	public void saveUser(User user, String villageName) {
	    LocationService locationService = new LocationService();

	    // Check if the specified village exists and get its ID
	    Location village = locationService.getVillageByName(villageName);
	    if (village == null) {
	        System.err.println("No village found named: " + villageName);
	        return;
	    }

	    // Set the location (village) for the user
	    user.setLocation(village);

	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        
	        // Save user with correct location
	        session.save(user);
	        transaction.commit();
	        System.out.println("User saved successfully.");
	        
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	        System.err.println("Failed to save the user.");
	    }
	}

    public User getUserById(UUID user_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, user_id);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve the user.");
            return null;
        }
    }

    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve users.");
            return null;
        }
    }

    public void updateUser(UUID user_id, User updatedUser) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, user_id);
            if (user != null) {
                user.setUser_id(updatedUser.getUser_id());
                user.setUser_name(updatedUser.getUser_name());
                user.setPassword(updatedUser.getPassword());
                user.setRole(updatedUser.getRole());
                user.setBorrower(updatedUser.getBorrower());
                user.setMembership(updatedUser.getMembership());
                user.setLocation(updatedUser.getLocation()); // Ensure the updated location is set
                transaction.commit();
                System.out.println("User updated successfully.");
            } else {
                System.err.println("User not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to update the user.");
        }
    }

    public void deleteUser(UUID user_id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, user_id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                System.out.println("User deleted successfully.");
            } else {
                System.err.println("User not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to delete the user.");
        }
    }
    public String authenticateUser(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User where email = :email", User.class);
            query.setParameter("email", email);

            User user;
            try {
                user = query.getSingleResult();
            } catch (NoResultException e) {
                return "User not found, please check your email and try again.";
            }

            // Compare hashed input password with stored hashed password
            String hashedInputPassword = hashPassword(password);
            if (hashedInputPassword.equals(user.getPassword())) {
                // Display welcome message instead of opening main menu
                System.out.println("Welcome to AUCA Library Management System.");
                return null;
            } else {
                return "Password incorrect, please try again.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred during authentication.";
        }
    }


    
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
    
    
    public String getProvinceByPersonId(String personId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Get the user by person_id
            User user = session.createQuery("FROM User WHERE person_id = :personId", User.class)
                               .setParameter("personId", personId)
                               .uniqueResult();

            if (user != null) {
                Location location = user.getLocation();
                // Traverse parent locations to find the province
                while (location != null && location.getLocationType() != Location_type.PROVINCE) {
                    location = location.getParentLocation();
                }
                // Return the province name
                return (location != null) ? location.getLocationName() : null;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve province for the person.");
            return null;
        }
    }
 // UserService.java
    public UUID authenticateAndGetUserId(String email, String password) {
        String authenticationResult = authenticateUser(email, password);
        if (authenticationResult == null) { // Successful login
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Query<User> query = session.createQuery("from User where email = :email", User.class);
                query.setParameter("email", email);
                User user = query.getSingleResult();
                return user.getUser_id();
            } catch (NoResultException e) {
                System.err.println("User not found.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(authenticationResult); // Output error if any
        return null; // Authentication failed
    }

}
