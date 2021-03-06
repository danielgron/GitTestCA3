/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import entity.Department;
import entity.RedCrossLevel;
import entity.Resource;
import entity.StaffedEvent;
import entity.watches.SamaritFunctionsOnWatch;
import entity.watches.SamaritWatch;
import enums.Status;
import facades.WatchFacade;
import facades.WatchFlowFacade;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Daniel
 */
@Path("watchflow")
@RolesAllowed("Coordinator")
public class WatchFlowService {

    SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("department", "resources");
    FilterProvider filters = new SimpleFilterProvider().addFilter("samaritFilter", theFilter);

    @Context
    private HttpServletRequest context;

    WatchFlowFacade wff;
    private static WatchFacade wf = new WatchFacade();

    /**
     * Creates a new instance of WatchFlowService
     */
    public WatchFlowService() {
        wff = new WatchFlowFacade();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events/{status}")
    public String getStaffedEventsFromStatus(@PathParam("status") String statusString) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Department d = util.DepartmentDecoder.getDepartmentFromToken(context);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mapper.setDateFormat(df);
        Status statusFromParam = Status.valueOf(statusString);
        List<StaffedEvent> allEventsWithStatus = wff.getAllStaffedEventsWithStatus(statusFromParam, d);
        SimpleBeanPropertyFilter UserFilter = SimpleBeanPropertyFilter.serializeAllExcept("password","department","event");
        FilterProvider userFilterProvider = new SimpleFilterProvider().addFilter("UserFilter", UserFilter);
        String json = mapper.writer(userFilterProvider).writeValueAsString(allEventsWithStatus);
        return json;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("redcrooslevel")
    public String getAllRedCrosslevels() throws JsonProcessingException {
        List<RedCrossLevel> levels = wff.getRedCrossLevels();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(levels);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("events/updatequantity")
    public String updateQuantityForEvent(String jsonEvent) throws IOException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mapper.setDateFormat(df);
        StaffedEvent event = mapper.readValue(jsonEvent, StaffedEvent.class);
        Map<String, Integer> map = event.getLevelsQuantity();
        List<Resource> resources = event.getResources();
        Integer id = event.getId();
        wff.updateQuantityForEvent(id, map);
        StaffedEvent eventAfterUpdates = wff.updateResources(id, resources);
        wff.updateStatusOfStaffedEvent(id, Status.Pending);
        SimpleBeanPropertyFilter UserFilter = SimpleBeanPropertyFilter.serializeAllExcept("password","department","event");
        FilterProvider userFilterProvider = new SimpleFilterProvider().addFilter("UserFilter", UserFilter);
        return mapper.writer(userFilterProvider).writeValueAsString(eventAfterUpdates);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("functions")
    public String getAllFunctionsForDepartment() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Department d = util.DepartmentDecoder.getDepartmentFromToken(context);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mapper.setDateFormat(df);
        return mapper.writeValueAsString(d.getWatchFunctions());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String registerWatches(@PathParam("id") int id, String json) throws IOException {
        System.out.println(json);
        ObjectMapper mapper = new ObjectMapper();
        
        List<SamaritWatch> allWatches = mapper.readValue(json, new TypeReference<List<SamaritWatch>>(){});
//        List<Samarit> samarits = mapper.readValue(json, new TypeReference<List<Samarit>>(){});

        wf.setWatchesForSamarits(allWatches, id);
//          String returnJson = mapper.writer(filters).writeValueAsString(samarits);
        return json;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("functions")
    public String saveSamaritFunctionsForEvent(String jsonEvent) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mapper.setDateFormat(df);
        StaffedEvent event = mapper.readValue(jsonEvent, StaffedEvent.class);
        List<SamaritFunctionsOnWatch> functionsForThisWatch = event.getWatchFunctions();
        StaffedEvent eventAfterUpdates = wff.updateWatchFunctionsForEvent(functionsForThisWatch, event.getId());
        SimpleBeanPropertyFilter UserFilter = SimpleBeanPropertyFilter.serializeAllExcept("password","department","event");
        FilterProvider userFilterProvider = new SimpleFilterProvider().addFilter("UserFilter", UserFilter);
        return mapper.writer(userFilterProvider).writeValueAsString(eventAfterUpdates);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("updatecateringandcomment")
    public String updateCateringAndComment(String jsonEvent) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mapper.setDateFormat(df);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        StaffedEvent event = mapper.readValue(jsonEvent, StaffedEvent.class);
        wff.updateCateringComment(event.getId(), event.getCatering());
        StaffedEvent updatedEvent = wff.updateCoordinatorComment(event.getId(), event.getCoordinatorcomment());
        SimpleBeanPropertyFilter UserFilter = SimpleBeanPropertyFilter.serializeAllExcept("password","department","event");
        FilterProvider userFilterProvider = new SimpleFilterProvider().addFilter("UserFilter", UserFilter);
        return mapper.writer(userFilterProvider).writeValueAsString(updatedEvent); 
    }

}
