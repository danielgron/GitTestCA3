/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.User;
import entityconnection.EntityConnector;
import javax.persistence.EntityManager;

/**
 *
 * @author danie
 */
public class CoordinatorFacade {
    
    
    
    public User addNewUser(User u){
        
        
        EntityManager em = EntityConnector.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        }
        finally{
            em.close();
        }
        return u;
    }
}
