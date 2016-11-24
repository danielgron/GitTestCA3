/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Department;
import entity.Event;
import entity.OcupiedSlot;
import entity.Samarit;
import entity.WatchFunction;
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
            Log.writeToLog("Exception encountered while adding samarite.");
            Log.writeToLog(e.getMessage());
        } finally {
            em.close();
        }
        return s;
    }

    public List<Samarit> getAvailableSamaritesFromEventId(int eventId) {
        Event e;
        List<Samarit> availableSams = new ArrayList();
        EntityManager em = EntityConnector.getEntityManager();
        //if (s.getRedCroosLevel()==null) throw new NoRedCrossLevelException();
        try {
            em.getTransaction().begin();
            e = em.find(Event.class, eventId);
            List<Samarit> allFromDepartMent = e.getDepartment().getSamarites();
            for (Samarit samarit : allFromDepartMent) {
                if (checkAvalibilty(samarit, e, em)) {
                    availableSams.add(samarit);
                }
            }
        } catch (Exception ex) {
            Log.writeToLog("Exception in Coordinator Facade getAvailable Samarits: " + ex.getMessage());
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
    private boolean checkAvalibilty(Samarit samarit, Event e, EntityManager em) {
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
            Log.writeToLog("Error when loading calenderEvent for Samarit: " + samarit.getUserName());
            Log.writeToLog(ex.getMessage());
            throw ex;

        }

        return available;
    }

    public List<WatchFunction> getWatchFunctionsFromDepartment(String department) {
        EntityManager em = EntityConnector.getEntityManager();
        Query q = em.createQuery("select w from WatchFunction w where w.department.nameOfDepartment LIKE :dept", WatchFunction.class);
        q.setParameter("dept", department);
        List<WatchFunction> list = q.getResultList();
        return list;
    }

}
