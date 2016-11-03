/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This Class is a representation of a 
 * Samarit
 * @author Daniel
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DT", discriminatorType = DiscriminatorType.CHAR)
public class Samarit extends User{
    
        @ManyToOne(cascade = CascadeType.MERGE)
    private Department department;
//  private Dato dato; // Not Implemented!
    private String firstName;
    private String lastName;
    private String adresse; //(Vej)
    private String zip;
    private String city;
    private String phone;
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    private RedCrossLevel redCrossLevel; // f.eks Samarit, eller Teamleder
    private String medicalLevel; // f.eks. Medic, Medic 2 eller læge. (Vil være intet for mange)
    private String driverLevel; // Hvilke Biler og bilbtyper må samaritten benytte. (Vil være intet for mange)
    private int shiftsThisSeason;
    private int shiftsTotal;
//  private List<VagtKort> vagtKorts; // Not implemented yet!
    @ManyToMany(mappedBy = "samarits")
    private List<Event> events;

    public Samarit() {
    }

    public Samarit(String email, String password) {
        super(email, password);
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RedCrossLevel getRedCroosLevel() {
        return redCrossLevel;
    }

    public void setRedCroosLevel(RedCrossLevel redCroosLevel) {
        this.redCrossLevel = redCroosLevel;
    }

    public String getMedicalLevel() {
        return medicalLevel;
    }

    public void setMedicalLevel(String medicalLevel) {
        this.medicalLevel = medicalLevel;
    }

    public String getDriverLevel() {
        return driverLevel;
    }

    public void setDriverLevel(String driverLevel) {
        this.driverLevel = driverLevel;
    }

    public int getShiftsThisSeason() {
        return shiftsThisSeason;
    }

    public void setShiftsThisSeason(int shiftsThisYear) {
        this.shiftsThisSeason = shiftsThisYear;
    }

    public int getShiftsTotal() {
        return shiftsTotal;
    }

    public void setShiftsTotal(int shiftsTotal) {
        this.shiftsTotal = shiftsTotal;
    }
    
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
}
