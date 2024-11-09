package service;

import model.Book;
import model.Book_status;
import model.Borrower;
import model.MembershipType;
import model.User;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BorrowerService {

    public void saveBorrower(Borrower borrower) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(borrower);
            transaction.commit();
            System.out.println("Borrower saved successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to save the borrower.");
        }
    }

    public Borrower getBorrowerById(UUID reservation_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Borrower.class, reservation_id);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve the borrower.");
            return null;
        }
    }

    public List<Borrower> getAllBorrowers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Borrower", Borrower.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve borrowers.");
            return null;
        }
    }

    public void updateBorrower(UUID reservation_id, Borrower updatedBorrower) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Borrower borrower = session.get(Borrower.class, reservation_id);
            if (borrower != null) {
                borrower.setUser(updatedBorrower.getUser());
                borrower.setBook(updatedBorrower.getBook());
                borrower.setDue_date(updatedBorrower.getDue_date());
                borrower.setReserve_date(updatedBorrower.getReserve_date());
                borrower.setReturn_date(updatedBorrower.getReturn_date());
                borrower.setLate_charge_fees(updatedBorrower.getLate_charge_fees());
                transaction.commit();
                System.out.println("Borrower updated successfully.");
            } else {
                System.err.println("Borrower not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to update the borrower.");
        }
    }

    public void deleteBorrower(UUID reservation_id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Borrower borrower = session.get(Borrower.class, reservation_id);
            if (borrower != null) {
                session.delete(borrower);
                transaction.commit();
                System.out.println("Borrower deleted successfully.");
            } else {
                System.err.println("Borrower not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to delete the borrower.");
        }
    }
    
    public String borrowBook(UUID userId, UUID bookId, Date reservedDate, Date dueDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the user and book
            User user = session.get(User.class, userId);
            Book book = session.get(Book.class, bookId);

            if (book == null || book.getBook_status() != Book_status.AVAILABLE) {
                return "The book is either not available or currently borrowed.";
            }

            // Check if user has reached borrowing limit
            int borrowedBooks = user.getBorrower().size();
            MembershipType membershipType = user.getMembership().get(0).getMembership_type();
            if (borrowedBooks >= membershipType.getMax_books()) {
                return "Borrowing limit reached for your membership type.";
            }

            // Check if the user has already borrowed this book
            Borrower existingRecord = getBorrowRecordByUserIdAndBookId(userId, bookId);
            if (existingRecord != null) {
                return "You have already borrowed this book.";
            }

            // Update book status and create a new borrower record
            book.setBook_status(Book_status.BORROWED);
            Borrower borrower = new Borrower();
            borrower.setUser(user);
            borrower.setBook(book);
            borrower.setReserve_date(reservedDate);
            borrower.setReturn_date(null);
            borrower.setDue_date(dueDate);
            
            session.save(borrower);
            
            transaction.commit();
            return "Book borrowed successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred during the borrowing process.";
        }
    }

    public boolean canBorrowMoreBooks(UUID userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            int borrowedBooks = user.getBorrower().size();
            MembershipType membershipType = user.getMembership().get(0).getMembership_type();

            return borrowedBooks < membershipType.getMax_books();
        }
    }
    
    public Borrower getBorrowRecordByUserIdAndBookId(UUID userId, UUID bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Query to get the Borrower record where the userId and bookId match
            String hql = "FROM Borrower b WHERE b.user.id = :userId AND b.book.id = :bookId";
            List<Borrower> borrowRecords = session.createQuery(hql, Borrower.class)
                                                  .setParameter("userId", userId)
                                                  .setParameter("bookId", bookId)
                                                  .getResultList();

            // If a record exists, return the first (and presumably only) one
            if (!borrowRecords.isEmpty()) {
                return borrowRecords.get(0); // Assuming one record should exist for this user and book combination
            } else {
                return null; // No record found
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve the borrow record.");
            return null;
        }
    }



}
