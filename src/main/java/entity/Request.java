/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import enums.RequestStatus;
import enums.Status;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author dennisschmock
 */
@Entity
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String eventName;
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;
    private int numberGuests;
    private String agegroup;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date eventDate;
    private String venue;
    private String street;
    private int zip;
    @ManyToOne
    private Department department;
    @OneToMany(mappedBy = "request")
    @JsonBackReference(value="resources-req")
    private List<Resource> resources;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date doorsopen;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date eventstart;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date eventend;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date watchStart;

    private String catering;
    private boolean treatmentfacility;
    private String comments;

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

    public Request() {
    }

    public Request(String eventName, int numberGuests, String agegroup, Date eventDate, String venue, String street, int zip, Date doorsopen, Date eventstart, Date eventend, Date watchStart, String catering, boolean treatmentfacility, String comments, boolean medics, boolean ambulance, boolean emergencyOffice, boolean stretcherTeam, boolean responseTeam, String visibility) {
        this.eventName = eventName;
        this.numberGuests = numberGuests;
        this.agegroup = agegroup;
        this.eventDate = eventDate;
        this.venue = venue;
        this.street = street;
        this.zip = zip;
        this.doorsopen = doorsopen;
        this.eventstart = eventstart;
        this.eventend = eventend;
        this.watchStart = watchStart;
        this.catering = catering;
        this.treatmentfacility = treatmentfacility;
        this.comments = comments;
        this.medics = medics;
        this.ambulance = ambulance;
        this.emergencyOffice = emergencyOffice;
        this.stretcherTeam = stretcherTeam;
        this.responseTeam = responseTeam;
        this.visibility = visibility;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return the numberGuests
     */
    public int getNumberGuests() {
        return numberGuests;
    }

    /**
     * @param numberGuests the numberGuests to set
     */
    public void setNumberGuests(int numberGuests) {
        this.numberGuests = numberGuests;
    }

    /**
     * @return the agegroup
     */
    public String getAgegroup() {
        return agegroup;
    }

    /**
     * @param agegroup the agegroup to set
     */
    public void setAgegroup(String agegroup) {
        this.agegroup = agegroup;
    }

    /**
     * @return the eventDate
     */
    public Date getEventDate() {
        return eventDate;
    }

    /**
     * @param eventDate the eventDate to set
     */
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * @return the venue
     */
    public String getVenue() {
        return venue;
    }

    /**
     * @param venue the venue to set
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the zip
     */
    public int getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * @return the doorsopen
     */
    public Date getDoorsopen() {
        return doorsopen;
    }

    /**
     * @param doorsopen the doorsopen to set
     */
    public void setDoorsopen(Date doorsopen) {
        this.doorsopen = doorsopen;
    }

    /**
     * @return the eventstart
     */
    public Date getEventstart() {
        return eventstart;
    }

    /**
     * @param eventstart the eventstart to set
     */
    public void setEventstart(Date eventstart) {
        this.eventstart = eventstart;
    }

    /**
     * @return the eventend
     */
    public Date getEventend() {
        return eventend;
    }

    /**
     * @param eventend the eventend to set
     */
    public void setEventend(Date eventend) {
        this.eventend = eventend;
    }

    /**
     * @return the watchStart
     */
    public Date getWatchStart() {
        return watchStart;
    }

    /**
     * @param watchStart the watchStart to set
     */
    public void setWatchStart(Date watchStart) {
        this.watchStart = watchStart;
    }

    /**
     * @return the catering
     */
    public String getCatering() {
        return catering;
    }

    /**
     * @param catering the catering to set
     */
    public void setCatering(String catering) {
        this.catering = catering;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(Contact contact) {
        this.contact = contact;
        contact.getRequest().add(this);
    }

    /**
     * @return the invoice
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * @param invoice the invoice to set
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;

    }

    /**
     * @return the treatmentfacility
     */
    public boolean isTreatmentfacility() {
        return treatmentfacility;
    }

    /**
     * @param treatmentfacility the treatmentfacility to set
     */
    public void setTreatmentfacility(boolean treatmentfacility) {
        this.treatmentfacility = treatmentfacility;
    }

    /**
     * @return the medics
     */
    public boolean isMedics() {
        return medics;
    }

    /**
     * @param medics the medics to set
     */
    public void setMedics(boolean medics) {
        this.medics = medics;
    }

    /**
     * @return the ambulance
     */
    public boolean isAmbulance() {
        return ambulance;
    }

    /**
     * @param ambulance the ambulance to set
     */
    public void setAmbulance(boolean ambulance) {
        this.ambulance = ambulance;
    }

    /**
     * @return the emergencyOffice
     */
    public boolean isEmergencyOffice() {
        return emergencyOffice;
    }

    /**
     * @param emergencyOffice the emergencyOffice to set
     */
    public void setEmergencyOffice(boolean emergencyOffice) {
        this.emergencyOffice = emergencyOffice;
    }

    /**
     * @return the stretcherTeam
     */
    public boolean isStretcherTeam() {
        return stretcherTeam;
    }

    /**
     * @param stretcherTeam the stretcherTeam to set
     */
    public void setStretcherTeam(boolean stretcherTeam) {
        this.stretcherTeam = stretcherTeam;
    }

    /**
     * @return the responseTeam
     */
    public boolean isResponseTeam() {
        return responseTeam;
    }

    /**
     * @param responseTeam the responseTeam to set
     */
    public void setResponseTeam(boolean responseTeam) {
        this.responseTeam = responseTeam;
    }

    /**
     * @return the visibility
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * @param visibility the visibility to set
     */
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }


    

}
