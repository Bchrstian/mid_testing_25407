package controller;

import model.Borrower;
import service.BorrowerService;

import java.util.List;
import java.util.UUID;

public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController() {
        this.borrowerService = new BorrowerService();
    }

    public void saveBorrower(Borrower borrower) {
        borrowerService.saveBorrower(borrower);
    }

    public Borrower getBorrowerById(UUID reservation_id) {
        return borrowerService.getBorrowerById(reservation_id);
    }

    public List<Borrower> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    public void updateBorrower(UUID reservation_id, Borrower updatedBorrower) {
        borrowerService.updateBorrower(reservation_id, updatedBorrower);
    }

    public void deleteBorrower(UUID reservation_id) {
        borrowerService.deleteBorrower(reservation_id);
    }
}
