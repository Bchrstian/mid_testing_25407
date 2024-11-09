package ui;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Main menu logic
        Scanner scanner = new Scanner(System.in);

        // Display welcome message
        System.out.println("WELCOME TO AUCA LIBRARY MANAGEMENT SYSTEM");
        System.out.println("Choose an option:");
        System.out.println("1. Select or register for membership");
        System.out.println("2. Borrow a book");
        System.out.println("3. See remaining books allowed");

        int choice = scanner.nextInt();

        // Handle user input and redirect to respective functionality
        switch (choice) {
            case 1:
                registerForMembership();
                break;
            case 2:
                borrowBook();
                break;
            case 3:
                showRemainingBooks();
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }

    private static void registerForMembership() {
        // Simulating user registration logic
        System.out.println("Registering for membership...");
        // Add your logic for registration here
    }

    private static void borrowBook() {
        // Simulate borrowing logic
        System.out.println("Please enter the book title:");
        Scanner scanner = new Scanner(System.in);
        String bookTitle = scanner.nextLine();
        System.out.println("Borrowing book: " + bookTitle);
    }

    private static void showRemainingBooks() {
        // Simulate showing membership and validation
        System.out.println("Displaying membership information...");
        // Example logic to show remaining book count
        int maxBooks = 5; // Example: assuming the user can borrow up to 5 books
        int borrowedBooks = 3; // Example: assuming the user has borrowed 3 books
        int remainingBooks = maxBooks - borrowedBooks;
        System.out.println("You are allowed to borrow " + remainingBooks + " more books.");
    }
}
