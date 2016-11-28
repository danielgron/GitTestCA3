/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Department;
import entity.StaffedEvent;
import entityconnection.EntityConnector;
import enums.Status;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Daniel
 */
public class WatchFlowFacade {
    
    public List<StaffedEvent> getAllStaffedEventsWithStatus(Status status){
        EntityManager em = EntityConnector.getEntityManager();
        List<StaffedEvent> allwithThatStatus;
        try {
            Query q = em.createQuery("Select s FROM StaffedEvent s where s.status = :status AND s.department = :dept ");
            q.setParameter("status", status);
            Department d = em.find(Department.class, "København");
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
    
}
