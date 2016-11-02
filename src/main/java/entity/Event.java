/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dennisschmock
 */
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "date_id",updatable = true,unique = true)
    private Date date_id;
    
    
    
    private String eventName;
    private String description;

    public Event(Date date_id, String eventName, String description) {
        this.date_id = date_id;
        this.eventName = eventName;
        this.description = description;
    }

    public Event() {
    }

    
    public Date getDate_id() {
        return date_id;
    }

    public void setDate_id(Date date_id) {
        this.date_id = date_id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   
    
}
