package model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Borrower")
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id")
    private UUID reservation_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "due_date")
    private Date due_date;

    @Column(name = "reserve_date")
    private Date reserve_date;

    @Column(name = "return_date")
    private Date return_date;

    @Column(name = "late_charge_fees")
    private int late_charge_fees;

    public Borrower() {
        // Default constructor
    }

    public UUID getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(UUID reservation_id) {
        this.reservation_id = reservation_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Date getReserve_date() {
        return reserve_date;
    }

    public void setReserve_date(Date reserve_date) {
        this.reserve_date = reserve_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public int getLate_charge_fees() {
        return late_charge_fees;
    }

    public void setLate_charge_fees(int late_charge_fees) {
        this.late_charge_fees = late_charge_fees;
    }
}
