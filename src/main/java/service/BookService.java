package service;

import model.Book;
import model.Book_status;
import model.Room;
import model.Shelf;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class BookService {
	
	
	
	
	
    private RoomService roomService;  

    // Constructor
    public BookService() {
        this.roomService = new RoomService();  
    }

    // Save a new Book
    public void saveBook(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
            System.out.println("Book saved successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to save the book.");
        }
    }

    // Retrieve a Book by ID
    public Book getBookById(UUID bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, bookId);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve the book.");
            return null;
        }
    }

    // Retrieve all Books
    public List<Book> getAllBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Book", Book.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve books.");
            return null;
        }
    }

    // Update Book details
    public void updateBook(UUID bookId, Book updatedBook) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            if (book != null) {
                // Updating fields based on the model attributes
                book.setISBNCode(updatedBook.getISBNCode());
                book.setTitle(updatedBook.getTitle());
                book.setPublisher_name(updatedBook.getPublisher_name());
                book.setPublication_year(updatedBook.getPublication_year());
                book.setEdition(updatedBook.getEdition());
                book.setBook_status(updatedBook.getBook_status());
                book.setShelf(updatedBook.getShelf());       // Shelf association
                book.setBorrower(updatedBook.getBorrower()); // Borrower association
                
                transaction.commit();
                System.out.println("Book updated successfully.");
            } else {
                System.err.println("Book not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to update the book.");
        }
    }

    // Delete a Book by ID
    public void deleteBook(UUID bookId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            if (book != null) {
                session.delete(book);
                transaction.commit();
                System.out.println("Book deleted successfully.");
            } else {
                System.err.println("Book not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to delete the book.");
        }
    }
    
    public List<Book> getAvailableBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery("from Book where book_status = :status", Book.class);
            query.setParameter("status", Book_status.AVAILABLE);
            if(query.list().isEmpty())
            	System.out.println("No availabe books");
            return query.list();
            
        }
        
        
    }
    
    
    public int countBooksInRoom(String roomCode) {
        // Retrieve the room by its code
        List<Book> books = roomService.getBooksByCode(roomCode);
        
        if (books.isEmpty()) {
        	System.out.println("Booklist in Room is Empty");
            return 0;  // Return 0 if room does not exist
        }

        int bookCount = 0;
        
        // Iterate over shelves in the room and count books
        for (Book book: books) {
            bookCount ++;
        }

        return bookCount;  // Return the total book count
    }
    

}
