package test;

import model.Book;
import model.Borrower;
import model.User;
import service.UserService;
import service.BookService;
import service.BorrowerService;

import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

public class BorrowerControllerTest {

	private final UserService userService = new UserService();
	private final BookService bookService = new BookService();
	private final BorrowerService borrowerService = new BorrowerService();

	@Test
	public void testBorrowBook() {
		Scanner scanner = new Scanner(System.in);

		// Step 1: Prompt user for email and password for authentication
		System.out.print("Enter email: ");
		String email = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		// Authenticate user and retrieve their userId
		UUID userId = userService.authenticateAndGetUserId(email, password);
		assertNotNull("User authentication failed or user not found.", userId);

		// Step 2: Retrieve the list of available books
		List<Book> availableBooks = bookService.getAvailableBooks();
		assertFalse("No books available for borrowing.", availableBooks.isEmpty());

		// Display the available books to the user
		System.out.println("Available Books:");
		for (Book book : availableBooks) {
			int i = 0;
			System.out.println((i + 1) + ". " + book.getTitle());
		}
		System.out.print("Select a book to borrow by entering the corresponding number: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character

		// Validate the user's choice
		if (choice < 1 || choice > availableBooks.size()) {
			fail("Invalid book selection.");
		}

		Book chosenBook = availableBooks.get(choice - 1);

		// Set the reserve date as the current date (date the book is borrowed)
		Date reserveDate = new Date();

		// Prompt user for the return date
//		Date returnDate = null;
//		while (returnDate == null) {
//			System.out.print("Enter the return date (yyyy-MM-dd): ");
//			String returnDateString = scanner.nextLine();
//
//			// Parse the return date using the custom method
//			returnDate = parseDate(returnDateString);
//			if (returnDate == null) {
//				System.out.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
//			}
//		}

		// Calculate the due date (7 days after the reserve date)
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(reserveDate);
		calendar.add(Calendar.DATE, 7);
		Date dueDate = calendar.getTime();

		// Step 3: Borrow the selected book
		String borrowResult = borrowerService.borrowBook(userId, chosenBook.getBook_id(), reserveDate, dueDate);
		assertEquals("Book borrowed successfully.", borrowResult);

		// Step 4: Retrieve and verify the borrowing record
		Borrower borrowRecord = borrowerService.getBorrowRecordByUserIdAndBookId(userId, chosenBook.getBook_id());
		assertNotNull("Borrowing record not found.", borrowRecord);

		// Verify that the dates and fine are correctly set
		assertEquals("Borrow date not set correctly.", reserveDate, borrowRecord.getReserve_date());
		assertEquals("Due date not set correctly.", dueDate, borrowRecord.getDue_date());
		assertEquals("Fine should be 0 by default.", 0, borrowRecord.getLate_charge_fees(), 0);
	}

	// Helper method to parse date string input by the user (yyyy-MM-dd)
	private Date parseDate(String dateStr) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			fail("Invalid date format.");
			return null; // Return null in case of failure
		}
	}
}
