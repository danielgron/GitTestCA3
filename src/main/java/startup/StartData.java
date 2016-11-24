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
import entity.watches.SamaritOccupied;
import entityconnection.EntityConnector;
import exceptions.DateNullException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import log.Log;

/**
 *
 * @author danie
 */
public class StartData {
    
    public static void main(String[] args) {
        StartData sd = new StartData();
        Persistence.generateSchema("pu_local", null);
        insertTestData();
        sd.insertRandomData();
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
    
    public void insertRandomData(){
        String[] fName = {"Adam", "Allan", "Anders", "Brian", "Børge", "Claus", "Daniel", "Danni", "Dennis", "Egon", "Emil", "Fie", "Freja", "Grethe","Gorm","Henning","Ib","Ida","Jens","Klaus","Kasper","Kenneth","Abel","Jarmo","Sonny","Cher","Dreng","Lotus","Dan","Lars","Mathilde","Mads","Han"};
        String[] lName = {"Andersen","Jespersen","Jørgensen","Hansen","Thomsen","Gram","Hat","Stol","Green","Pind","Løkke","Nielsen","Flotnavn","Avn","Ravn","Havn","Barm","Heintze","Gønge","Von Jarmo","Tømrer","Forsørensen","Pilatus","Ort","Rohde","Lund","Greve","Vad","Dam","Bondo","Kjærsgård","Gade","Hassan","Schmidt","Thorning","Klausen","Mbutu","Merkel","Solo","Simpson"};
        String[] emailDomain = {"hotmail","gmail","jubii","yahoo"};
        String[] emailEnd = {".com",".net",".dk"};
        String[] adr1 = {"Store ","Lille ","","","","","","","",""};
        String[] adr2 = {" Randers",""};
        String[] adr3 = {"vej","gade"," Hovedgade"," Landevej"};
        
        EntityManager em = EntityConnector.getEntityManager();
        Query q = em.createQuery("Select d from Department d where (d.nameOfDepartment='København')");
        Query q2 = em.createQuery("Select ur from User_Role ur where (ur.roleName='User')");
        Query q3 = em.createQuery("Select r from RedCrossLevel r", RedCrossLevel.class);
        List<RedCrossLevel> listofAllRedCrossLevels = q3.getResultList();
        Department d ;
        try{
            d = (Department) q.getSingleResult();
        }
        catch (NoResultException ex) {
            d= new Department();
            d.setNameOfDepartment("København");
        }
        User_Role userRole = (User_Role) q2.getSingleResult();
        ArrayList<User> randomTestUsers = new ArrayList();
        Log.writeToLog("Test Data adding random users");
        for (int i = 0; i < 50; i++) {
            String userFName = fName[((int)(Math.random()*fName.length))];
            String userLName = lName[((int)(Math.random()*lName.length))];
            String email = userFName+userLName+(int)(Math.random()*10000)+"@"+emailDomain[((int)(Math.random()*emailDomain.length))]+emailEnd[((int)(Math.random()*emailEnd.length))];
            Samarit s= new Samarit(email, userFName+"123");
            s.setFirstName(userFName);
            s.setLastName(userLName);
            s.setDepartment(d);
            s.setPhone("88888888");
            s.addRoleToUser(userRole);
            s.addRedCrossLevelToSamarit(listofAllRedCrossLevels.get(ThreadLocalRandom.current().nextInt(0, listofAllRedCrossLevels.size() - 1)));
            for (int j = 0; j < 50; j++) {
            s.addNotAvail(ocupySam(s));
            }

            randomTestUsers.add(s);
        }
        Log.writeToLog("Test Data adding random events");
        for (int i = 0; i < 50; i++) {
            Event e =testEvent();
            d.addEvent(e);
            e.setDepartment(d);
        }
        try{
        em.getTransaction().begin();
        for (User randomTestUser : randomTestUsers) {
            em.persist(randomTestUser);
        }
        em.getTransaction().commit();
        }
        finally{
            em.close();
        }
    }
    
    public SamaritOccupied ocupySam(Samarit sam){
        SamaritOccupied so=null;
        try{
        int hours = (int)(Math.random()*10);
        Date start = randomDate();
        
        long endTime = start.getTime()+(1000*60*60*(hours+1));
        Date end = new Date(endTime);
        if (start.getTime()==end.getTime()) return null;
        so = new SamaritOccupied(sam,start,end,false);
        }
        catch(DateNullException ex){
            Log.writeToLog("Test Data tried to add a null start date");
        }
        return so;
    }
    
    public Event testEvent(){
        String[] soccerName ={"Brøndby","Randers","B93","Lyngby","Porto","Nørresundby","Hobro","AGF","Ikast","AAB","AB","Silkeborg"};
        String[] firmafest = {"Novo","Politiken","CPHBUSINESS","Microsoft","ProfilOptik"};
        String[] type = {"Julefrokost","Påskefrokost","Firmafest","Teambuilding"};
        Event e = new Event();
        String name;
        if (Math.random()<0.5) name= "FCK vs"+soccerName[(int)(Math.random()*soccerName.length)];
        else name= firmafest[(int)(Math.random()*firmafest.length)]+" "+type[(int)(Math.random()*type.length)];
        e.setName(name);
        int duration  = (int)(Math.random()*10);
        Date dStart = randomDate();
        Date dEnd = new Date(116, dStart.getMonth(), dStart.getDate(), dStart.getHours()+duration, 0);
        e.setStart(dStart);
        e.setEnd(dEnd);
        return e;
    }
    
    public Date randomDate(){
        int month = (int)(Math.random()*12);
        int day = (int)(Math.random()*30);
        int hour = (int)(Math.random()*12)+12;
        return new Date(116, month, day, hour, 0);
    }
}
