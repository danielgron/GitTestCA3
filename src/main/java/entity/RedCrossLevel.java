/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author danie
 */
@Entity
public class RedCrossLevel implements Serializable {

    @OneToMany(mappedBy = "redCrossLevel")
    private List<Samarit> samarits;

    private static final long serialVersionUID = 1L;
    @Id
    private String levelName;
    @OneToMany(mappedBy = "redCrossLevel")
    private List<ExternalUser> externalUsers;
    
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
    
    


    
}
