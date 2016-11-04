package facades;

import entity.Admin;
import entity.Department;
import entity.Samarit;
import security.IUserFacade;
import entity.User;
import entity.User_Role;
import entityconnection.EntityConnector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import log.Log;
import security.IUser;
import security.PasswordStorage;
import startup.StartData;

public class UserFacade implements IUserFacade {

    /*When implementing your own database for this seed, you should NOT touch any of the classes in the security folder
    Make sure your new facade implements IUserFacade and keeps the name UserFacade, and that your Entity User class implements 
    IUser interface, then security should work "out of the box" with users and roles stored in your database */
    

    public UserFacade() {
        //Test Users
        if(isDatabaseUsersEmpty()){
        StartData.insertTestData();
        }
    }

    @Override
    public IUser getUserByUserId(String id) {
        EntityManager em = EntityConnector.getEntityManager();
        return em.find(User.class, id);
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    @Override
    public List<String> authenticateUser(String userName, String password) {
        try {
            Log.writeToLog("Authenticating user: "+userName);
            EntityManager em = EntityConnector.getEntityManager();
            TypedQuery<User> q = em.createQuery("select u from User u where u.email=:name",User.class);
            q.setParameter("name", userName);
            User user = q.getSingleResult();
            if(PasswordStorage.verifyPassword(password, user.getPassword())){
               return user.getRolesAsStrings();
            }
            else{
                return null;
            }
        } catch (Exception ex) {
            Log.writeToLog("Authenticating user failed: "+ex.getMessage());
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    

    private boolean isDatabaseUsersEmpty() {
        EntityManager em = EntityConnector.getEntityManager();
        Query q = em.createQuery("select u from User u ", User.class);
        List<User> us = q.getResultList();
        return us.isEmpty();
    }
    
    /**
     * Persist a new Role in the database, and returns it if
     * succes
     * @param role The name that will be used
     * @return The User_Role object if succes and null if not.
     */
    public User_Role persistRole(String role) {
        EntityManager em = EntityConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            User_Role userRole = new User_Role(role);
            em.persist(userRole);
            em.getTransaction().commit();
            return userRole; 
        } catch (Exception e) {
            //error
            return null;
        }
        finally{
        em.close();
        }
    }
    
    /**
     * Tries to find the role that matches the parameter and return it
     * Returns null if it can't find it
     * @param role The Role as a String
     * @return The Role as a User_Role object
     */
    public User_Role findRole(String role){
        EntityManager em = EntityConnector.getEntityManager();
        try {
            User_Role u = (User_Role) em.createNamedQuery("User_Role.findbyroleName")
                    .setParameter("name", role)
                    .getSingleResult();
            return u;
        } catch (Exception e) {
            System.out.println("ERROR!" + e);
            return null;
        }
        finally{
            em.close();
        }
    }
    
}
