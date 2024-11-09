package service;

import model.MembershipType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MembershipTypeService {

    public void saveMembershipType(MembershipType membershipType) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(membershipType);
            transaction.commit();
            System.out.println("Membership Type saved successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to save the Membership Type.");
        }
    }

    public MembershipType getMembershipTypeById(UUID membership_type_id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MembershipType.class, membership_type_id);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve the Membership Type.");
            return null;
        }
    }

    public List<MembershipType> getAllMembershipTypes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from MembershipType", MembershipType.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve Membership Types.");
            return null;
        }
    }

    public void updateMembershipType(UUID membership_type_id, MembershipType updatedMembershipType) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            MembershipType membershipType = session.get(MembershipType.class, membership_type_id);
            if (membershipType != null) {
                membershipType.setMembership_name(updatedMembershipType.getMembership_name());
                membershipType.setMax_books(updatedMembershipType.getMax_books());
                membershipType.setPrice(updatedMembershipType.getPrice());
                transaction.commit();
                System.out.println("Membership Type updated successfully.");
            } else {
                System.err.println("Membership Type not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to update the Membership Type.");
        }
    }

    public void deleteMembershipType(UUID membership_type_id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            MembershipType membershipType = session.get(MembershipType.class, membership_type_id);
            if (membershipType != null) {
                session.delete(membershipType);
                transaction.commit();
                System.out.println("Membership Type deleted successfully.");
            } else {
                System.err.println("Membership Type not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.println("Failed to delete the Membership Type.");
        }
    }
    
 // MembershipTypeService.java
    public List<String> listMembershipTypes() {
        List<MembershipType> types = getAllMembershipTypes();
        if (types != null && !types.isEmpty()) {
            return types.stream()
                        .map(MembershipType::getMembership_name)
                        .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
