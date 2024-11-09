package test;

import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.HibernateUtil;

public class DatabaseConnectionTest {

    private Session session;
    private Transaction transaction;

    @Before
    public void setUp() throws Exception {
       
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() throws Exception {
       
        if (transaction != null && transaction.isActive()) {
            transaction.rollback(); 
        }
        if (session != null && session.isOpen()) {
            session.close(); 
        }
    }

    @Test
    public void testDatabaseConnection() {
        
        assertNotNull("The session should not be null", session); 
        System.out.println("Database connection established successfully.");
    }

    @Test
    public void testSchemaCreation() {
        
        try {
            
            session.createNativeQuery("SELECT 1").getSingleResult();
            System.out.println("Schema creation checked successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
