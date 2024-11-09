package service;

import model.Book;
import model.Room;
import model.Shelf;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomService {

    public void saveRoom(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(room);
            transaction.commit();
            System.out.println("Room saved successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to save the Room.");
        }
    }

    public Room getRoomById(UUID roomId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Room.class, roomId);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve the Room.");
            return null;
        }
    }

    public List<Room> getAllRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Room", Room.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve rooms.");
            return null;
        }
    }

    public void updateRoom(UUID roomId, Room updatedRoom) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Room room = session.get(Room.class, roomId);
            if (room != null) {
                room.setRoomCode(updatedRoom.getRoomCode());
                // If there are shelves to update, you can handle that here.
                transaction.commit();
                System.out.println("Room updated successfully.");
            } else {
                System.err.println("Room not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to update the Room.");
        }
    }

    public void deleteRoom(UUID roomId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Room room = session.get(Room.class, roomId);
            if (room != null) {
                session.delete(room);
                transaction.commit();
                System.out.println("Room deleted successfully.");
            } else {
                System.err.println("Room not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to delete the Room.");
        }
    }
    
    public List<Book> getBooksByCode(String roomCode) {
        List<Book> booklist = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Querying by roomCode
        	return session.createQuery(
                    "SELECT b FROM Book b " +
                    "JOIN b.shelf s " +
                    "JOIN s.room r " +
                    "WHERE r.roomCode = :roomCode", Book.class)
                .setParameter("roomCode", roomCode)
                .list();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve the books by room code.");
        }
        return booklist;
    }

 



    public Room getRoomWithFewBooks() {
        Room roomWithFewBooks = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Query rooms with their shelves and the count of books
            List<Object[]> result = session.createQuery(
                    "SELECT r, COUNT(b) FROM Room r " +
                    "JOIN r.shelves s " +
                    "JOIN s.book b " +
                    "GROUP BY r.roomId " +
                    "ORDER BY COUNT(b) ASC", Object[].class)
                    .getResultList();  

         
            if (!result.isEmpty()) {
                Object[] row = result.get(0);
                roomWithFewBooks = (Room) row[0];
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve room with few books.");
        }

        return roomWithFewBooks;  
    }


}
