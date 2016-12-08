/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.watches;

import com.fasterxml.jackson.annotation.JsonBackReference;
import entity.Event;
import entity.user.Samarit;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * This Class is a reprenstation of an Samarit that is put on an StaffedEvent.(An watch)
 * The class holds a refrence back to the event, An Refrence to an Samarit
 * And a String that represents the RedCrossLevel the Samarit will have for this
 * Watch.
 * @author Dennis
 */

@DiscriminatorValue(value = "SW")
@Entity
public class SamaritWatch extends SamaritCalendar {

    /**
     * @return the watchRole
     */
    public String getWatchRole() {
        return watchRole;
    }

    /**
     * @param watchRole the watchRole to set
     */
    public void setWatchRole(String watchRole) {
        this.watchRole = watchRole;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
        @JsonBackReference (value="watches-event")

    private Event event;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Samarit samarit;
    
    private String watchRole;
    
    public SamaritWatch() {
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

  

}
