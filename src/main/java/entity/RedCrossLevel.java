/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author danie
 */
@Entity
public class RedCrossLevel implements Serializable {

    @ManyToMany(mappedBy = "redCrossLevel")
    @JsonBackReference(value="samarits-rcl")
    private List<Samarit> samarits;

    private static final long serialVersionUID = 1L;
    @Id
    private String levelName;
    
    public RedCrossLevel(){
    }
    
    public RedCrossLevel(String level){
        this.levelName=level;
    }

    public String getLevel() {
        return levelName;
    }

    public void setLevel(String levelName) {
        this.levelName = levelName;
    }

    public List<Samarit> getSamarits() {
        return samarits;
    }

    public void setSamarits(List<Samarit> samarits) {
        this.samarits = samarits;
    }
    
    public void addSamaritToLevel(Samarit sam){
        if(samarits == null){
            samarits = new ArrayList<>();
        }
        samarits.add(sam);
    }
    
    


    
}
