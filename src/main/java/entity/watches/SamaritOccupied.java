/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.watches;

import com.fasterxml.jackson.annotation.JsonFilter;
import entity.Event;
import entity.OcupiedSlot;
import entity.Samarit;
import exceptions.DateNullException;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author dennisschmock
 */

@NamedQueries({
@NamedQuery(name = "SamaritOccupied.findByUserName", query = "SELECT w FROM SamaritOccupied AS w WHERE w.samarit.userName = :userName")})
@JsonFilter("samaritFilter")
@DiscriminatorValue(value = "SC")
@Entity
public class SamaritOccupied extends SamaritCalendar{

    private String title;
    private String rendering;
    private String color;
   
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Samarit samarit;
    

    public SamaritOccupied() {
    }

    public SamaritOccupied(Samarit testSam, Date start, Date end, boolean b) throws DateNullException {
        super(start,end,b);
        this.samarit = testSam;
        
    }

   

    

    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRendering() {
        return rendering;
    }

    public void setRendering(String rendering) {
        this.rendering = rendering;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
