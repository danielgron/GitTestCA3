/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.Gson;
import entity.watches.SamaritOccupied;
import facades.WatchFacade;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import util.JacksonFilter;
import util.WatchConverter;
import javax.ws.rs.container.ContainerRequestContext;


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

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void setWatch(@PathParam("id") String id, String sWatch) {
        SamaritOccupied sw = null;

        try {

            mapper = new ObjectMapper();
            sw = mapper.readValue(sWatch, SamaritOccupied.class);
            sw.getSamarit().setUserName(id);

        } catch (IOException ex) {
            Logger.getLogger(WatchService.class.getName()).log(Level.SEVERE, null, ex);
        }
        wf.addUnavailForWatch(sw);

    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getWatchesForSamarit(@PathParam("id") String id) {
            //  String token = request.getHeaderString("Authorization").substring("Bearer ".length());

        List<SamaritOccupied> watches = null;
        watches = wf.getWatchesForUser(id);
        String json = "{fail}";
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            mapper.setDateFormat(df);
            SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("samarit");
            FilterProvider filters = new SimpleFilterProvider().addFilter("samaritFilter", theFilter);

            json = mapper.writer(filters).writeValueAsString(watches);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(WatchService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

}
