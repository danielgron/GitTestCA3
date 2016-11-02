/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
/**
 *
 * @author Daniel
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DT", discriminatorType = DiscriminatorType.CHAR)
public class Admin extends User{

    public Admin() {
    }

    public Admin(String email, String password) {
        super(email, password);
    }

    
}
