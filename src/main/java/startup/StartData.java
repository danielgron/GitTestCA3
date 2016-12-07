/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startup;

import entity.Contact;
import entity.user.Admin;
import entity.Department;
import entity.Event;
import entity.Invoice;
import entity.RedCrossLevel;
import entity.Request;
import entity.Resource;
import entity.user.Samarit;
import entity.StaffedEvent;
import entity.user.User;
import entity.user.User_Role;
import entity.WatchFunction;
import entity.watches.SamaritFunctionsOnWatch;
import entity.watches.SamaritOccupied;
import entity.watches.SamaritWatch;
import entityconnection.EntityConnector;
import enums.RequestStatus;
import enums.Status;
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
        Persistence.generateSchema("pu_local", null);
        StartData sd = new StartData();
        insertTestData();
        sd.insertRandomData();
        createStaffedEvent();
for (int i = 0; i < 10; i++) {
            sd.testRequest();
        }
        
    }


    public static void insertTestData() {
        Log.writeToLog("Inserting Test Users in database");
        EntityManager em = EntityConnector.getEntityManager();
        RedCrossLevel r1 = new RedCrossLevel("Samarit");
        RedCrossLevel r2 = new RedCrossLevel("Teamleder");
        RedCrossLevel r3 = new RedCrossLevel("Gæst");
        Department d = new Department();
        d.setNameOfDepartment("København");
        WatchFunction f1 = new WatchFunction("Chaffør", d);
        WatchFunction f2 = new WatchFunction("Chaffør med Trailer", d);
        WatchFunction f3 = new WatchFunction("VagtLeder", d);
        Event e = new Event();
        e.setName("Test Event");
        e.setStart(new Date(116, 5, 5, 10, 0));
        e.setEnd(new Date(116, 5, 6, 10, 0));
        e.setDepartment(d);
        d.getEvents().add(e);
        User samarit = new Samarit("sam", "test");
        User_Role userRole = new User_Role("User");
        d.addUser((Samarit) samarit);
        samarit.addRoleToUser(userRole);

        User admin = new Admin("admin", "test");
        User_Role adminRole = new User_Role("Admin");
        admin.addRoleToUser(adminRole);

        User coordinator = new Samarit("coordinator", "test");
        User_Role coordinatorRole = new User_Role("Coordinator");
        User_Role departmentAdminRole = new User_Role("DepartmentAdmin");
        d.addUser((Samarit) coordinator);
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
            em.persist(f1);
            em.persist(f2);
            em.persist(f3);
            em.persist(samarit);
            em.persist(admin);
            em.persist(coordinator);
            em.getTransaction().commit();
            Log.writeToLog("Inserted Test Users in database");
        } catch (Exception ex) {
            Log.writeToLog("Exception" + ex.getMessage());
        } finally {
            em.close();
        }
    }
    
    public void insertRandomData(){
        
        
        
        EntityManager em = EntityConnector.getEntityManager();
        Query q = em.createQuery("Select d from Department d where (d.nameOfDepartment='København')");
        Query q2 = em.createQuery("Select ur from User_Role ur where (ur.roleName='User')");
        Query q3 = em.createQuery("Select r from RedCrossLevel r", RedCrossLevel.class);
        Query q4 = em.createQuery("Select f from WatchFunction f", WatchFunction.class);
        List<RedCrossLevel> listofAllRedCrossLevels = q3.getResultList();
        List<WatchFunction> listofAllWatchFunctions = q4.getResultList();
        Department d;
        try {
            d = (Department) q.getSingleResult();
        } catch (NoResultException ex) {
            d = new Department();
            d.setNameOfDepartment("København");
        }
        User_Role userRole = (User_Role) q2.getSingleResult();
        ArrayList<User> randomTestUsers = new ArrayList();
        Log.writeToLog("Test Data adding random users");
        for (int i = 0; i < 50; i++) {
            String userFName = randomFName();
            String userLName = randomLName();
            String email = generateEmail(userFName,userLName);
            String address = randomAddress();
            Samarit s= new Samarit(email, userFName+"123");
            s.setFirstName(userFName);
            s.setLastName(userLName);
            s.setDepartment(d);
            s.setAdresse(address);
            s.setPhone("88888888");
            s.addRoleToUser(userRole);
            s.addRedCrossLevelToSamarit(listofAllRedCrossLevels.get(ThreadLocalRandom.current().nextInt(0, listofAllRedCrossLevels.size())));
            s.addFunctionToSamarit(listofAllWatchFunctions.get(ThreadLocalRandom.current().nextInt(0, listofAllRedCrossLevels.size())));
            for (int j = 0; j < 50; j++) {
                s.addNotAvail(ocupySam(s));
            }

            randomTestUsers.add(s);
        }
        Log.writeToLog("Test Data adding random events");
        for (int i = 0; i < 50; i++) {
            Event e = testEvent();
            d.addEvent(e);
            e.setDepartment(d);
        }
        for (int i = 1; i < 4; i++) {
            Resource res = new Resource();
            res.setName("Bil #" + i);
            d.addResource(res);
            res.setDepartment(d);
        }
        for (int i = 1; i < 4; i++) {
            Resource res = new Resource();
            res.setName("Telefon #" + i);
            d.addResource(res);
            res.setDepartment(d);
        }
        Resource res = new Resource();
            res.setName("Hjertestarter");
            d.addResource(res);
            res.setDepartment(d);
        try {
            em.getTransaction().begin();
            for (User randomTestUser : randomTestUsers) {
                em.persist(randomTestUser);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public String randomAddress(){
        String[] adr1 = {"Store ","Lille ","Øvre ","Nedre ","Jyske","","","","","","",""};
        String[] adr2 = {"Randers","Morgen","Blomster","Paradis","Fiol","Banan","Vin","Herre","Roskilde","Jylland","Bøge","Ege"};
        String[] adr3 = {"vej","gade"," Hovedgade"," Landevej","stræde","stien","pladsen"};
        
        return adr1[(int)(Math.random()*adr1.length)]+adr2[(int)(Math.random()*adr2.length)]+adr3[(int)(Math.random()*adr3.length)]+ " " + (int)(Math.random()*200);
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
    public String eventName(){
        String name;
        String[] soccerName = {"Brøndby", "Randers", "B93", "Lyngby", "Porto", "Nørresundby", "Hobro", "AGF", "Ikast", "AAB", "AB", "Silkeborg"};
        String[] firmafest = {"Novo", "Politiken", "CPHBUSINESS", "Microsoft", "ProfilOptik","DSB","Socialdemokraterne","PostNord"};
        String[] type = {"Julefrokost", "Påskefrokost", "Firmafest", "Teambuilding","Koncert"};
        if (Math.random() < 0.5) {
            name = "FCK vs " + soccerName[(int) (Math.random() * soccerName.length)];
        } else {
            name = firmafest[(int) (Math.random() * firmafest.length)] + " " + type[(int) (Math.random() * type.length)];
        }
        return name;
    }

    public Event testEvent() {
        Event e = new Event();
        String name = eventName();
        e.setName(name);
        int duration = (int) (Math.random() * 10);
        Date dStart = randomDate();
        Date dEnd = new Date(116, dStart.getMonth(), dStart.getDate(), dStart.getHours() + duration, 0);
        e.setStart(dStart);
        e.setEnd(dEnd);
        return e;
    }

    public Date randomDate() {
        int month = (int) (Math.random() * 12);
        int day = (int) (Math.random() * 30);
        int hour = (int) (Math.random() * 12) + 12;
        return new Date(116, month, day, hour, 0);
    }
    public String randomFName(){
        String[] fName = {"Adam", "Allan", "Anders", "Brian", "Børge", "Claus", "Daniel", "Danni", "Dennis", "Egon", "Emil", "Fie", "Freja", "Grethe","Gorm","Henning","Ib","Ida","Jens","Klaus","Kasper","Kenneth","Abel","Jarmo","Sonny","Cher","Dreng","Lotus","Dan","Lars","Mathilde","Mads","Morten","Michael"};
        return (fName[((int)(Math.random()*fName.length))]);
    }
    public String randomLName(){
        String[] lName = {"Andersen","Jespersen","Jørgensen","Hansen","Thomsen","Gram","Hat","Stol","Green","Pind","Løkke","Nielsen","Flotnavn","Avn","Ravn","Havn","Barry","Heintze","Gønge","Von Jarmo","Tømrer","Forsørensen","Pilatus","Ort","Rohde","Lund","Greve","Vad","Dam","Bondo","Kjærsgård","Gade","Hassan","Michael","Juel"};
        return (lName[((int)(Math.random()*lName.length))]);
    }
    public String generateEmail(String fName, String lName){
        String[] emailDomain = {"hotmail","gmail","jubii","yahoo"};
        String[] emailEnd = {".com",".net",".dk"};
        return(fName+lName+(int)(Math.random()*10000)+"@"+emailDomain[((int)(Math.random()*emailDomain.length))]+emailEnd[((int)(Math.random()*emailEnd.length))]);
    }
    
    public Request testRequest(){
        
        EntityManager em = EntityConnector.getEntityManager();
        try {
            
            Department d = em.find(Department.class, "København");
        
        Request r = new Request();
        String[] ageGroups = {"Voksne", "Børn", "Blandet"};
        String[] venue = {"Parken","Forum"};
        String[] catering = {"Rekvirant","Ingen","RK"};
        String address = randomAddress();
        Date start = randomDate();
        long open = start.getTime()-(1000*60*60);
        Date doorsOpen = new Date(open);
        Date end = new Date(start.getTime()+(1000*60*60*5));
        
        
        r.setEventName(eventName());
        r.setDepartment(d);
        r.setAgegroup(ageGroups[(int)(Math.random()*ageGroups.length)]);
        r.setEventDate(start);
        r.setEventstart(start);
        r.setDoorsopen(doorsOpen);
        r.setWatchStart(start);
        r.setComments("Please bring bandaid");
        r.setEventend(end);
        r.setVenue(venue[(int)(Math.random()*venue.length)]);
        r.setCatering(catering[(int)(Math.random()*catering.length)]);
        r.setZip(2100);
        r.setStretcherTeam(Math.random()>0.50);
        r.setResponseTeam(Math.random()>0.50);
        r.setEmergencyOffice(Math.random()>0.9);
        r.setStreet(address);
        r.setContact(randomContact());
        r.setMedics(Math.random()>0.9);
        r.setNumberGuests((int)(Math.random()*100));
        r.setAmbulance(Math.random()>0.9);
        r.setInvoice(randomInvoice());
        r.setRequestStatus(RequestStatus.RECIEVED);
        em.getTransaction().begin();
        em.persist(r);
        em.getTransaction().commit();
        }
        finally{
                em.close();
                }
        return null;
    }
    
    public Invoice randomInvoice(){
        Invoice i = new Invoice();
        i.setCvr("87654321");
        i.setName("John Doe");
        i.setStreet(randomAddress());
        i.setZip("2200");
        i.setCompany("");
        
        return i;
    }
    public Contact randomContact(){
        Contact c = new Contact();
        String fname = randomFName();
        String lname = randomLName();
        c.setName(fname+" "+lname);
        c.setMail(generateEmail(fname,lname));
        return c;
    }
    private static void createStaffedEvent() {
        for (int i = 0; i < 3; i++) {
EntityManager em = EntityConnector.getEntityManager();
           
            
        try {
            Department d = em.find(Department.class, "København");
            StaffedEvent event = new StaffedEvent(Status.ReadyToCreate, new Date(), new Date(), false, "Svømme Stævne", "Flot", d);
            Query q1 = em.createQuery("Select l from RedCrossLevel l");
            List<RedCrossLevel> allRedCrossLevels = q1.getResultList();
            event.initilazeLinkedMap(allRedCrossLevels);
em.getTransaction().begin();
            em.persist(event);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println("EXECPTION IN CREATED STAFFED EVENT!! " + e.getMessage() );
    }
}
    }
}
