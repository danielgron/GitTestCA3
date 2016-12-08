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
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Daniel
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DT", discriminatorType = DiscriminatorType.CHAR)
public class StaffedEvent extends Event {

    @Enumerated(EnumType.STRING)
    private Status status;
    private String address;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date doorsopen;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date watchStart;

    private String catering;
    private boolean treatmentfacility;
    private String comments;
    private int price;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Contact contact;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Invoice invoice;
    private boolean medics;
    private boolean ambulance;
    private boolean emergencyOffice;
    private boolean stretcherTeam;
    private boolean responseTeam;
    private String visibility;
    private String venue;
    private String street;
    private int zip;
    private int numberGuests;
    private String agegroup;
    private String coordinatorcomment;

    /*
    Hold track of how many of each RedCrossLevel is needed!
    Should be set to hold one of every RedCrossLevel with value of 0
    When Created!
     */
    @ElementCollection
    @OneToMany
    @MapKeyColumn(name = "rlevel")
    @Column(name = "numberneeded")
    @CollectionTable(name = "STAFFNUMBER_EVENT", joinColumns = @JoinColumn(name = "event_id"))
    private Map<String, Integer> levelsQuantity;

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
     * Takes all the redcrossLevels and Initilazes the map with the Levels as
     * keys and the values of 0
     *
     * @param list
     */
    public void initilazeLinkedMap(List<RedCrossLevel> list) {
        levelsQuantity = new LinkedHashMap<>();

        for (RedCrossLevel redCrossLevel : list) {
            levelsQuantity.put(redCrossLevel.getLevel(), 0);
        }

    }

    public Date getDoorsopen() {
        return doorsopen;
    }

    public void setDoorsopen(Date doorsopen) {
        this.doorsopen = doorsopen;
    }

    public String getCatering() {
        return catering;
    }

    public void setCatering(String catering) {
        this.catering = catering;
    }

    public boolean isTreatmentfacility() {
        return treatmentfacility;
    }

    public void setTreatmentfacility(boolean treatmentfacility) {
        this.treatmentfacility = treatmentfacility;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public boolean isMedics() {
        return medics;
    }

    public void setMedics(boolean medics) {
        this.medics = medics;
    }

    public boolean isAmbulance() {
        return ambulance;
    }

    public void setAmbulance(boolean ambulance) {
        this.ambulance = ambulance;
    }

    public boolean isEmergencyOffice() {
        return emergencyOffice;
    }

    public void setEmergencyOffice(boolean emergencyOffice) {
        this.emergencyOffice = emergencyOffice;
    }

    public boolean isStretcherTeam() {
        return stretcherTeam;
    }

    public void setStretcherTeam(boolean stretcherTeam) {
        this.stretcherTeam = stretcherTeam;
    }

    public boolean isResponseTeam() {
        return responseTeam;
    }

    public void setResponseTeam(boolean responseTeam) {
        this.responseTeam = responseTeam;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getNumberGuests() {
        return numberGuests;
    }

    public void setNumberGuests(int numberGuests) {
        this.numberGuests = numberGuests;
    }

    public String getAgegroup() {
        return agegroup;
    }

    public void setAgegroup(String agegroup) {
        this.agegroup = agegroup;
    }

    public Date getWatchStart() {
        return watchStart;
    }

    public void setWatchStart(Date watchStart) {
        this.watchStart = watchStart;
    }

    public String getCoordinatorcomment() {
        return coordinatorcomment;
    }

    public void setCoordinatorcomment(String coordinatorcomment) {
        this.coordinatorcomment = coordinatorcomment;
    }
    }
