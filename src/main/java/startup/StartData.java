/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startup;

import entity.Admin;
import entity.Department;
import entity.Event;
import entity.RedCrossLevel;
import entity.Samarit;
import entity.User;
import entity.User_Role;
import entityconnection.EntityConnector;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import log.Log;

/**
 *
 * @author danie
 */
public class StartData {
    
    public static void main(String[] args) {
        insertTestData();
    }
    public static void insertTestData() {
        Log.writeToLog("Inserting Test Users in database");
        EntityManager em = EntityConnector.getEntityManager();
        RedCrossLevel r1 = new RedCrossLevel("Samarit");
        RedCrossLevel r2 = new RedCrossLevel("Teamleder");
        RedCrossLevel r3 = new RedCrossLevel("Gæst");
        Department d = new Department();
        d.setNameOfDepartment("København");
        Event e = new Event();
            e.setName("Test Event");
            e.setStart(new Date(116, 5, 5, 10, 0));
            e.setEnd(new Date(116, 5, 6, 10, 0));
            e.setDepartment(d);
            d.getEvents().add(e);
        User samarit = new Samarit("sam", "test");
        User_Role userRole = new User_Role("User");
        d.addUser((Samarit)samarit);
        samarit.addRoleToUser(userRole);
        
        User admin = new Admin("admin","test");
        User_Role adminRole = new User_Role("Admin");
        admin.addRoleToUser(adminRole);

        User coordinator = new Samarit("coordinator", "test");
        User_Role coordinatorRole = new User_Role("Coordinator");
        User_Role departmentAdminRole = new User_Role("DepartmentAdmin");
        d.addUser((Samarit)coordinator);
        coordinator.addRoleToUser(departmentAdminRole);
        coordinator.addRoleToUser(userRole);
        coordinator.addRoleToUser(coordinatorRole);
    
        try {
            Log.writeToLog("Connecting to database");
           
            em = EntityConnector.getEntityManager();
            em.getTransaction().begin();
            em.persist(r1);
            em.persist(r2);
            em.persist(r3);
            em.persist(d);
            em.persist(e);
            em.persist(samarit);
            em.persist(admin);
            em.persist(coordinator);
            em.getTransaction().commit();
            Log.writeToLog("Inserted Test Users in database");
        } catch (Exception ex) {
            Log.writeToLog("Exception" + ex.getMessage());
        }
        finally{
            em.close();
        }
    }
    
    public static void insertRandomData(){
        String[] fName = {"Adam", "Allan", "Anders", "Brian", "Børge", "Claus", "Daniel", "Danni", "Dennis", "Egon", "Emil", "Fie", "Freja", "Grethe","Gorm"};
        String[] lName = {"Andersen","Jespersen","Jørgensen","Hansen","Thomsen","Gram","Hat","Stol","Green","Pind","Løkke","Nielsen","Flotnavn","Avn","Ravn","Havn","Barm"};
        String[] emailDomain = {"hotmail","gmail"};
        String[] emailEnd = {".com",".net",".dk"};
        EntityManager em = EntityConnector.getEntityManager();
        Query q = em.createQuery("Select d from Department d where (d.nameOfDepartment='København')");
        
        Department d = (Department) q.getSingleResult();
        ArrayList<User> randomTestUsers = new ArrayList();
        for (int i = 0; i < 50; i++) {
            String userFName = fName[((int)(Math.random()*fName.length))];
            String userLName = lName[((int)(Math.random()*lName.length))];
            String email = userFName+userLName+emailDomain[((int)(Math.random()*emailDomain.length))];
            Samarit s= new Samarit(email, userFName+"123");
            s.setFirstName(userFName);
            s.setFirstName(userLName);
            
            randomTestUsers.add(s);
        }
        for (User randomTestUser : randomTestUsers) {
            
        }
    }
}
