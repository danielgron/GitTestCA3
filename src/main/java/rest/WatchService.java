/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import entity.SamaritCalenderEvent;
import facades.WatchFacade;
import java.io.IOException;
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
import util.WatchConverter;

/**
 * REST Web Service
 *
 * @author dennisschmock
 */
@Path("watch")

public class WatchService {

    @Context
    private UriInfo context;
    private static WatchConverter wc = new WatchConverter();
    private static Gson gson = new Gson();
    private static WatchFacade wf = new WatchFacade();
    private static JsonFactory factory = new JsonFactory();
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Creates a new instance of WatchService
     */
    public WatchService() {
    }

    /**
     * Retrieves representation of an instance of rest.WatchService
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getWatches() {
        return gson.toJson("test");
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void setWatch(@PathParam("id") String id, String sWatch) {
        SamaritCalenderEvent sw = null;

        try {
            sw = mapper.readValue(sWatch, SamaritCalenderEvent.class);
            sw.getSamarit().setUserName(sWatch);

        } catch (IOException ex) {
            Logger.getLogger(WatchService.class.getName()).log(Level.SEVERE, null, ex);
        }
        wf.addUnavailForWatch(sw);

    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getWatchesForSamarit(@PathParam("id") String id) {
        List<SamaritCalenderEvent> watches = null;
        watches = wf.getWatchesForUser(id);
        String json = "fail";
        try {
            json =  mapper.writeValueAsString(watches);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(WatchService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

}
