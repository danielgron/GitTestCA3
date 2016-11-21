/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Event;
import entity.Samarit;
import entity.SamaritCalenderEvent;
import entity.User;
import entityconnection.EntityConnector;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import util.DateUtils;

/**
 *
 * @author dennisschmock
 */
public class WatchFacade {

    public WatchFacade() {
        EntityManager em = EntityConnector.getEntityManager();


    }

    public List<SamaritCalenderEvent> getWatches() {
        EntityManager em = EntityConnector.getEntityManager();
        List<SamaritCalenderEvent> watches = null;
        try {
            Query q = em.createQuery("SELECT w FROM SamaritCalenderEvent w");
            watches = q.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return watches;
    }

    public List<SamaritCalenderEvent> getWatchesForUser(String userName) {
        EntityManager em = EntityConnector.getEntityManager();
        List<SamaritCalenderEvent> watches = null;
        try {
            User samarit = em.find(Samarit.class, userName);
            Query q = em.createNamedQuery("SamaritCalenderEvent.findByUserName");
            q.setParameter("userName", userName);
            watches = q.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return watches;
    }

    public SamaritCalenderEvent getWatch(int id) {
        EntityManager em = EntityConnector.getEntityManager();
        SamaritCalenderEvent watch = null;
        try {
            watch = em.find(SamaritCalenderEvent.class, id);

        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        return watch;
    }

    public SamaritCalenderEvent updateWatch(SamaritCalenderEvent watch) {
        EntityManager em = EntityConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(watch);
            em.getTransaction().commit();

        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return watch;
    }

    public SamaritCalenderEvent deleteWatch(Integer id) {
        EntityManager em = EntityConnector.getEntityManager();
        SamaritCalenderEvent watch = null;
        try {
            watch = em.find(SamaritCalenderEvent.class, id);
            em.getTransaction().begin();
            em.remove(watch);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return watch;
    }

    public SamaritCalenderEvent addWatch(SamaritCalenderEvent watch, String email, Integer eventId) {
        EntityManager em = EntityConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            if (email != null) {
                Samarit samarit = em.find(Samarit.class, email);
                samarit.addWatch(watch);
                em.merge(samarit);
            }
            if (eventId != null) {
                Event event = em.find(Event.class, eventId);

            }
            em.persist(watch);
            em.getTransaction().commit();

        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return watch;
    }

    public SamaritCalenderEvent addUnavailForWatch(SamaritCalenderEvent watch) {
        EntityManager em = EntityConnector.getEntityManager();
                if (watch==null){
                    return null;
                }
        Samarit sm = null;
        try {
            em.getTransaction().begin();
            if (watch.getSamarit().getUserName() != null) {
                sm = em.find(Samarit.class, "coordinator");
            }
            watch.setSamarit(sm);
            em.persist(watch);

            em.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            em.close();
        }
        return watch;

    }
}
