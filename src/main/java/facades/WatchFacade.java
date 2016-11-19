/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Event;
import entity.Samarit;
import entity.SamaritWatch;
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

    public List<SamaritWatch> getWatches() {
        EntityManager em = EntityConnector.getEntityManager();
        List<SamaritWatch> watches = null;
        try {
            Query q = em.createQuery("SELECT w FROM SamaritWacth w");
            watches = q.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return watches;
    }

    public List<SamaritWatch> getWatchesForUser(String email) {
        EntityManager em = EntityConnector.getEntityManager();
        List<SamaritWatch> watches = null;
        try {
            User samarit = em.find(Samarit.class, email);
            Query q = em.createNamedQuery("SamaritWatch.findByUserName");
            q.setParameter("mail", email);
            watches = q.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return watches;
    }

    public SamaritWatch getWatch(int id) {
        EntityManager em = EntityConnector.getEntityManager();
        SamaritWatch watch = null;
        try {
            watch = em.find(SamaritWatch.class, id);

        } catch (Exception ex) {
            Logger.getLogger(EventFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        return watch;
    }

    public SamaritWatch updateWatch(SamaritWatch watch) {
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

    public SamaritWatch deleteWatch(Integer id) {
        EntityManager em = EntityConnector.getEntityManager();
        SamaritWatch watch = null;
        try {
            watch = em.find(SamaritWatch.class, id);
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

    public SamaritWatch addWatch(SamaritWatch watch, String email, Integer eventId) {
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

    public SamaritWatch addUnavailForWatch(SamaritWatch watch) {
        EntityManager em = EntityConnector.getEntityManager();
        Samarit sm = null;
        try {
            em.getTransaction().begin();
            System.out.println("AFter entityManager");
            if (watch.getSamarit().getUserName() != null) {
                System.out.println("Befor2e find " + watch.getSamarit().getUserName());
                System.out.println(sm.getUserName());
            }
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
