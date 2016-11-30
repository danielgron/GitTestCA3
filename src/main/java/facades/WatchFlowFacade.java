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

    /**
     * Gets all the StaffedEventWith the
     *
     * @param status
     * @param d
     * @return
     */
    public List<StaffedEvent> getAllStaffedEventsWithStatus(Status status, Department d) {
        EntityManager em = EntityConnector.getEntityManager();
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
     * Updates two things for an event. 1.How many samarits and their level that
     * should be used for an event 2.All resources that should be added to an
     * event. To make sure that nothing else is changed this method only takes
     * the id of the event, and loads the event from the database instead!
     *
     * @param eventId The ID of the Event
     * @param map Map that holds infomation about staff Quantity
     * @param resources List of Resources that has been assigned to Event
     * @return The newly updated Entity
     */
    public StaffedEvent updateQuantityForEvent(Integer eventId, Map<String, Integer> map, List<Resource> resources) throws Exception {
        EntityManager em = EntityConnector.getEntityManager();
        StaffedEvent event = null;
        try {
            TypedQuery<StaffedEvent> q1 = em.createQuery("Select e from StaffedEvent e where e.id =:eventid", StaffedEvent.class);
            q1.setParameter("eventid", eventId);
            event = q1.getSingleResult();

            // We need to remove all ResourceWatches from the "Excisting" Object.
//        removeResourceWatches(event, em);
            em.getTransaction().begin();
            event.setLevelsQuantity(map);
            event.setResources(resources);
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
    private void removeResourceWatches(StaffedEvent event, EntityManager em) throws Exception {
        List<ResourceWatch> watches = event.getResourceWatchs();
        if(watches == null || watches.isEmpty()){
            return; // then there is no Resources assigned yet!
        }
        em.getTransaction().begin();
        for (ResourceWatch watch : watches) {
            em.remove(watch);
        }
        em.getTransaction().commit();
        
        /*
          List<ResourceWatch> eventResourceWatchs = event.getResourceWatchs();
          for (Resource res : resources) {
            ResourceWatch resWatch = new ResourceWatch();
            resWatch.setEvent(event);
            resWatch.setResource(res);
            resWatch
            eventResourceWatchs.add(resWatch);
            em.persist(resWatch);
        }
        */
    }

}
