/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Department;
import entity.Event;
import entity.Eventable;
import entity.OcupiedSlot;
import entity.Resource;
import entity.Samarit;
import entity.WatchFunction;
import entity.watches.ResourceWatch;
import entity.watches.SamaritOccupied;
import entity.watches.SamaritCalendar;
import entityconnection.EntityConnector;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import log.Log;
import util.DateUtils;

/**
 *
 * @author danie
 */
public class CoordinatorFacade {

    DepartmentFacade df = new DepartmentFacade();

    public Samarit addNewSamarit(Samarit s) {
        //s.setDepartment(df.getDepartment(s.getDepartment().getNameOfDepartment()));
        Department d = df.getDepartment(s.getDepartment().getNameOfDepartment());
        d.addUser(s);
        //Log.writeToLog("Adding new samarite");
        EntityManager em = EntityConnector.getEntityManager();
        //if (s.getRedCroosLevel()==null) throw new NoRedCrossLevelException();
        try {
            em.getTransaction().begin();
            em.merge(d);
            em.persist(s);
            em.getTransaction().commit();
            Log.writeToLog("New samarite added");
        } catch (Exception e) {
            Log.writeErrorMessageToLog("Exception encountered while adding samarite.");
            Log.writeErrorMessageToLog(e.getMessage());
            throw e;
        } finally {
            em.close();
        }
        return s;
    }

    public List<Samarit> getAvailableSamaritesFromEventId(int eventId) {
        Event e;
        List<Samarit> availableSams = new ArrayList();
        EntityManager em = EntityConnector.getEntityManager();
        em.getEntityManagerFactory().getCache().evictAll(); // IMPORTANT!!! This Clears the Cache of the JPA!
        //if (s.getRedCroosLevel()==null) throw new NoRedCrossLevelException();
        try {
            em.getTransaction().begin();
            e = em.find(Event.class, eventId);
            List<Samarit> allFromDepartMent = e.getDepartment().getSamarites();
            for (Samarit samarit : allFromDepartMent) {
                if (checkAvalibilty(samarit, e)) {
                    availableSams.add(samarit);
                }
            }
        } catch (Exception ex) {
            Log.writeErrorMessageToLog("Exception in Coordinator Facade getAvailable Samarits: " + ex.getMessage());
            throw ex;
        } finally {
            em.close();
        }
        return availableSams;
    }

    /**
     * Checks if the samarit is availble in the perriod that the Event Spans
     * over
     *
     * @param samarit Samarit
     * @param e Event
     * @return True if Availiby else false
     */
    private boolean checkAvalibilty(Samarit samarit, Event e) {
        boolean available = true;
        String samNAme = samarit.getUserName();
        //Query q = em.createQuery("SELECT s FROM Samarit AS s LEFT JOIN s.watches AS sw WHERE sw IS NULL OR sw.start >= '2016-11-03' AND sw.end <='2016-11-03'");
        List<OcupiedSlot> blockedTimes = samarit.getNotAvail();
        try {
            for (OcupiedSlot blockedTime : blockedTimes) {

                // In case of isAllDay evalutaing to true always set available to false
                if (blockedTime.isAllDay()) {
                    if (blockedTime.getStart().getDate() != e.getStart().getDate()) {
                        continue;
                    } else {
                        return false;
                    }
                }
                if ( // In case isAllDay is false check start and end times
                        (DateUtils.dateBetween(blockedTime.getStart(), e.getStart(), e.getEnd())
                        || DateUtils.dateBetween(blockedTime.getEnd(), e.getStart(), e.getEnd())
                        || DateUtils.dateBetween(e.getStart(), blockedTime.getStart(), blockedTime.getEnd())
                        || DateUtils.dateBetween(e.getStart(), blockedTime.getStart(), blockedTime.getEnd()))) {
                    available = false;
                }

            }
        } catch (Exception ex) {
            Log.writeErrorMessageToLog("Error when loading calenderEvent for Samarit: " + samarit.getUserName());
            Log.writeErrorMessageToLog(ex.getMessage());
            throw ex;

        }

        return available;
    }

    public List<WatchFunction> getWatchFunctionsFromDepartment(String department) {
        List<WatchFunction> list = null;
        EntityManager em = EntityConnector.getEntityManager();
        try {
            Query q = em.createQuery("select w from WatchFunction w where w.department.nameOfDepartment LIKE :dept", WatchFunction.class);
            q.setParameter("dept", department);
            list = q.getResultList();
        } catch (Exception e) {
            log.Log.writeErrorMessageToLog("Error" + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
        return list;
    }

    public WatchFunction createNewFunctionForDepartment(WatchFunction watchFunction) {
        EntityManager em = EntityConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(watchFunction);
            em.getTransaction().commit();
        } catch (Exception e) {
            log.Log.writeErrorMessageToLog("Error in Create Function: " + e);
            throw e;
        } finally {
            em.close();
        }
        return watchFunction;
    }

    public void toggleResource(int eventId, int resId) {

        Event e;
        EntityManager em = EntityConnector.getEntityManager();
        em.getEntityManagerFactory().getCache().evictAll(); // IMPORTANT!!! This Clears the Cache of the JPA!
        //if (s.getRedCroosLevel()==null) throw new NoRedCrossLevelException();
        try {
            em.getTransaction().begin();
            // Get the event in question
            e = em.find(Event.class, eventId);
            // Get the resources registered for the event
            List<ResourceWatch> eventResourceWatchs = e.getResourceWatchs();
            // Get resource from id
            Resource r = em.find(Resource.class, resId);
            // Iterate
            List<OcupiedSlot> notAvail = r.getNotAvail();

            boolean isThere = false;
            for (OcupiedSlot watch : notAvail) {
                if (((ResourceWatch) watch).getEvent() == e) {
                    isThere = true;
                }
            }

            // If already present - remove the shift
            if (isThere) {
                Query q = em.createQuery("delete from ResourceWatch rw where (rw.resource.id =:resId AND rw.event.id =:eventId)", WatchFunction.class);
                q.setParameter("resId", resId);
                q.setParameter("eventId", eventId);
                q.executeUpdate();
            } // Else create it and add it
            else {
                ResourceWatch resWatch = new ResourceWatch();
                Resource res = em.find(Resource.class, resId);

                resWatch.setEvent(e);
                resWatch.setResource(res);
                eventResourceWatchs.add(resWatch);
                em.persist(resWatch);
            }
            em.getTransaction().commit();

        } catch (Exception ex) {
            Log.writeErrorMessageToLog("Exception in Coordinator Facade toggleResource: " + ex.getMessage());
            throw ex;
        } finally {
            em.close();
        }

    }

}
