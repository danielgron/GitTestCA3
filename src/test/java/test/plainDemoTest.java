package test;

import entity.Department;
import entity.Event;
import entity.Samarit;
import entity.SamaritCalenderEvent;
import entity.User;
import entityconnection.EntityConnector;
import facades.CoordinatorFacade;
import facades.UserFacade;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
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
            TypedQuery qnew = em.createQuery("select u from User u", User.class);
            List<User> linew = qnew.getResultList();
            int numbersAfterInsert = linew.size();
             assertTrue(numbersBeforeInsert + 1 == numbersAfterInsert);
            
        }
        
        @Test
        public void checkAvalibilty(){
            EntityManager em = EntityConnector.getEntityManager();
            Samarit testSam = new Samarit("test2@gmail.com", "testingpassword");
            Department d = new Department();
            d.setNameOfDepartment("TestDepartment");
            d.addUser(testSam);
            testSam.addWatch(new SamaritCalenderEvent(testSam, new Event(), new Date(2001, 2, 5, 6, 0),new Date(2001, 2, 5, 10, 0), false));
            // Event with with start and end in between the watch marked
            Event e = new Event();
            e.setStart(new Date(2001, 5, 5, 10, 0));
            e.setEnd(new Date(2001, 5, 6, 10, 0));
            e.setDepartment(d);
            
            em.getTransaction().begin();
            em.persist(d);
            em.persist(e);
            em.persist(testSam);
            em.getTransaction().commit();
            
            List<Samarit> l = cf.getAvailableSamaritesFromEventId(1);
            System.out.println(testSam.getDepartment().getNameOfDepartment());
            System.out.println(l.size());
            System.out.println(e.getId());
             assertTrue(1 == 1);
            
        }
        
}
