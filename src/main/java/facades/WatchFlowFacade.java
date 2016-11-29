/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Department;
import entity.RedCrossLevel;
import entity.StaffedEvent;
import entityconnection.EntityConnector;
import enums.Status;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel
 */
public class WatchFlowFacade {
    
    /**
     * Gets all the StaffedEventWith the
     * @param status
     * @param d
     * @return
     */
    public List<StaffedEvent> getAllStaffedEventsWithStatus(Status status, Department d){
        EntityManager em = EntityConnector.getEntityManager();
        List<StaffedEvent> allwithThatStatus;
        try {
            Query q = em.createQuery("Select s FROM StaffedEvent s where s.status = :status AND s.department = :dept ");
            q.setParameter("status", status);
            q.setParameter("dept", d);
            allwithThatStatus = q.getResultList();
        } catch (Exception e) {
            log.Log.writeErrorMessageToLog("Error in get all Staffed Events: " + e.getMessage() );
            throw e;
        }
        finally{
            em.close();
        }
        
        return allwithThatStatus;
        
        
        
        
    }

    public List<RedCrossLevel> getRedCrossLevels() {
        EntityManager em = EntityConnector.getEntityManager();
        List<RedCrossLevel> levels;
        try{
            TypedQuery<RedCrossLevel> q1 = em.createQuery("select l from RedCrossLevel l", RedCrossLevel.class);
            levels = q1.getResultList();
            
        }
        catch(Exception e){
            log.Log.writeToLog("Error in getting RedCrossLevels: " + e.getMessage());
            throw e;
        }
        finally{
            em.close();
        }
        return levels;
    }
    
}
