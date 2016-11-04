/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityconnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import log.Log;

/**
 *
 * @author Daniel
 */
public class EntityConnector {
    
    private static EntityManagerFactory emf;
    private static String persistenceUnit = "pu_local";
    
    private EntityConnector(){
        Log.writeToLog("Creating EntityConnectorObject");
    }
    
    public static EntityManager getEntityManager(){
        if(emf == null){
            emf = Persistence.createEntityManagerFactory(persistenceUnit);
            Log.writeToLog("Entity Manager Factory created");
        }
        return emf.createEntityManager();
    }
    
    public static void createEntityManagerFactory(){
        emf = Persistence.createEntityManagerFactory(persistenceUnit);
    }

    public static void setPersistenceUnit(String persistenceUnit) {
        EntityConnector.persistenceUnit = persistenceUnit;
    }
}
