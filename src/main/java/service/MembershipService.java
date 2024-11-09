package service;

import model.Membership;
import model.MembershipType;
import model.Status;
import model.User;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MembershipService {
	
    public Date calculateExpiryDate(Date registrationDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(registrationDate);
        calendar.add(Calendar.DAY_OF_YEAR, 14);  // Adding 14 days
        return calendar.getTime();
    }

    public void saveMembership(Membership membership) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(membership);
            transaction.commit();
            System.out.println("Membership saved successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to save the membership.");
        }
    }

    public Membership getMembershipById(UUID membership_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Membership.class, membership_id);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve the membership.");
            return null;
        }
    }

    public List<Membership> getAllMemberships() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Membership", Membership.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve memberships.");
            return null;
        }
    }

    public void updateMembership(UUID membership_id, Membership updatedMembership) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Membership membership = session.get(Membership.class, membership_id);
            if (membership != null) {
                membership.setMembership_type(updatedMembership.getMembership_type());
                membership.setMembership_status(updatedMembership.getMembership_status());
                membership.setRegistration_date(updatedMembership.getRegistration_date());
                membership.setExpiring_date(updatedMembership.getExpiring_date());
                membership.setFine(updatedMembership.getFine());
                membership.setUser(updatedMembership.getUser());
                transaction.commit();
                System.out.println("Membership updated successfully.");
            } else {
                System.err.println("Membership not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to update the membership.");
        }
    }

    public void deleteMembership(UUID membership_id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Membership membership = session.get(Membership.class, membership_id);
            if (membership != null) {
                session.delete(membership);
                transaction.commit();
                System.out.println("Membership deleted successfully.");
            } else {
                System.err.println("Membership not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to delete the membership.");
        }
    }
    
    public void registerMembership(UUID userId, UUID membershipTypeId, Date registrationDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = session.get(User.class, userId);
            MembershipType membershipType = session.get(MembershipType.class, membershipTypeId);

            if (user != null && membershipType != null) {
                Membership membership = new Membership();
                membership.setUser(user);
                membership.setMembership_type(membershipType);
                membership.setRegistration_date(registrationDate);

                // Set expiry date 1 month from registration
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(registrationDate);
                calendar.add(Calendar.MONTH, 1);
                membership.setExpiring_date(calendar.getTime());

                membership.setMembership_status(Status.PENDING); // Assuming ACTIVE as initial status
                session.save(membership);

                transaction.commit();
                System.out.println("Membership registered successfully.");
            } else {
                System.err.println("User or Membership Type not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to register membership.");
        }
    }

}
