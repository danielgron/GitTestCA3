package test;

import entity.Department;
import entity.Event;
import entity.StaffedEvent;
import entity.user.Samarit;
import entity.watches.SamaritOccupied;
import entity.user.User;
import entityconnection.EntityConnector;
import enums.Status;
import exceptions.DateNullException;
import facades.CoordinatorFacade;
import facades.UserFacade;
import facades.WatchFlowFacade;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import log.Log;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import rest.CoordinatorService;
import startup.StartData;

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
            // this is already done? This is just slowing down the 
            // test proces???!?!?!!?!?
//            Persistence.generateSchema("pu_test", null);
//        EntityConnector.setPersistenceUnit("pu_test"); 
//        EntityConnector.createEntityManagerFactory();
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
        @Ignore
        public void checkAvalibiltyNoConflict(){
            EntityManager em = EntityConnector.getEntityManager();
            Samarit testSam = new Samarit("test2@gmail.com", "testingpassword");
            Department d = new Department();
            d.setNameOfDepartment("TestDepartment");
            d.addUser(testSam);
        try {
            testSam.addNotAvail(new SamaritOccupied(testSam, new Date(101, 2, 5, 6, 0),new Date(101, 2, 5, 10, 0), false));
        } catch (DateNullException ex) {
            Logger.getLogger(plainDemoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        @Ignore
        public void checkAvalibiltyConflict(){
            EntityManager em = EntityConnector.getEntityManager();
            Samarit testSam = new Samarit("test3@gmail.com", "testingpassword");
            Department d = new Department();
            d.setNameOfDepartment("TestDepartment2");
            d.addUser(testSam);
        try {
            testSam.addNotAvail(new SamaritOccupied(testSam, new Date(101, 2, 5, 6, 0),new Date(101, 7, 5, 10, 0), false));
        } catch (DateNullException ex) {
            Logger.getLogger(plainDemoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        /*
        Test when we put an "all day occupied" object in the database for
        one Samarit, that the Samarit is registered as not avalible.
        */
          @Test
          @Ignore
        public void checkAvalibeConflictAllDay() throws DateNullException{
            EntityManager em = EntityConnector.getEntityManager();
            TypedQuery<Samarit> q1 = em.createQuery("Select s from Samarit s", Samarit.class);
            List<Samarit> listallSamarit = q1.getResultList();
            Samarit firstSam = listallSamarit.get(0);
            Samarit secondSam = listallSamarit.get(1);
            SamaritOccupied g = new SamaritOccupied(firstSam, new Date(), null, true); //Sets the occupied to all day
            
            Event e = new Event(new Date(), new Date(), false, "testEvent", "test", firstSam.getDepartment());
            
            em.getTransaction().begin();
            em.persist(g);
            em.persist(e);
            em.getTransaction().commit();
            
            List<Samarit> allAvaibledforEvent = cf.getAvailableSamaritesFromEventId(e.getId());
            
           assertTrue(!allAvaibledforEvent.contains(firstSam));
           assertTrue(allAvaibledforEvent.contains(secondSam));
            
        }
        
        @Test
        public void testAddComment(){
            EntityManager em = EntityConnector.getEntityManager();
            String testComment = "This is how we party";
            Department d = em.find(Department.class, "København");
            StaffedEvent event = new StaffedEvent(Status.Pending, new Date(), new Date(), true, "Test", "Test", d);
            em.getTransaction().begin();
            em.persist(event);
            em.getTransaction().commit();
            WatchFlowFacade wff = new WatchFlowFacade();
            wff.updateCoordinatorComment(event.getId(), testComment);
            EntityManager newEm = EntityConnector.getEntityManager();
            StaffedEvent updatedEvent = newEm.find(StaffedEvent.class, event.getId());
            assertTrue(updatedEvent.getCoordinatorcomment().equals(testComment));
        }
}
