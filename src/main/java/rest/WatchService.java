/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
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
    @Consumes(MediaType.APPLICATION_JSON)
    public void setWatch(String date){
        System.out.println("test"+date);
    };
            

    
}
