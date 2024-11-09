package test;

//In GetRoomWithFewBooksTest.java

import controller.RoomController;
import model.Room;
import org.junit.Test;

import static org.junit.Assert.*;

public class GetRoomWithFewBooksTest {

 @Test
 public void getRoomWithFewBooksTest() {
     // Create the controller
     RoomController roomController = new RoomController();

     // Call the method to get the room with the fewest books
     Room room = roomController.getRoomWithFewBooks();

     // Check if the room is retrieved
     assertNotNull("Room should not be null", room);

     // Print out the room details and the number of books in the room
     System.out.println("Room Code: " + room.getRoomCode());
     System.out.println("Number of Shelves: " + room.getShelves().size());
     
     // Print the details of each shelf and the number of books on each shelf
     room.getShelves().forEach(shelf -> {
         System.out.println("Shelf Code: " + shelf.getShelf_id() + ", Number of Books: " + shelf.getBook().size());
     });

     // Verify that the room has books (it should have at least 1 book)
     assertTrue("Room should have books", room.getShelves().stream().anyMatch(shelf -> shelf.getBook().size() > 0));
 }
}
