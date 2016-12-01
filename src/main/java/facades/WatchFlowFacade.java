/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Department;
import entity.RedCrossLevel;
import entity.Resource;
import entity.StaffedEvent;
import entity.watches.ResourceWatch;
import entityconnection.EntityConnector;
import enums.Status;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel
 */
public class WatchFlowFacade {
    
    
    CoordinatorFacade cf;

    public WatchFlowFacade(){ 
     cf = new CoordinatorFacade();
    }
    
    

    /**
     * Gets all the StaffedEventWith the
     *
     * @param status
     * @param d
     * @return
     */
    public List<StaffedEvent> getAllStaffedEventsWithStatus(Status status, Department d) {
        EntityManager em = EntityConnector.getEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        List<StaffedEvent> allwithThatStatus;
        try {
            Query q = em.createQuery("Select s FROM StaffedEvent s where s.status = :status AND s.department = :dept ");
            q.setParameter("status", status);
            q.setParameter("dept", d);
            allwithThatStatus = q.getResultList();
        } catch (Exception e) {
            log.Log.writeErrorMessageToLog("Error in get all Staffed Events: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }

        return allwithThatStatus;

    }

    public List<RedCrossLevel> getRedCrossLevels() {
        EntityManager em = EntityConnector.getEntityManager();
        List<RedCrossLevel> levels;
        try {
            TypedQuery<RedCrossLevel> q1 = em.createQuery("select l from RedCrossLevel l", RedCrossLevel.class);
            levels = q1.getResultList();

        } catch (Exception e) {
            log.Log.writeToLog("Error in getting RedCrossLevels: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
        return levels;
    }

    /**
     * Updates Only the LevelsQuantity for an event. 
     * LevelsQuantity holds how many samarits and their level that
     * should be used for an event.
     * To make sure that nothing else is changed this method only takes
     * the id of the event, and loads the event from the database instead!
     *
     * @param eventId The ID of the Event
     * @param map Map that holds infomation about staff Quantity
     * @return The newly updated Entity
     */
    public StaffedEvent updateQuantityForEvent(Integer eventId, Map<String, Integer> map) throws Exception {
        EntityManager em = EntityConnector.getEntityManager();
        StaffedEvent event = null;
        try {
            TypedQuery<StaffedEvent> q1 = em.createQuery("Select e from StaffedEvent e where e.id =:eventid", StaffedEvent.class);
            q1.setParameter("eventid", eventId);
            event = q1.getSingleResult();

            em.getTransaction().begin();
            event.setLevelsQuantity(map);
            em.getTransaction().commit();
        } catch (Exception e) {
            log.Log.writeErrorMessageToLog("Error in Updated Quantity For Event: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
        return event;
    }

    /**
     * Updates only resources for an event.
     * Should remove all ResourceWatches asociated with the existing object if any
     * And also put in ResourceWatches for an new event
     * @param eventId
     * @param incommingResources
     * @return
     */
    public StaffedEvent updateResources(Integer eventId, List<Resource> incommingResources) throws Exception {
        EntityManager em = EntityConnector.getEntityManager();
        StaffedEvent event = null;
        try {
            TypedQuery<StaffedEvent> q1 = em.createQuery("Select e from StaffedEvent e where e.id =:eventid", StaffedEvent.class);
            q1.setParameter("eventid", eventId);
            event = q1.getSingleResult();
           
//   We need to remove all ResourceWatches from the "existing" Object.
//   And Add one for each Resource.
          removeResourceWatchesfromEvent(event, em);
          addNewResourceWatchesToEvent(eventId, incommingResources, em);
            em.getTransaction().begin();
            event.setResources(incommingResources);
            em.getTransaction().commit();
        } catch (Exception e) {
            log.Log.writeErrorMessageToLog("Error in Updated Quantity For Event: " + e.getMessage());
            throw e;
        }
        finally{
            em.close();
        }
        return event;
    }

    /*
    How to Deal with ResourceWatches??!?!?!?!!?!
     */
    private void removeResourceWatchesfromEvent(StaffedEvent event, EntityManager em) throws Exception {
        List<Resource> resourcesBeforeUpdate = event.getResources();
        if (resourcesBeforeUpdate == null || resourcesBeforeUpdate.isEmpty()) {
            return; // then there is no Resources assigned yet!
        }
        em.getTransaction().begin();
        for (Resource resource : resourcesBeforeUpdate) {
            cf.deleteResourceFromEvent(event.getId(), resource.getId());
        }
        em.getTransaction().commit();

    }

    private void addNewResourceWatchesToEvent(Integer eventId, List<Resource> incommingResources, EntityManager em) throws Exception {
        for (Resource incommingResource : incommingResources) {
            em.getTransaction().begin();
            cf.addResourceToEvent(eventId, incommingResource.getId());
            em.getTransaction().commit();
        }
    }
    /**
     * Updates the status of the Event.
     * @param eventId Id of the event to be updated
     * @param status The status that the Event should now have
     */
    public void updateStatusOfStaffedEvent(Integer eventId, Status status) {
        EntityManager em = EntityConnector.getEntityManager();
        try {
            TypedQuery<StaffedEvent> q1 = em.createQuery("Select e from StaffedEvent e where e.id =:eventid", StaffedEvent.class);
            q1.setParameter("eventid", eventId);
            StaffedEvent event = q1.getSingleResult();
            em.getTransaction().begin();
            event.setStatus(status);
            em.getTransaction().commit();
        } catch (Exception e) {
            log.Log.writeErrorMessageToLog("Eror in Updating status: " + e.getMessage());
            throw e;
        }
        finally{
            em.close();
        }
    }


    }
