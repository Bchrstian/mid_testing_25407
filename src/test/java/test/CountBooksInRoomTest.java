package test;

import controller.BookController;
import org.junit.Test;

import java.util.Scanner;

public class CountBooksInRoomTest {  // Renamed the test class

    @Test
    public void countBooksInRoomTest() {  // Renamed the test method
        Scanner scanner = new Scanner(System.in);
        BookController bookController = new BookController();  // Assuming constructor doesn't need arguments

        // Prompt user for room code
        System.out.print("Enter room code: ");
        String roomCode = scanner.nextLine();

        // Get the count of books in the room
        int bookCount = bookController.countBooksInRoom(roomCode);

        // Output the result
        System.out.println("Number of books in room " + roomCode + ": " + bookCount);
    }
}
