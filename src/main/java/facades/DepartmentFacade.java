/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Department;
import entityconnection.EntityConnector;
import javax.persistence.EntityManager;
import log.Log;

/**
 *
 * @author danie
 */
public class DepartmentFacade {
    public Department getDepartment(String departmentName){
        Log.writeToLog("Getting department corresponding to " + departmentName + " from database");
        Department d = null;
        EntityManager em = EntityConnector.getEntityManager();
        try{
            d = em.find(Department.class, departmentName);
        }
        catch(Exception e){
            Log.writeToLog("Exception encountered while extracting department");
            Log.writeToLog(e.getMessage());
        }
        finally{
            em.close();
        }
        return d;
    }
    


}
