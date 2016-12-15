/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import entity.Department;
import entity.Event;
import entity.Request;
import entity.Resource;
import facades.EventFacade;
import facades.RequestFacade;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import util.DateUtils;
import util.DepartmentDecoder;
import util.JSON_Converter;

/**
 * REST Web Service
 *
 * @author dennisschmock
 */
//Nope... Synes ikke at virke
@JsonIgnoreProperties(ignoreUnknown = true)
@Path("request")
public class RequestService {

    @Context
    private HttpServletRequest context;
    private RequestFacade rf = new RequestFacade();
    private static JsonFactory factory = new JsonFactory();
    private static ObjectMapper mapper = new ObjectMapper();
    private static JSON_Converter eJson = new JSON_Converter();
    DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");

    //private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    /**
     * Creates a new instance of RequestResource
     */
    public RequestService() {
        SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("watches");
        FilterProvider filters = new SimpleFilterProvider().addFilter("samaritFilter", theFilter);
    }

    /**
     * Retrieves representation of an instance of rest.RequestService
     *
     * @return an instance of java.lang.String
     * @throws java.lang.Exception
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRequests() throws Exception {
        Department d = null;
        try {
            //TODO return proper representation object
            d = util.DepartmentDecoder.getDepartmentFromToken(context);
        } catch (Exception ex) {
            Logger.getLogger(RequestService.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception();
        }
        List<Request> requests = rf.getRequests(d);
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            mapper.setDateFormat(df);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requests);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(RequestService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retrieves representation of an instance of rest.RequestService
     *
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
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            mapper.setDateFormat(df);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        } catch (JsonProcessingException ex) {
            log.Log.writeErrorMessageToLog("Error REST get an Request: " + ex.getMessage());
            throw ex;
        }
    }

    @GET
    @Path("resource/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getResourceInTimeslot(@PathParam("start") String start, @PathParam("end") String end) throws JsonProcessingException {
        EventFacade ef = new EventFacade();
        //TODO return proper representation object

        DateTime dtStart = new DateTime(start);
        DateTime dtEnd = new DateTime(end);
        Date dStart = dtStart.toDate();
        Date dEnd = dtStart.toDate();

        List<Resource> res = ef.getAvailableResourcesForDates(dStart, dEnd);
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);
        } catch (JsonProcessingException ex) {
            log.Log.writeErrorMessageToLog("Error REST get resourcelist: " + ex.getMessage());
            throw ex;
        }
    }

    @POST
    @Path("requesttoevent")
    @Produces(MediaType.APPLICATION_JSON)
    public String postRequestToEvent(String json) throws JsonProcessingException, Exception {

        EventFacade ef = new EventFacade();
        Request r;
        try {
            r = mapper.readValue(json, Request.class);
            r.setDepartment(DepartmentDecoder.getDepartmentFromToken(context));
            Event e = ef.createEventFromRequest(r);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(e);
        } catch (JsonProcessingException ex) {
            log.Log.writeErrorMessageToLog("Error REST request to event: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.Log.writeErrorMessageToLog("Error REST request to event: " + ex.getMessage());
            throw ex;
        }
    }

    @POST
    @Path("requesttoapproved/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String moveRequestToApproved(@PathParam("id") String id) throws JsonProcessingException, Exception {

        Request r = rf.approveRequest(Integer.parseInt(id));
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(r);
        } catch (JsonProcessingException ex) {
            log.Log.writeErrorMessageToLog("Error REST request approve: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.Log.writeErrorMessageToLog("Error REST request approve: " + ex.getMessage());
            throw ex;
        }
    }

    @POST
    @Path("requesttorejected/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String moveRequestToRejected(@PathParam("id") String id) throws JsonProcessingException, Exception {

        Request r = rf.rejectRequest(Integer.parseInt(id));
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(r);
        } catch (JsonProcessingException ex) {
            log.Log.writeErrorMessageToLog("Error REST request approve: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.Log.writeErrorMessageToLog("Error REST request approve: " + ex.getMessage());
            throw ex;
        }
    }

    /**
     * PUT method for updating or creating an instance of RequestService
     *
     * @param content representation for the resource
     * @return
     * @throws java.io.IOException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String putJson(String content) throws IOException {

        Request r;
        try {
            mapper = new ObjectMapper();
            r = mapper.readValue(content, Request.class);
            Request updateRequest = rf.updateRequest(r);
            return mapper.writeValueAsString(updateRequest);
        } catch  (Exception ex){
            log.Log.writeErrorMessageToLog("Error REST request approve: " + ex.getMessage());
            throw ex;
        }

    }

    /*
     *
     * @param json representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postNewSamaritterRequest(String json) throws IOException {
        try {
            ObjectMapper localMapper = new ObjectMapper();
//            DateFormat df = localMapper.getDateFormat();
//            localMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
//            localMapper.setDateFormat(df);
            Request incomingRequest = localMapper.readValue(json, Request.class);
            Request afterDbUpdate = rf.createRequest(incomingRequest);
            return localMapper.writeValueAsString(afterDbUpdate);
        } catch (IOException ex) {
            log.Log.writeErrorMessageToLog("Error in Rest postSamaritterRequest : " + ex.getMessage());
            throw ex;
        }
    }
}
