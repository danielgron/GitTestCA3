/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Department;
import entity.RedCrossLevel;
import entity.StaffedEvent;
import enums.Status;
import facades.WatchFlowFacade;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
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

    @Context
    private HttpServletRequest context;
    
    
    WatchFlowFacade wff;

    /**
     * Creates a new instance of WatchFlowService
     */
    public WatchFlowService() {
        wff = new WatchFlowFacade();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events/{status}")
    public String getStaffedEventsFromStatus(@PathParam("status") String statusString) throws Exception{
       ObjectMapper mapper = new ObjectMapper();
       Department d = util.DepartmentDecoder.getDepartmentFromToken(context);
       DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
       mapper.setDateFormat(df);
       Status statusFromParam = Status.valueOf(statusString);
       List<StaffedEvent> allEventsWithStatus =  wff.getAllStaffedEventsWithStatus(statusFromParam, d);
       return mapper.writeValueAsString(allEventsWithStatus);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("redcrooslevel")
    public String getAllRedCrosslevels() throws JsonProcessingException{
        List<RedCrossLevel> levels = wff.getRedCrossLevels();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(levels);
    }
}
