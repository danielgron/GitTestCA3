/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Resource;
import facades.CoordinatorFacade;
import facades.EventFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Daniel
 */
@Path("Resource")
@RolesAllowed("Coordinator")
public class ResourceResource {
private static JsonFactory factory = new JsonFactory();
private static EventFacade ef = new EventFacade();
private static CoordinatorFacade cf = new CoordinatorFacade();
private static ObjectMapper mapper = new ObjectMapper();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ResourceResource
     */
    public ResourceResource() {
    }

    /**
     * Retrieves representation of an instance of rest.ResourceResource
     * @param id
     * @return an instance of java.lang.String
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @GET
    @Path("{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAvailable(@PathParam("eventId") String id) throws JsonProcessingException {
        
        List<Resource> eventResources = ef.getEventResources(Integer.parseInt(id));
    try {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(eventResources);
    } catch (JsonProcessingException ex) {
        Logger.getLogger(ResourceResource.class.getName()).log(Level.SEVERE, null, ex);
        throw ex;
    }
    }
    
    @POST
    @Path("changeResShift/{eventId}/{resId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String changeResShift(@PathParam("eventId") String eventId, @PathParam("resId") String resId) throws JsonProcessingException {
        
        
        
        
        List<Resource> eventResources = ef.getEventResources(Integer.parseInt(eventId));
    try {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(eventResources);
    } catch (JsonProcessingException ex) {
        Logger.getLogger(ResourceResource.class.getName()).log(Level.SEVERE, null, ex);
        throw ex;
    }
    }

    /**
     * PUT method for updating or creating an instance of ResourceResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
