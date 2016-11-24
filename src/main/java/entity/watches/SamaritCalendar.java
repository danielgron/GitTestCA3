/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.watches;

import entity.OcupiedSlot;
import exceptions.DateNullException;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dennis
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Table(name = "SAMARITCALENDER")
public class SamaritCalendar implements Serializable, OcupiedSlot{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WATCHSTART")
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WATCHEND")
    private Date end;

    private boolean allDay;

    public SamaritCalendar() {
    }

  

    public SamaritCalendar(Date start, Date end, boolean allDay) throws DateNullException {
        if (start==null) throw new DateNullException("Not allowed to add null start date");
        this.start = start;
        if(end!=null) this.end = end;
        else{
            this.start.setHours(0);
            this.start.setMinutes(0);
            long endTime = this.start.getTime()+(1000*60*60*24);
            this.end= new Date(endTime);
        }
        this.allDay = allDay;
    }

   
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * @return the allDay
     */
    public boolean isAllDay() {
        return allDay;
    }

    /**
     * @param allDay the allDay to set
     */
    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

  

}
