package controller;

import model.Shelf;
import service.ShelfService;

import java.util.List;
import java.util.UUID;

public class ShelfController {

    private final ShelfService shelfService;

    public ShelfController() {
        this.shelfService = new ShelfService();
    }

    public void saveShelf(Shelf shelf) {
        shelfService.saveShelf(shelf);
    }

    public Shelf getShelfById(UUID shelfId) {
        return shelfService.getShelfById(shelfId);
    }

    public List<Shelf> getAllShelves() {
        return shelfService.getAllShelves();
    }

    public void updateShelf(UUID shelfId, Shelf updatedShelf) {
        shelfService.updateShelf(shelfId, updatedShelf);
    }

    public void deleteShelf(UUID shelfId) {
        shelfService.deleteShelf(shelfId);
    }
    public List<Shelf> getShelvesByCategory(String category) {
        return shelfService.getShelvesByCategory(category);
    }
}
