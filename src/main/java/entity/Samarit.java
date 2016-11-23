/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.watches.SamaritOccupied;
import com.fasterxml.jackson.annotation.JsonBackReference;
import entity.watches.SamaritCalendar;
import entity.watches.SamaritWatch;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This Class is a representation of a Samarit
 *
 * @author Daniel
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@DiscriminatorColumn(name = "DT", discriminatorType = DiscriminatorType.CHAR)
public class Samarit extends User {

    @ManyToOne(cascade = CascadeType.MERGE)
    private Department department;
    private String firstName;
    private String lastName;
    private String adresse; //(Vej)
    private String zip;
    private String city;
    private String phone;

    @ManyToMany(cascade = CascadeType.MERGE)
    @NotNull
    private List<RedCrossLevel> redCrossLevel; // f.eks Samarit, eller Teamleder
    private int shiftsThisSeason;
    private int shiftsTotal;
//  private List<VagtKort> vagtKorts; // Not implemented yet!

    
    @OneToMany(mappedBy = "samarit", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference(value="watches-sam")
    private List<SamaritWatch> watches = new ArrayList();
    
    @OneToMany(mappedBy = "samarit", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference(value="occupied-sam")
    private List<SamaritOccupied> notAvail = new ArrayList<>();
    
    @ManyToMany(cascade = CascadeType.MERGE)
    @NotNull
    private List<WatchFunction> watchFunctions;

    public Samarit() {
    }

    public Samarit(String email, String password) {
        super(email, password);
    }

    public void addWatch(SamaritWatch watch) {
        this.getWatches().add(watch);
        watch.setSamarit(this);
    }
    
    
    public void addNotAvail(SamaritOccupied notAvail){
        this.notAvail.add(notAvail);
        notAvail.setSamarit(this);
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

    /**
     * @return the redCrossLevel
     */
    public List<RedCrossLevel> getRedCrossLevel() {
        return redCrossLevel;
    }

    /**
     * @param redCrossLevel the redCrossLevel to set
     */
    public void setRedCrossLevel(List<RedCrossLevel> redCrossLevel) {
        this.redCrossLevel = redCrossLevel;
    }

    /**
     * @return the watches
     */
    public List<SamaritWatch> getWatches() {
        return watches;
    }

    /**
     * @param watches the watches to set
     */
    public void setWatches(List<SamaritWatch> watches) {
        this.watches = watches;
    }

    /**
     * @return the notAvail
     */
    public List<SamaritOccupied> getNotAvail() {
        return notAvail;
    }

    public List<WatchFunction> getWatchFunctions() {
        return watchFunctions;
    }

    public void setWatchFunctions(List<WatchFunction> watchFunctions) {
        this.watchFunctions = watchFunctions;
    }

    /**
     * @param notAvail the notAvail to set
     */
    public void setNotAvail(List<SamaritOccupied> notAvail) {
        this.notAvail = notAvail;
    }
    
    public void addRedCrossLevelToSamarit(RedCrossLevel level){
        if(redCrossLevel == null){
            redCrossLevel = new ArrayList<>();
        }
        redCrossLevel.add(level);
        level.addSamaritToLevel(this);
    }
    
    public void addFunctionToSamarit(WatchFunction function){
        if(watchFunctions == null){
            watchFunctions = new ArrayList<>();
        }
        watchFunctions.add(function);
        function.addSamaritToFunction(this);
    }

    

}
