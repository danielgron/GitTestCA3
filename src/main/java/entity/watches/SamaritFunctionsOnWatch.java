/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.watches;

import com.fasterxml.jackson.annotation.JsonBackReference;
import entity.StaffedEvent;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This Class keeps track of the functions 
 * each shifts holds, and which Samarit that is assiged to it
 * 
 */
@Entity
public class SamaritFunctionsOnWatch implements Serializable {

    @JsonBackReference(value="samarit-Functions")
    @ManyToOne
    private StaffedEvent staffedEvent;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String samaritUserName;
    
    private String functionName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSamaritUserName() {
        return samaritUserName;
    }

    public void setSamaritUserName(String samaritUserName) {
        this.samaritUserName = samaritUserName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public StaffedEvent getStaffedEvent() {
        return staffedEvent;
    }

    public void setStaffedEvent(StaffedEvent staffedEvent) {
        this.staffedEvent = staffedEvent;
    }
    
    
    
    
    
}
