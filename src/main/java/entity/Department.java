/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class represents the different
 * departments in Red Cross Samaritterne.
 * @author Daniel
 */
@Entity
public class Department implements Serializable {

    @Id
    private String nameOfDepartment;
//    private List<VagtKort> vagtkorts; // not Implemented!

    public String getNameOfDepartment() {
        return nameOfDepartment;
    }

    public void setNameOfDepartment(String nameOfDepartment) {
        this.nameOfDepartment = nameOfDepartment;
    }
}
