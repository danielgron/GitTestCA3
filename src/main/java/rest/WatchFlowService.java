/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Department;
import entity.StaffedEvent;
import enums.Status;
import facades.WatchFlowFacade;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.*;
import javax.ws.rs.core.Request;

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
    @Path("events/{status}")
    public String getStaffedEventsFromStatus(@PathParam("status") String statusString) throws Exception{
       ObjectMapper mapper = new ObjectMapper();
       Department d = util.DepartmentDecoder.getDepartmentFromToken(context);
       DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
       mapper.setDateFormat(df);
       Status statusFromParam = Status.valueOf(statusString);
       List<StaffedEvent> allEventsWithStatus =  wff.getAllStaffedEventsWithStatus(statusFromParam, d);
       return mapper.writeValueAsString(allEventsWithStatus);
    }
}
