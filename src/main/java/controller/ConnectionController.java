package controller;

import org.hibernate.Session;
import util.HibernateUtil;

public class ConnectionController {

    public Session getConnection() {
        Session session = null;
        try {
           
            session = HibernateUtil.getSessionFactory().openSession();
            System.out.println("Connection established successfully.");
        } catch (Exception e) {
           
            System.err.println("Failed to establish connection. Error: " + e.getMessage());
            e.printStackTrace(); 
        }
        return session;
    }

    
    public void closeConnection(Session session) {
        if (session != null) {
            try {
                session.close();
                System.out.println("Session closed successfully.");
            } catch (Exception e) {
                System.err.println("Failed to close the session. Error: " + e.getMessage());
                e.printStackTrace(); 
            }
        }
    }
}
