/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import entity.Event;
import facades.EventFacade;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import util.DateUtils;
import util.JSON_Converter;

/**
 * REST Web Service
 *
 * @author dennisschmock
 */
@Path("event")
public class EventService {

    private static EventFacade ef = new EventFacade();
    private static JSON_Converter eJson = new JSON_Converter();
    private Gson gson = new Gson();
    private static ObjectMapper mapper = new ObjectMapper();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public EventService() {
    }

    /**
     * Retrieves representation of an instance of rest.EventService
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEvents() {
        List<Event> events = ef.getEvents();
        System.out.println("Works in event");
        return eJson.parseEvents(events);

    }

    /**
     * Retrieves representation of an instance of rest.EventService
     *
     * @return an instance of java.lang.String
     */
    @Path("range")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEventsDateRange(@QueryParam("start") String start, @QueryParam("end") String end) {
        LocalDate localStartDate = LocalDate.parse(start);
        LocalDate localEndDate = LocalDate.parse(end);
        Date startDate = DateUtils.asDate(localStartDate);
        Date endDate = DateUtils.asDate(localEndDate);
        List<Event> events = ef.getEventsDateRange(startDate, endDate);
        System.out.println("Works in event");
        return eJson.parseEvents(events);

    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSingleEvent(@PathParam("id") String id){
      Event event = ef.getEvent(Integer.parseInt(id));
        try {
            return  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(event);
        } catch (JsonProcessingException ex) {
           log.Log.writeToLog("Exception When Creating JSON Object single event: " + ex);
           return null;
        }
        
    }

    /**
     * PUT method for updating or creating an instance of EventService
     *
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String json) {
        Event event;
        System.out.println(json);
//        System.out.println("Got here");
        event = gson.fromJson(json, Event.class);
        System.out.println(event.getName());
        ef.createEvent(event);
    }
}
