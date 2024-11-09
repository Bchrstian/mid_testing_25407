package test;

import controller.RoomController;
import model.Room;

import org.junit.Test;

import java.util.Scanner;

public class RoomControllerTest {

    @Test
    public void testSaveRoomWithInput() {
        Scanner scanner = new Scanner(System.in);
        RoomController roomController = new RoomController();

        // Prompt for Room details
        System.out.print("Enter Room Code: ");
        String roomCode = scanner.nextLine();

        // Create and save the Room
        Room room = new Room();
        room.setRoomCode(roomCode);

        roomController.saveRoom(room);
        System.out.println("Room saved successfully with Room Code: " + room.getRoomCode());
    }
}
