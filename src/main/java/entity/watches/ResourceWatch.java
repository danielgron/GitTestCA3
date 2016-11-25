/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.watches;

import entity.Event;
import entity.OcupiedSlot;
import entity.Resource;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author danie
 */
@Entity
public class ResourceWatch implements Serializable, OcupiedSlot {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Event event;

    @ManyToOne
    private Resource resource;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResourceWatch)) {
            return false;
        }
        ResourceWatch other = (ResourceWatch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.watches.ResourceWatch[ id=" + id + " ]";
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public Date getStart() {
        return event.getStart();
    }

    @Override
    public Date getEnd() {
        return event.getEnd();
    }

    @Override
    public boolean isAllDay() {
        return event.isAllDay();
    }

    public Resource getResource() {
        return resource;
    }

}
