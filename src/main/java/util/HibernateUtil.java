package util;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    // Static block to build the SessionFactory when the class is loaded
    static {
        try {
            // Create the Configuration object
            Configuration configuration = new Configuration();

            // Set Hibernate properties programmatically
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "org.postgresql.Driver");
            properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/auca_library_db");
            properties.put(Environment.USER, "postgres");
            properties.put(Environment.PASS, "A$aprocky08");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

            // Optional Hibernate settings
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            properties.put(Environment.FORMAT_SQL, "true"); 
            
            // Connection pool settings using c3p0 (optional)
            properties.put(Environment.C3P0_MIN_SIZE, "5");
            properties.put(Environment.C3P0_MAX_SIZE, "20");
            properties.put(Environment.C3P0_TIMEOUT, "300");
            properties.put(Environment.C3P0_MAX_STATEMENTS, "50");
            properties.put(Environment.C3P0_IDLE_TEST_PERIOD, "3000");

            // Apply the properties to the configuration
            configuration.setProperties(properties);

         // Add the annotated entity classes
            configuration.addAnnotatedClass(model.User.class);
            configuration.addAnnotatedClass(model.Book.class);
            configuration.addAnnotatedClass(model.Borrower.class);
            configuration.addAnnotatedClass(model.Membership.class);
            configuration.addAnnotatedClass(model.MembershipType.class);
            configuration.addAnnotatedClass(model.Location.class);
            configuration.addAnnotatedClass(model.Room.class);
            configuration.addAnnotatedClass(model.Shelf.class);
            


            // Create Serv
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            // Build the SessionFactory
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            // Log the exception to know what went wrong
            System.err.println("SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Method to get the SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Method to shut down the SessionFactory
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
