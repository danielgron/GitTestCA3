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
import util.DateUtils;

/**
 *
 * @author dennisschmock
 */
public class WatchFacade {

    public WatchFacade() {
        EntityManager em = EntityConnector.getEntityManager();
//        if (getWatches().size() <= 0) {
//            Samarit samarit = em.find(Samarit.class, "coordinator");
//            LocalDate d1 = LocalDate.of(2016, Month.NOVEMBER, 7);
//            LocalDate d2 = LocalDate.of(2016, Month.NOVEMBER, 8);
//            LocalDate d3 = LocalDate.of(2016, Month.NOVEMBER, 9);
//            LocalDate d4 = LocalDate.of(2016, Month.NOVEMBER, 10);
//            Date d01 = DateUtils.asDate(d1);
//            Date d02 = DateUtils.asDate(d2);
//            Date d03 = DateUtils.asDate(d3);
//            Date d04 = DateUtils.asDate(d4);
//
//            SamaritWatch sw1 = new SamaritWatch(samarit, d01, false);
//            SamaritWatch sw2 = new SamaritWatch(samarit, d02, false);
//            SamaritWatch sw3 = new SamaritWatch(samarit, d03, false);
//            SamaritWatch sw4 = new SamaritWatch(samarit, d04, false);
//
//            em.getTransaction().begin();
//            em.persist(sw1);
//            em.persist(sw2);
//            em.persist(sw3);
//            em.persist(sw4);
//            em.getTransaction().commit();
//
//        }

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

            Query q = em.createQuery("SELECT w FROM SameritWatch w WHERE w.samarit = ?u");
            q.setParameter("u", samarit);
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
            if (watch.getSamarit() != null) {
                sm = em.find(Samarit.class, watch.getSamarit().getUserName());
                watch.setSamaritWithWatch(sm);
                System.out.println(watch);
            }
            em.persist(watch);

            em.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("Closed!");
            em.close();
        }
        return watch;

    }
}
