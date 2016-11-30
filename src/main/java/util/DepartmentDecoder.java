/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import entity.Department;
import entity.user.Samarit;
import entityconnection.EntityConnector;
import java.text.ParseException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Request;
import security.Secret;

/**
 *
 * @author Daniel
 */
public class DepartmentDecoder {
    

    public static Department getDepartmentFromToken(HttpServletRequest request) throws Exception {
        EntityManager em = EntityConnector.getEntityManager();
        try{
            
           String token =  request.getHeader("Authorization");
           String[] spiltedToken = token.split(" ");
           String usernameFromToken = getUsernameFromToken(spiltedToken[1]);
           TypedQuery<Samarit> q1 = em.createQuery("select s from Samarit s where s.userName = :username",Samarit.class);
           q1.setParameter("username", usernameFromToken);
           Samarit s = q1.getSingleResult();
           Department d = s.getDepartment();
           return d;
            
        }
        catch(Exception e){
            System.out.println("Error in Department Decoder:  " + e.getMessage());
            throw e;
        }
    }
    
    
   private static String getUsernameFromToken(String token) throws ParseException, JOSEException {

    SignedJWT signedJWT = SignedJWT.parse(token);
    JWSVerifier verifier = new MACVerifier(Secret.SHARED_SECRET);
    
    if (signedJWT.verify(verifier)) {
        String userName = signedJWT.getJWTClaimsSet().getSubject();
        System.out.println("Username extracted: " + userName);
      return userName;
    } else {
      throw new JOSEException("Firm is not verified.");
    }
  }
    
}
