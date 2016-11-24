/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import entity.watches.ResourceWatch;
import entity.watches.SamaritOccupied;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Daniel
 */
@Entity
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    
    @OneToMany(mappedBy = "resource", cascade = {CascadeType.PERSIST, CascadeType.MERGE},targetEntity = ResourceWatch.class)
    @JsonBackReference(value="watches-res")
    private List<OcupiedSlot> notAvail;
    @ManyToOne
    private Department department;
    

    public Resource() {
        this.notAvail = new ArrayList();
    }
    

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
        if (!(object instanceof Resource)) {
            return false;
        }
        Resource other = (Resource) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Resource[ id=" + id + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OcupiedSlot> getNotAvail() {
        return notAvail;
    }

    public void setNotAvail(List<OcupiedSlot> notAvail) {
        this.notAvail = notAvail;
    }
    
    public void addNotAvail(OcupiedSlot notAvail){
        this.notAvail.add(notAvail);
        ResourceWatch rw= (ResourceWatch)notAvail;
        
                rw.setResource(this);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    
    
}
