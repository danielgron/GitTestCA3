/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Department;
import entity.Samarit;
import entity.User;
import entityconnection.EntityConnector;
import javax.persistence.EntityManager;
import log.Log;

/**
 *
 * @author danie
 */
public class CoordinatorFacade {
    
    DepartmentFacade df = new DepartmentFacade();
    
    public Samarit addNewSamarit(Samarit s){
        //s.setDepartment(df.getDepartment(s.getDepartment().getNameOfDepartment()));
        Department d = df.getDepartment(s.getDepartment().getNameOfDepartment());
        d.addUser(s);
        Log.writeToLog("Adding new samarite");
        EntityManager em = EntityConnector.getEntityManager();
        //if (s.getRedCroosLevel()==null) throw new NoRedCrossLevelException();
        try{
            em.getTransaction().begin();
            em.merge(d);
            em.persist(s);
            em.getTransaction().commit();
            Log.writeToLog("New samarite added");
        }
        catch(Exception e){
            Log.writeToLog("Exception encountered while adding samarite.");
            Log.writeToLog(e.getMessage());
        }
        finally{
            em.close();
        }
        return s;
    }
}
