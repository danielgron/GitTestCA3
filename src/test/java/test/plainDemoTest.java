package test;

import entity.Department;
import entity.Event;
import entity.Samarit;
import entity.watches.SamaritOccupied;
import entity.User;
import entityconnection.EntityConnector;
import facades.CoordinatorFacade;
import facades.UserFacade;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import log.Log;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rest.Coordinator;

/**
 *
 * @author lam
 */
public class plainDemoTest {
    
    CoordinatorFacade cf;
    UserFacade uf;

    public plainDemoTest() {
        cf  = new CoordinatorFacade();
        uf  = new UserFacade(); // creates Users in the database!
    }

    @BeforeClass
    public static void setUpClass() {
        Persistence.generateSchema("pu_test", null);
        EntityConnector.setPersistenceUnit("pu_test");
        EntityConnector.createEntityManagerFactory();
        try {
            Log.startLogFile();
        } catch (IOException ex) {
            System.out.println("Could Not Start the Log File!" + ex.getMessage());
        }
        Log.writeToLog("Server has Started!");
    
}

        @Before
        public void setUp() {
            Persistence.generateSchema("pu_test", null);
        EntityConnector.setPersistenceUnit("pu_test");
        EntityConnector.createEntityManagerFactory();
        }
  
         @Test
        public void saveSamaritTest(){
            EntityManager em = EntityConnector.getEntityManager();
            TypedQuery q = em.createQuery("select u from User u", User.class);
            List<User> li = q.getResultList();
            System.out.println("Users in list: " + li.size());
            int numbersBeforeInsert = li.size();
            
            Samarit testSam = new Samarit("test@gmail.com", "testingpassword");
            // if any database Constrais are made make sure to add
            TypedQuery<Department> query = em.createQuery("select d from Department d", Department.class);
            Department d = query.getSingleResult();
            testSam.setDepartment(d);
            cf.addNewSamarit(testSam);
            int numbersAfterInsert;
            try{
            TypedQuery qnew = em.createQuery("select u from User u", User.class);
            List<User> linew = qnew.getResultList();
            numbersAfterInsert = linew.size();
            }
            finally{
                em.close();
            }
             assertTrue(numbersBeforeInsert + 1 == numbersAfterInsert);
            
        }
        
        @Test
        public void checkAvalibiltyNoConflict(){
            EntityManager em = EntityConnector.getEntityManager();
            Samarit testSam = new Samarit("test2@gmail.com", "testingpassword");
            Department d = new Department();
            d.setNameOfDepartment("TestDepartment");
            d.addUser(testSam);
            testSam.addNotAvail(new SamaritOccupied(testSam, new Event(), new Date(101, 2, 5, 6, 0),new Date(101, 2, 5, 10, 0), false));
            // Event with with start and end in between the watch marked
            Event e = new Event();
            e.setName("Test Event");
            e.setStart(new Date(101, 5, 5, 10, 0));
            e.setEnd(new Date(101, 5, 6, 10, 0));
            e.setDepartment(d);
            d.getEvents().add(e);
            
            int numBefore;
            Query q = em.createQuery("SELECT e FROM Event e");
            List<Event> events = q.getResultList();
            for (Event event : events) {
                System.out.println(event.getName());
            }
            numBefore=events.size();
            try{
            em.getTransaction().begin();
            em.persist(d);
            em.persist(e);
            em.persist(testSam);
            em.getTransaction().commit();
            }
            finally{
                em.close();
            }
            List<Samarit> l = cf.getAvailableSamaritesFromEventId(e.getId());
            System.out.println(testSam.getDepartment().getNameOfDepartment());
            System.out.println(l.size());
            System.out.println(e.getId());
             //assertEquals((int)numBefore+1,(int)e.getId());
             assertEquals(1,l.size());
        }
        
        @Test
        public void checkAvalibiltyConflict(){
            EntityManager em = EntityConnector.getEntityManager();
            Samarit testSam = new Samarit("test3@gmail.com", "testingpassword");
            Department d = new Department();
            d.setNameOfDepartment("TestDepartment2");
            d.addUser(testSam);
            testSam.addNotAvail(new SamaritOccupied(testSam, new Event(), new Date(101, 2, 5, 6, 0),new Date(101, 7, 5, 10, 0), false));
            // Event with with start and end in between the watch marked
            Event e = new Event();
            e.setName("Test Event");
            e.setStart(new Date(101, 5, 5, 10, 0));
            e.setEnd(new Date(101, 5, 6, 10, 0));
            e.setDepartment(d);
            d.getEvents().add(e);
            int numBefore;
            Query q = em.createQuery("SELECT e FROM Event e where e.name IS NOT NULL");
            
            List<Event> events = q.getResultList();
            for (Event event : events) {
                System.out.println(event.getName());
            }
            numBefore=events.size();
            try{
            em.getTransaction().begin();
            em.persist(d);
            em.persist(e);
            em.persist(testSam);
            em.getTransaction().commit();
            }
            finally{
                em.close();
            }
            List<Samarit> l = cf.getAvailableSamaritesFromEventId(e.getId());
            System.out.println(testSam.getDepartment().getNameOfDepartment());
            System.out.println(l.size());
            System.out.println(e.getId());
             //assertEquals((int)numBefore+1,(int)e.getId());
             assertEquals(0,l.size());
        }
}
