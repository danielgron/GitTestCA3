/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Department;
import entity.Event;
import entityconnection.EntityConnector;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.*;
import javax.persistence.TypedQuery;

/**
 *
 * @author dennisschmock
 */
public class EventFacade {

    public EventFacade() {
        this.populateEvents();
    }

    /**
     * The purpose of this method is to return a list of Events
     *
     * @return
     */
    public List<Event> getEvents() {
        List<Event> events = null;

        EntityManager em = EntityConnector.getEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM Event e");
            events = q.getResultList();
        } catch (Exception ex) {
            log.Log.writeToLog("Exception get events" + ex);
        } finally {
            em.close();
        }
        return events;
    }

    public Event createEvent(Event event) {
        EntityManager em = EntityConnector.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(event);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            log.Log.writeToLog("Exception create events" + ex);
        } finally {
            em.close();
        }
        return event;

    }

    public Event getEvent(int id) {
        EntityManager em = EntityConnector.getEntityManager();
        Event event = null;
        try {
            event = em.find(Event.class, id);
        } catch (Exception ex) {
            log.Log.writeToLog("Exception get event" + ex);
        } finally {
            em.close();
        }
        return event;
    }

    public List<Event> getEventsDateRange(Date startDate, Date endDate) {
        List<Event> events = null;
        EntityManager em = EntityConnector.getEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM Event AS e WHERE e.start BETWEEN :start AND :end");
            q.setParameter("start", startDate);
            q.setParameter("end", endDate);
            events = q.getResultList();
        } catch (Exception ex) {
            log.Log.writeToLog("Exception get events Date Range" + ex);
        } finally {
            em.close();
        }

        return events;
    }

    public void populateEvents() {
        EntityManager em = EntityConnector.getEntityManager();
        LocalDateTime local = LocalDateTime.of(2016, Month.NOVEMBER, 3, 15, 0);
        LocalDateTime local1 = LocalDateTime.of(2016, Month.NOVEMBER, 5, 15, 0);
        LocalDateTime local2 = LocalDateTime.of(2016, Month.NOVEMBER, 7, 15, 0);
        LocalDateTime local3 = LocalDateTime.of(2016, Month.NOVEMBER, 9, 15, 0);
        LocalDateTime local4 = LocalDateTime.of(2016, Month.DECEMBER, 3, 15, 0);
        LocalDateTime local5 = LocalDateTime.of(2016, Month.DECEMBER, 3, 15, 0);

        Date start = new Date();
        Date start6 = Date.from(local.atZone(ZoneId.systemDefault()).toInstant());
        Date start1 = Date.from(local1.atZone(ZoneId.systemDefault()).toInstant());
        Date start2 = Date.from(local2.atZone(ZoneId.systemDefault()).toInstant());
        Date start3 = Date.from(local3.atZone(ZoneId.systemDefault()).toInstant());
        Date start4 = Date.from(local4.atZone(ZoneId.systemDefault()).toInstant());
        Date start5 = Date.from(local5.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime end = local.plusHours(1);
        LocalDateTime end1 = local1.plusHours(1);
        LocalDateTime end2 = local2.plusHours(1);
        LocalDateTime end3 = local3.plusHours(1);
        LocalDateTime end4 = local4.plusHours(1);
        LocalDateTime end5 = local5.plusHours(1);
        Date end01 = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
        Date end02 = Date.from(end1.atZone(ZoneId.systemDefault()).toInstant());
        Date end03 = Date.from(end2.atZone(ZoneId.systemDefault()).toInstant());
        Date end04 = Date.from(end3.atZone(ZoneId.systemDefault()).toInstant());
        Date end05 = Date.from(end4.atZone(ZoneId.systemDefault()).toInstant());
        Date end06 = Date.from(end5.atZone(ZoneId.systemDefault()).toInstant());

        if (getEvents().size() <= 0) {
            TypedQuery<Department> findfirstDepartment = em.createQuery("select d from Department d", Department.class);
            Department firstDepartment = findfirstDepartment.getSingleResult();
            Event event = new Event(start, end01, false, "FCK Brøndby", "test", firstDepartment);
            Event event1 = new Event(start1, end02, false, "JuleFrokost", "test", firstDepartment);
            Event event2 = new Event(start2, end03, false, "MadFestival", "test", firstDepartment);
            Event event3 = new Event(start3, end04, false, "Roskilde", "test", firstDepartment);
            Event event4 = new Event(start4, end05, false, "Fodbold Stævne", "test", firstDepartment);
            Event event5 = new Event(start5, end06, false, "TDC fest", "test", firstDepartment);
            createEvent(event);
            createEvent(event1);
            createEvent(event2);
            createEvent(event3);
            createEvent(event4);
            createEvent(event5);
        }
    }
}
