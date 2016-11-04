//package test;
//
//import entity.Department;
//import entity.Samarit;
//import entity.User;
//import entityconnection.EntityConnector;
//import facades.CoordinatorFacade;
//import facades.UserFacade;
//import java.io.IOException;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import log.Log;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import rest.Coordinator;
//
///**
// *
// * @author lam
// */
//public class plainDemoTest {
//    
//    CoordinatorFacade cf;
//    UserFacade uf;
//
//    public plainDemoTest() {
//        cf  = new CoordinatorFacade();
//        uf  = new UserFacade(); // creates Users in the database!
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//        EntityConnector.setPersistenceUnit("pu_test");
//        EntityConnector.createEntityManagerFactory();
//        try {
//            Log.startLogFile();
//        } catch (IOException ex) {
//            System.out.println("Could Not Start the Log File!" + ex.getMessage());
//        }
//        Log.writeToLog("Server has Started!");
//    
//}
//
//        @Before
//        public void setUp() {
//        }
//  
//         @Test
//        public void saveSamaritTest(){
//            EntityManager em = EntityConnector.getEntityManager();
//            TypedQuery q = em.createQuery("select u from User u", User.class);
//            List<User> li = q.getResultList();
//            System.out.println("Users in list: " + li.size());
//            int numbersBeforeInsert = li.size();
//            
//            Samarit testSam = new Samarit("test@gmail.com", "testingpassword");
//            // if any database Constrais are made make sure to add
//            Department d = em.find(Department.class, "København"); // must match the inserted value
//            testSam.setDepartment(d);
//            cf.addNewSamarit(testSam);
//            TypedQuery qnew = em.createQuery("select u from User u", User.class);
//            List<User> linew = q.getResultList();
//            int numbersAfterInsert = linew.size();
//             assertTrue(numbersBeforeInsert + 1 == numbersAfterInsert);
//            
//        }
//        
//}
