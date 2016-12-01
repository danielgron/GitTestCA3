/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startup;

import entity.Request;
import entityconnection.EntityConnector;
import enums.RequestStatus;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author danie
 */
public class NonNuking {

    public static void main(String[] args) {
        EntityManager em = EntityConnector.getEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("Select r from Request r", Request.class);
        List<Request> resultList = q.getResultList();
        for (Request request : resultList) {
            if (Math.random() < 0.33) {
                request.setRequestStatus(RequestStatus.RECIEVED);
            } else if (Math.random() < 0.33) {
                request.setRequestStatus(RequestStatus.SENT);
            } else {
                request.setRequestStatus(RequestStatus.APPROVED);
            }
        }
        em.getTransaction().commit();
    }
}
