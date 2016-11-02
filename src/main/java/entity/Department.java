/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This class represents the different
 * departments in Red Cross Samaritterne.
 * @author Daniel
 */
@Entity
public class Department implements Serializable {

    @Id
    private String nameOfDepartment;
    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    
    private List<Samarit> samarites;
//    private List<VagtKort> vagtkorts; // not Implemented!

    public List<Samarit> getSamarites() {
        return samarites;
    }

    public void setUsers(List<Samarit> samarites) {
        this.samarites = samarites;
    }
    public void addUser(Samarit u) {
        if (samarites==null)samarites = new ArrayList();
        samarites.add(u);
        u.setDepartment(this);
        
    }

    public String getNameOfDepartment() {
        return nameOfDepartment;
    }

    public void setNameOfDepartment(String nameOfDepartment) {
        this.nameOfDepartment = nameOfDepartment;
    }
    
    
}
