package test;

import controller.BookController;
import controller.ShelfController;
import model.Book;
import model.Shelf;
import model.Book_status;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class BookControllerTest {

    @Test
    public void testSaveBookWithShelfAssignment() {
        Scanner scanner = new Scanner(System.in);
        BookController bookController = new BookController();
        ShelfController shelfController = new ShelfController();

        // Display available shelves based on category
        System.out.print("Enter the book category to display available shelves: ");
        String bookCategory = scanner.nextLine();
        List<Shelf> shelves = shelfController.getShelvesByCategory(bookCategory);

        if (shelves == null || shelves.isEmpty()) {
            System.out.println("No shelves found for this category.");
            return;
        }

        System.out.println("Available shelves in category '" + bookCategory + "':");
        for (int i = 0; i < shelves.size(); i++) {
            System.out.println((i + 1) + ". Shelf ID: " + shelves.get(i).getShelf_id() +
                               ", Stock: " + shelves.get(i).getAvailable_stock());
        }

        // Prompt the user to select a shelf
        System.out.print("Enter the number of the shelf to assign to the book: ");
        int shelfSelection = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        if (shelfSelection < 1 || shelfSelection > shelves.size()) {
            System.out.println("Invalid selection. Test terminated.");
            return;
        }
        Shelf selectedShelf = shelves.get(shelfSelection - 1);

        // Prompt for book details
        System.out.print("Enter ISBN Code for the book: ");
        String ISBNCode = scanner.nextLine();
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter publisher name: ");
        String publisherName = scanner.nextLine();
        System.out.print("Enter publication year (yyyy-MM-dd): ");
        String publicationYearString = scanner.nextLine();
        System.out.print("Enter book edition: ");
        int edition = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter book status (AVAILABLE, BORROWED, etc.): ");
        String statusString = scanner.nextLine();
        Book_status bookStatus = Book_status.valueOf(statusString.toUpperCase());

        // Create and save the Book
        Book book = new Book();
        book.setISBNCode(ISBNCode);
        book.setTitle(title);
        book.setPublisher_name(publisherName);
        book.setPublication_year(java.sql.Date.valueOf(publicationYearString)); // Convert string to Date
        book.setEdition(edition);
        book.setBook_status(bookStatus);
        book.setShelf(selectedShelf);

        bookController.saveBook(book);
        System.out.println("Book saved successfully and assigned to Shelf ID: " + selectedShelf.getShelf_id());
    }
}
