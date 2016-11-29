/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Request;
import facades.RequestFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author dennisschmock
 */
@Path("request")
public class RequestService {

    @Context
    private UriInfo context;
    private RequestFacade rf = new RequestFacade();
    private static JsonFactory factory = new JsonFactory();
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Creates a new instance of RequestResource
     */
    public RequestService() {
    }

    /**
     * Retrieves representation of an instance of rest.RequestService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRequests() {
        //TODO return proper representation object
        List<Request> requests = rf.getRequests();
        try {
            return  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requests);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(RequestService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

     /**
     * Retrieves representation of an instance of rest.RequestService
     * @param id
     * @return an instance of java.lang.String
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRequest(@PathParam("id") String id) throws JsonProcessingException {
        
        //TODO return proper representation object
        int idFromString = Integer.parseInt(id);
        Request request = rf.getRequest(idFromString);
        try {
            return  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        } catch (JsonProcessingException ex) {
            log.Log.writeErrorMessageToLog("Error REST get an Request: " +ex.getMessage());
            throw ex;
        }
    }
    /**
     * PUT method for updating or creating an instance of RequestService
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
