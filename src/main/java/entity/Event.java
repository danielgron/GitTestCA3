/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import entity.watches.ResourceWatch;
import entity.watches.SamaritWatch;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dennisschmock
 */
@Entity
@JsonFilter("samaritFilter")
public class Event implements Serializable, OcupiedSlot {

    private static final long serialVersionUID = 1L;
    @OneToMany (cascade = CascadeType.PERSIST, mappedBy = "event") // Events can persist new ResourceWatches.
    @JsonBackReference(value="resourceWatchs-event")
    private List<ResourceWatch> resourceWatchs;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_start")
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_end")
    private Date end;

    private boolean allDay;

    @ManyToOne
    private Department department;
    
    private List<Resource> resources;

    private String name;

    @Column(name = "description")
    private String desc;

    @OneToMany(mappedBy = "event")
    @JsonBackReference (value="watches-event")
    List<SamaritWatch> watches = new ArrayList();

    public Event(Date start, Date end, boolean allDay, String name, String desc, Department department) {
        this.start = start;
        this.end = end;
        this.allDay = allDay;
        this.name = name;
        this.desc = desc;
        this.department = department;
        department.addEvent(this);
    }

    public Event() {
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    public void addWatch(SamaritWatch watch) {
        this.watches.add(watch);
        watch.setEvent(this);

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
  
    @Override
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
    @Override
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
        department.addEvent(this); // Event is responsable for adding the refrence
    }

    public List<ResourceWatch> getResourceWatchs() {
        return resourceWatchs;
    }

    public void setResourceWatchs(List<ResourceWatch> resourceWatchs) {
        this.resourceWatchs = resourceWatchs;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<SamaritWatch> getWatches() {
        return watches;
    }

    public void setWatches(List<SamaritWatch> watches) {
        this.watches = watches;
    }
    
    

}
