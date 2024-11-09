package controller;

import model.Room;
import service.RoomService;

import java.util.List;
import java.util.UUID;

public class RoomController {

    private final RoomService roomService;

    public RoomController() {
        this.roomService = new RoomService();
    }

    public void saveRoom(Room room) {
        roomService.saveRoom(room);
    }

    public Room getRoomById(UUID roomId) {
        return roomService.getRoomById(roomId);
    }

    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    public void updateRoom(UUID roomId, Room updatedRoom) {
        roomService.updateRoom(roomId, updatedRoom);
    }

    public void deleteRoom(UUID roomId) {
        roomService.deleteRoom(roomId);
    }
    public Room getRoomWithFewBooks() {
        return roomService.getRoomWithFewBooks(); 
    }
}
