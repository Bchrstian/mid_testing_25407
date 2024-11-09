package service;

import model.Shelf;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class ShelfService {

    public void saveShelf(Shelf shelf) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(shelf);
            transaction.commit();
            System.out.println("Shelf saved successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to save the Shelf.");
        }
    }

    public Shelf getShelfById(UUID shelfId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Shelf.class, shelfId);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve the Shelf.");
            return null;
        }
    }

    public List<Shelf> getAllShelves() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Shelf", Shelf.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve Shelves.");
            return null;
        }
    }

    public void updateShelf(UUID shelfId, Shelf updatedShelf) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Shelf shelf = session.get(Shelf.class, shelfId);
            if (shelf != null) {
                shelf.setBook_category(updatedShelf.getBook_category());
                shelf.setRoom(updatedShelf.getRoom());
                shelf.setAvailable_stock(updatedShelf.getAvailable_stock());
                shelf.setBorrowed_number(updatedShelf.getBorrowed_number());
                shelf.setInitial_stock(updatedShelf.getInitial_stock());
                // Additional updates for book relationships or other fields can be handled here.
                
                transaction.commit();
                System.out.println("Shelf updated successfully.");
            } else {
                System.err.println("Shelf not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to update the Shelf.");
        }
    }

    public void deleteShelf(UUID shelfId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Shelf shelf = session.get(Shelf.class, shelfId);
            if (shelf != null) {
                session.delete(shelf);
                transaction.commit();
                System.out.println("Shelf deleted successfully.");
            } else {
                System.err.println("Shelf not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to delete the Shelf.");
        }
    }
    
    public List<Shelf> getShelvesByCategory(String category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Shelf where book_category = :category", Shelf.class)
                          .setParameter("category", category)
                          .list();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve shelves by category.");
            return null;
        }
    }
}
