/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Event;
import entityconnection.EntityConnector;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author dennisschmock
 */
public class EventFacade {

    /**
     * The purpose of this method is to return a list of Events
     * @return
     */
    public List<Event> getEvents() {
        List<Event> events = null;

        EntityManager em = EntityConnector.getEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM Event e");
            events = q.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return events;
    }
    
    public Event createEvent(Event event){
        EntityManager em = EntityConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(event);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return event;
        
    }
    
    public Event getEvent(int id){
        EntityManager em = EntityConnector.getEntityManager();
        Event event = null;
        try {
            event = em.find(Event.class, id);
        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return event;
    }
}
