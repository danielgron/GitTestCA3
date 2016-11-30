/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.watches;

import entity.Event;
import entity.user.Samarit;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Dennis
 */

@DiscriminatorValue(value = "SW")
@Entity
public class SamaritWatch extends SamaritCalendar {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Event event;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Samarit samarit;

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
