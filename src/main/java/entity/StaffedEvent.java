/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import entity.watches.SamaritFunctionsOnWatch;
import enums.Status;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author Daniel
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DT", discriminatorType = DiscriminatorType.CHAR)
public class StaffedEvent extends Event{
    
    @Enumerated(EnumType.STRING)
    private Status status;
    private String address;
    
    /*
    Hold track of how many of each RedCrossLevel is needed!
    Should be set to hold one of every RedCrossLevel with value of 0
    When Created!
    */
    @ElementCollection
    @OneToMany
    @MapKeyColumn(name="rlevel")
    @Column(name="numberneeded")
    @CollectionTable(name="STAFFNUMBER_EVENT", joinColumns=@JoinColumn(name="event_id"))
    private Map<String,Integer> levelsQuantity;
    

    @OneToMany(mappedBy = "staffedEvent", cascade = CascadeType.ALL)
    private List<SamaritFunctionsOnWatch> watchFunctions;
    
    public StaffedEvent() {
    }

    public StaffedEvent(Status status, Date start, Date end, boolean allDay, String name, String desc, Department department) {
        super(start, end, allDay, name, desc, department);
        this.status = status;
        watchFunctions = new ArrayList<>();
    }
    
   
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Integer> getLevelsQuantity() {
        return levelsQuantity;
    }

    public void setLevelsQuantity(Map<String, Integer> levelsQuantity) {
        this.levelsQuantity = levelsQuantity;
    }

    public List<SamaritFunctionsOnWatch> getWatchFunctions() {
        return watchFunctions;
    }

    public void setWatchFunctions(List<SamaritFunctionsOnWatch> watchFunctions) {
        this.watchFunctions = watchFunctions;
    }

    
    
    /**
     * This method should be called right after an Staffed Event is created.
     * Takes all the redcrossLevels and Initilazes the map with the Levels as keys
     * and the values of 0
     * @param list
     */
    public void initilazeLinkedMap(List<RedCrossLevel> list){
        levelsQuantity = new LinkedHashMap<>();
        
        for (RedCrossLevel redCrossLevel : list) {
            levelsQuantity.put(redCrossLevel.getLevel(), 0);
        }
        
    }
    
    
}
