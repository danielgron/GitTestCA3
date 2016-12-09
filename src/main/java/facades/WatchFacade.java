/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Event;
import entity.StaffedEvent;
import entity.user.Samarit;
import entity.watches.SamaritOccupied;
import entity.watches.SamaritWatch;
import entityconnection.EntityConnector;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author dennisschmock
 */
public class WatchFacade {

    String pattern = "yyyy-MM-dd";
    DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");

    public WatchFacade() {

    }

    public List<SamaritOccupied> getWatches() {
        EntityManager em = EntityConnector.getEntityManager();
        List<SamaritOccupied> watches = null;
        try {
            Query q = em.createQuery("SELECT w FROM SamaritOccupied w");
            watches = q.getResultList();
        } catch (Exception ex) {
            log.Log.writeErrorMessageToLog("Error in Get Watches " + ex.getMessage());
        } finally {
            em.close();
        }
        return watches;
    }

    public List<SamaritOccupied> getWatchesForUser(String userName) {
        EntityManager em = EntityConnector.getEntityManager();
        List<SamaritOccupied> watches = null;
        try {

            Query q = em.createNamedQuery("SamaritOccupied.findByUserName");
            q.setParameter("userName", userName);
            watches = q.getResultList();

        } catch (Exception ex) {
            log.Log.writeToLog("Error in getWatches for User " + ex.getMessage());
        } finally {
            em.close();
        }
        return watches;
    }

    public SamaritOccupied getWatch(int id) {
        EntityManager em = EntityConnector.getEntityManager();
        SamaritOccupied watch = null;
        try {
            watch = em.find(SamaritOccupied.class, id);

        } catch (Exception ex) {
            log.Log.writeErrorMessageToLog("Error in getWatch " + ex.getMessage());
            throw ex;
        } finally {
            em.close();
        }
        return watch;
    }

    public SamaritOccupied updateWatch(SamaritOccupied watch) {
        EntityManager em = EntityConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(watch);
            em.getTransaction().commit();

        } catch (Exception ex) {
            log.Log.writeErrorMessageToLog("Error in update Watch " + ex.getMessage());
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        return watch;
    }

    public SamaritOccupied deleteWatch(Integer id) {
        EntityManager em = EntityConnector.getEntityManager();
        SamaritOccupied watch = null;
        try {
            watch = em.find(SamaritOccupied.class, id);
            em.getTransaction().begin();
            em.remove(watch);
            em.getTransaction().commit();
        } catch (Exception ex) {
            log.Log.writeErrorMessageToLog("Error in deleteWatch " + ex.getMessage());
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
            log.Log.writeErrorMessageToLog("Error in add watch: " + ex.getMessage());
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        return watch;
    }

    public SamaritOccupied addUnavailForWatch(SamaritOccupied watch) {
        EntityManager em = EntityConnector.getEntityManager();

        Samarit sm = null;
        try {
            em.getTransaction().begin();
            if (watch.getSamarit().getUserName() != null) {
                sm = em.find(Samarit.class, watch.getSamarit().getUserName());
            }
            //Find out if watch exist already
            Query q = em.createQuery("SELECT w FROM SamaritOccupied AS w WHERE w.samarit.userName = ?1 AND w.start = ?2");
            q.setParameter(1, watch.getSamarit().getUserName());
            q.setParameter(2, watch.getStart());
            List<SamaritOccupied> occ = q.getResultList();

            for (SamaritOccupied samaritOccupied : occ) {
                if (samaritOccupied.isAllDay()) {
                    return null;
                }
            }

            //Set as Occupied
            watch.setSamarit(sm);
            em.persist(watch);

            em.getTransaction().commit();

        } catch (Exception ex) {
            log.Log.writeErrorMessageToLog("Error in add Unavaible: " + ex.getMessage());
            throw ex;
        } finally {
            em.close();
        }
        return watch;

    }

    public SamaritOccupied getWatchesForDateUser(String date, String userName) {
        EntityManager em = EntityConnector.getEntityManager();
        SamaritOccupied watch = null;
        List<SamaritOccupied> watches;
        LocalDate dstart = dtf.parseLocalDate(date);
        LocalDate dstart1 = dstart.plusDays(1);

        Date d1 = dstart.toDate();
        Date d2 = dstart1.toDate();

        try {
            Query q = em.createQuery("SELECT w FROM SamaritOccupied AS w WHERE w.samarit.userName = ?1 AND w.start >= ?2 AND w.start < ?3"); // AND w.start = :date

            q.setParameter(1, userName);
            q.setParameter(2, d1);
            q.setParameter(3, d2);
            watches = q.getResultList();

            for (SamaritOccupied watche : watches) {

                if (watche.isAllDay()) {
                    deleteWatch(watche.getId());
                    watch = watche;
                }
            }
        } catch (Exception e) {
            log.Log.writeErrorMessageToLog("Error in getwatches for User: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
        return watch;
    }

    public List<Samarit> setWatchesForSamarits(List<SamaritWatch> samaritWatches, int eventId) {
        EntityManager em = EntityConnector.getEntityManager();
        try {
            StaffedEvent event = em.find(StaffedEvent.class, eventId);

            em.getTransaction().begin();
            for (SamaritWatch samaritWatch : samaritWatches) {

                //Method sets the bidirectional reference
                event.addWatch(samaritWatch);

                //Method also sets the bidirectional reference
                samaritWatch.setTitle(event.getName());
                samaritWatch.setStart(event.getStart());
                samaritWatch.setEnd(event.getEnd());

                samaritWatch.setColor("blue");
//                em.merge(event);
//                em.merge(samarit);
                em.persist(samaritWatch);

            }
            em.getTransaction().commit();

        } catch (Exception ex) {
            // em.getTransaction().rollback();
            log.Log.writeErrorMessageToLog("Error in getwatches for User: " + ex.getMessage());
            throw ex;
        } finally {
            em.close();
        }
//        return samarits;
        return null;

    }

    public List<SamaritWatch> getShifts(String userName) {
        EntityManager em = EntityConnector.getEntityManager();
        List<SamaritWatch> watches = new ArrayList<>();
        try {
            Query q = em.createQuery("Select sw FROM SamaritWatch AS sw WHERE sw.samarit.userName = ?1");
            q.setParameter(1, userName);
            watches = q.getResultList();
        } catch (Exception ex) {
            // em.getTransaction().rollback();
            log.Log.writeErrorMessageToLog("Error in getwatches for User: " + ex.getMessage());
            throw ex;
        } finally {
            em.close();
        }

        return watches;

    }

    public List<SamaritOccupied> addBlockInterval(List<SamaritOccupied> listOfBlocked) {
        for (SamaritOccupied samaritOccupied : listOfBlocked) {
          
            samaritOccupied = this.addUnavailForWatch(samaritOccupied);
        }
        return listOfBlocked;
    }
}
