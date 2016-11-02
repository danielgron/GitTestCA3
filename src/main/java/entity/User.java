package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import security.IUser;
import security.PasswordStorage;
import log.Log;

@Entity
public abstract class User implements IUser, Serializable{
  
  private String password;  //Pleeeeease dont store me in plain text
  @Id
  private String email;
  
  @ManyToMany(cascade = CascadeType.PERSIST)
  List<User_Role> roles = new ArrayList();

//   List<Role> roles = new ArrayList();
    public User() {
    }

  public User(String email, String password) {
    this.email = email;
      try {
          String hashPassword = PasswordStorage.createHash(password);
          this.password = hashPassword;
      } catch (PasswordStorage.CannotPerformOperationException ex) {
          Log.writeToLog("Could not create Password for User");
          this.password = "failed!";
      }
  }
  
  
  public void addRoleToUser(User_Role role){
   if(role != null){
       roles.add(role);
       role.addUserToRole(this); // has to add both refrences
   }
   else{
       //ERROR!!!
   }
  }
    
  @Override
  public List<String> getRolesAsStrings() {
   List<String> temp = new ArrayList<>();
      for (User_Role role : roles) {
          temp.add(role.getRoleName());
      }
      return temp;
  }
      
  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUserName() {
    return email;
  }

  public void setUserName(String userName) {
    this.email = userName;
  }
  
  
  public void removeRoleFromUser(User_Role role){
      roles.remove(role);
      role.removeUserFromRole(this);
  }        
}
