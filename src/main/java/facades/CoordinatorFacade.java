/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Samarit;
import entity.User;
import entityconnection.EntityConnector;
import javax.persistence.EntityManager;
import log.Log;

/**
 *
 * @author danie
 */
public class CoordinatorFacade {
    
    
    
    public Samarit addNewSamarit(Samarit s){
        
        Log.writeToLog("Adding new Samarit");
        EntityManager em = EntityConnector.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
            Log.writeToLog("New samarit added");
        }
        finally{
            em.close();
        }
        return s;
    }
}
