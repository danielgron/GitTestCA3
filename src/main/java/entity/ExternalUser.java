/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
/**
 * This Class represent, someone who will
 * be assigned to a vagtkort, but not have all of the same attributes.
 * @author Daniel
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DT", discriminatorType = DiscriminatorType.CHAR)
public class ExternalUser extends User{

    private String firstName;
    private String lastName;
    private String phone;
    private String redCroosLevel; // f.eks Samarit, eller Teamleder
    private String medicalLevel; // f.eks. Medic, Medic 2 eller læge. (Vil være intet for mange)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expiration; // Might not be needed!
//  private List<VagtKort> vagtKorts; // Not implemented yet!

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRedCroosLevel() {
        return redCroosLevel;
    }

    public void setRedCroosLevel(String redCroosLevel) {
        this.redCroosLevel = redCroosLevel;
    }

    public String getMedicalLevel() {
        return medicalLevel;
    }

    public void setMedicalLevel(String medicalLevel) {
        this.medicalLevel = medicalLevel;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
   
    //Inherrited:
    @Override
    public void removeRoleFromUser(User_Role role) {
        super.removeRoleFromUser(role); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUserName(String userName) {
        super.setUserName(userName); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUserName() {
        return super.getUserName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPassword() {
        return super.getPassword(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getRolesAsStrings() {
        return super.getRolesAsStrings(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addRoleToUser(User_Role role) {
        super.addRoleToUser(role); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
