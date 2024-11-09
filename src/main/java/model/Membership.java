package model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "membership_id", updatable = false, nullable = false)
    private UUID membership_id;

    @ManyToOne
    @JoinColumn(name = "membership_type_id", nullable = false)
    private MembershipType membership_type;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_status", nullable = false)
    private Status membership_status;

    @Column(name = "registration_date", nullable = false)
    private Date registration_date;

    @Column(name = "expiring_date")
    private Date expiring_date;

    @Column(name = "fine")
    private int fine;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Default constructor
    public Membership() {}

    public UUID getMembership_id() {
        return membership_id;
    }

    public void setMembership_id(UUID membership_id) {
        this.membership_id = membership_id;
    }

    public MembershipType getMembership_type() {
        return membership_type;
    }

    public void setMembership_type(MembershipType membership_type) {
        this.membership_type = membership_type;
    }

    public Status getMembership_status() {
        return membership_status;
    }

    public void setMembership_status(Status membership_status) {
        this.membership_status = membership_status;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public Date getExpiring_date() {
        return expiring_date;
    }

    public void setExpiring_date(Date expiring_date) {
        this.expiring_date = expiring_date;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
