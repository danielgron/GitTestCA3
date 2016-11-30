/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.user.Samarit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This class represents the different departments in Red Cross Samaritterne.
 *
 * @author Daniel
 */
@Entity
public class Department implements Serializable {

    @OneToMany(mappedBy = "department")
    private List<Request> requests;

    @OneToMany(mappedBy = "department")
    @JsonBackReference(value="watchfunction-dep")
    private List<WatchFunction> watchFunctions;

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    @JsonBackReference(value="event-dep")
    private List<Event> events;

    @Id
    private String nameOfDepartment;
    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    @JsonBackReference(value="samarit-con")
    private List<Samarit> samarites;
//    private List<VagtKort> vagtkorts; // not Implemented!
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    private List<Resource> resources;

    public List<Samarit> getSamarites() {
        return samarites;
    }

    public void setUsers(List<Samarit> samarites) {
        this.samarites = samarites;
    }

    public void addUser(Samarit u) {
        if (samarites == null) {
            samarites = new ArrayList();
        }
        samarites.add(u);
        u.setDepartment(this);

    }

    public String getNameOfDepartment() {
        return nameOfDepartment;
    }

    public void setNameOfDepartment(String nameOfDepartment) {
        this.nameOfDepartment = nameOfDepartment;
    }

    public List<Event> getEvents() {
        if (events ==null) events = new ArrayList();
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<WatchFunction> getWatchFunctions() {
        return watchFunctions;
    }

    public void setWatchFunctions(List<WatchFunction> watchFunctions) {
        this.watchFunctions = watchFunctions;
    }
    
    
    
    public void addEvent(Event e){
        if(events == null){
            events = new ArrayList<>();
        }
        events.add(e);
    }
    
    public void addWatchFunction(WatchFunction function){
        if(watchFunctions == null){
            watchFunctions = new ArrayList<>();
        }
        watchFunctions.add(function);
        function.setDepartment(this);
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
    
    public void addResource(Resource resource){
        resources.add(resource);
    }
    
    

}
