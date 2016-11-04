/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dennisschmock
 */
@Entity
public class SamaritWatch implements Serializable {

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @ManyToOne (cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Samarit samarit;

    @ManyToOne (cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Event event;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private boolean isAvailable = true;
    
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date watchStart;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date watchEnd;

    public SamaritWatch() {
    }

    public SamaritWatch(Samarit samarit, Event event, Date watchStart, Date watchEnd) {
        this.samarit = samarit;
        this.event = event;
        this.watchStart = watchStart;
        this.watchEnd = watchEnd;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the samarit
     */
    public Samarit getSamarit() {
        return samarit;
    }

    /**
     * @param samarit the samarit to set
     */
    public void setSamarit(Samarit samarit) {
        this.samarit = samarit;
    }

    /**
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * @return the isAvailable
     */
    public boolean isIsAvailable() {
        return isAvailable;
    }

    /**
     * @param isAvailable the isAvailable to set
     */
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * @return the watchStart
     */
    public Date getWatchStart() {
        return watchStart;
    }

    /**
     * @param watchStart the watchStart to set
     */
    public void setWatchStart(Date watchStart) {
        this.watchStart = watchStart;
    }

    /**
     * @return the watchEnd
     */
    public Date getWatchEnd() {
        return watchEnd;
    }

    /**
     * @param watchEnd the watchEnd to set
     */
    public void setWatchEnd(Date watchEnd) {
        this.watchEnd = watchEnd;
    }

    
}
