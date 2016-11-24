
package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * This Class Reprcent the different functions (f.eks Chaffør, Vagtleder m.m)
 * Samarits can have on a watch.
 * @author Daniel
 */
@Entity
public class WatchFunction implements Serializable {

    @Id
    private String functionName;
    
    @ManyToMany(mappedBy = "watchFunctions")
    @JsonBackReference(value="samarits-wf")
    private List<Samarit> samaritsThatHasThisFunction;
    
    @ManyToOne
    @JsonBackReference (value="department-wf")
    private Department department;

    public WatchFunction() {
    }

    public WatchFunction(String functionName, Department department) {
        this.functionName = functionName;
        department.addWatchFunction(this); // Department 
    }
    
    

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List<Samarit> getSamaritsThatHasThisFunction() {
        return samaritsThatHasThisFunction;
    }

    public void setSamaritsThatHasThisFunction(List<Samarit> samaritsThatHasThisFunction) {
        this.samaritsThatHasThisFunction = samaritsThatHasThisFunction;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
   public void addSamaritToFunction(Samarit sam) {
       if(samaritsThatHasThisFunction == null){
           samaritsThatHasThisFunction = new ArrayList<>();
       }
       samaritsThatHasThisFunction.add(sam);
   }
   
   
    
}
