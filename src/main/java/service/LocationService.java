package service;

import model.Location;
import model.Location_type;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class LocationService {

    public Location findOrCreateLocation(String locationCode, String locationName, Location_type type, Location parent) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Check if location with the same name and type already exists
            List<Location> existingLocations = session.createQuery(
                    "FROM Location WHERE locationName = :locationName AND locationType = :locationType", Location.class)
                    .setParameter("locationName", locationName)
                    .setParameter("locationType", type)
                    .getResultList();

            if (!existingLocations.isEmpty()) {
                // Return the found location
                return existingLocations.get(0);
            }

            // Create a new Location if no duplicate found
            Location location = new Location();
            location.setLocationCode(locationCode);
            location.setLocationName(locationName);
            location.setLocationType(type);
            location.setParentLocation(parent);  // Set the parent location

            session.save(location);
            transaction.commit();
            return location;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public Location getLocationById(UUID locationId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Location.class, locationId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Location getLocationByName(String locationName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Location WHERE locationName = :locationName AND locationType = :locationType", Location.class)
                    .setParameter("locationName", locationName)
                    .setParameter("locationType", Location_type.VILLAGE)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveLocation(Location location) {
        executeInTransaction(session -> session.save(location));
    }

    public void createLocation(String locationCode, String locationName, Location_type locationType, UUID parentId) {
        Location location = new Location();
        location.setLocationCode(locationCode);
        location.setLocationName(locationName);
        location.setLocationType(locationType);

        if (parentId != null) {
            Location parentLocation = getLocationById(parentId);
            if (parentLocation != null) {
                System.out.println("Parent location found: " + parentLocation.getLocationName());
                location.setParentLocation(parentLocation);
                parentLocation.addChildLocation(location); // Ensure bidirectional relationship
            } else {
                System.err.println("Parent location not found for ID: " + parentId);
                return;
            }
        }

        saveLocation(location);
        System.out.println("Location created: " + location.getLocationName() + 
                           " with parent: " + 
                           (location.getParentLocation() != null ? location.getParentLocation().getLocationName() : "None"));
    }

    public List<Location> getAllLocations() {
        return executeInSession(session -> session.createQuery("from Location", Location.class).list());
    }

    public List<Location> getAllProvinces() {
        return executeInSession(session -> session.createQuery("FROM Location WHERE locationType = :type", Location.class)
                                                  .setParameter("type", Location_type.PROVINCE)
                                                  .list());
    }

    public List<Location> getAllDistricts() {
        return executeInSession(session -> session.createQuery("FROM Location WHERE locationType = :type", Location.class)
                                                  .setParameter("type", Location_type.DISTRICT)
                                                  .list());
    }

    public List<Location> getAllSectors(UUID districtId) {
        return executeInSession(session -> session.createQuery("FROM Location WHERE locationType = :locationType AND parentLocation.locationId = :parentId", Location.class)
                                                  .setParameter("locationType", Location_type.SECTOR)
                                                  .setParameter("parentId", districtId)
                                                  .list());
    }

    public List<Location> getAllCells(UUID sectorId) {
        return executeInSession(session -> session.createQuery("FROM Location WHERE locationType = :locationType AND parentLocation.locationId = :parentId", Location.class)
                                                  .setParameter("locationType", Location_type.CELL)
                                                  .setParameter("parentId", sectorId)
                                                  .list());
    }

    public List<Location> getAllVillages(UUID cellId) {
        return executeInSession(session -> session.createQuery("FROM Location WHERE locationType = :type AND parentLocation.id = :cellId", Location.class)
                                                  .setParameter("type", Location_type.VILLAGE)
                                                  .setParameter("cellId", cellId)
                                                  .list());
    }

    public void updateLocation(UUID locationId, Location updatedLocation) {
        executeInTransaction(session -> {
            Location location = session.get(Location.class, locationId);
            if (location != null) {
                location.setLocationCode(updatedLocation.getLocationCode());
                location.setLocationName(updatedLocation.getLocationName());
                location.setLocationType(updatedLocation.getLocationType());
            } else {
                System.err.println("Location not found.");
            }
        });
    }

    public void deleteLocation(UUID locationId) {
        executeInTransaction(session -> {
            Location location = session.get(Location.class, locationId);
            if (location != null) {
                session.delete(location);
                System.out.println("Location deleted successfully.");
            } else {
                System.err.println("Location not found.");
            }
        });
    }

    public Location getProvinceByVillage(String villageName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Location village = session.createQuery("FROM Location WHERE locationName = :villageName AND locationType = :locationType", Location.class)
                                      .setParameter("villageName", villageName)
                                      .setParameter("locationType", Location_type.VILLAGE)
                                      .uniqueResult();
            if (village != null) {
                Location currentLocation = village;
                while (currentLocation != null && currentLocation.getLocationType() != Location_type.PROVINCE) {
                    currentLocation = currentLocation.getParentLocation();
                }
                return currentLocation;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve the Province for the village.");
            return null;
        }
    }

    public Location getVillageByName(String villageName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Location where locationName = :locationName AND locationType = :locationType", Location.class)
                          .setParameter("locationName", villageName)
                          .setParameter("locationType", Location_type.VILLAGE)
                          .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve village.");
            return null;
        }
    }

    public boolean doesVillageExist(String villageName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Location location = session.createQuery("FROM Location WHERE locationName = :villageName AND locationType = :villageType", Location.class)
                                       .setParameter("villageName", villageName)
                                       .setParameter("villageType", Location_type.VILLAGE)
                                       .uniqueResult();
            return location != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void executeInTransaction(TransactionalOperation operation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            operation.execute(session);
            session.flush();  // Ensure changes are immediately written to the database
            transaction.commit();
            System.out.println("Operation executed successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to execute the operation.");
        }
    }
    



    private <T> T executeInSession(SessionOperation<T> operation) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return operation.execute(session);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to execute the session operation.");
            return null; 
        }
    }

    @FunctionalInterface
    private interface TransactionalOperation {
        void execute(Session session);
    }

    @FunctionalInterface
    private interface SessionOperation<T> {
        T execute(Session session);
    }
}
