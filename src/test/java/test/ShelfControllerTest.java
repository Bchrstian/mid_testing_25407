package test;

import controller.RoomController;
import controller.ShelfController;
import model.Room;
import model.Shelf;

import org.junit.Test;

import java.util.List;
import java.util.Scanner;

public class ShelfControllerTest {

    @Test
    public void testSaveShelfWithRoomAssignment() {
        Scanner scanner = new Scanner(System.in);
        ShelfController shelfController = new ShelfController();
        RoomController roomController = new RoomController();

        // Display available rooms for assignment
        System.out.println("Available rooms:");
        List<Room> rooms = roomController.getAllRooms();
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ". " + rooms.get(i).getRoomCode());
        }

        // Prompt the user to select a room
        System.out.print("Enter the number of the room to assign to the shelf: ");
        int roomSelection = scanner.nextInt();
        scanner.nextLine(); 

        
        if (roomSelection < 1 || roomSelection > rooms.size()) {
            System.out.println("Invalid selection. Test terminated.");
            return;
        }
        Room selectedRoom = rooms.get(roomSelection - 1);

        // Prompt for Shelf details
        System.out.print("Enter the book category for the shelf: ");
        String bookCategory = scanner.nextLine();
        System.out.print("Enter available stock for the shelf: ");
        int availableStock = scanner.nextInt();
        System.out.print("Enter the initial stock of books for the shelf: ");
        int initialStock = scanner.nextInt();

        // Create and save the Shelf
        Shelf shelf = new Shelf();
        shelf.setBook_category(bookCategory);  
        shelf.setAvailable_stock(availableStock);
        shelf.setBorrowed_number(0);  
        shelf.setInitial_stock(initialStock);
        shelf.setRoom(selectedRoom);

        shelfController.saveShelf(shelf);
        System.out.println("Shelf saved successfully in Room: " + selectedRoom.getRoomCode());
    }
}
