package rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import entity.user.Samarit;
import entity.WatchFunction;
import facades.CoordinatorFacade;
import facades.UserFacade;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import security.IUser;
import util.JSON_Converter;
import util.SamaritConverter;

@Path("coordinator")
@RolesAllowed("Coordinator")
public class CoordinatorService {

    private static CoordinatorFacade cf = new CoordinatorFacade();
    private static JsonFactory factory = new JsonFactory();
    private static UserFacade uf = new UserFacade();
    private static ObjectMapper mapper = new ObjectMapper();
    SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("password","department");
    FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", theFilter);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSomething() {
        String now = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
        return "{\"message\" : \"REST call accesible by only authenticated ADMINS\",\n" + "\"serverTime\": \"" + now + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{eventId}")
    public String getFreeSamarites(@PathParam("eventId") String id) throws Exception {
        try {
            List<Samarit> sams = cf.getAvailableSamaritesFromEventId(Integer.parseInt(id));
            if (sams.size() > 0) {

//                return mapper.writer(filters).writeValueAsString(sams);
                return SamaritConverter.samConverter(sams);
            } else {
                return "[]"; // returns an empty array if the size of the list is 0
            }
        } catch (Exception ex) {
            log.Log.writeToLog("Failed at Getting Availble Samarits: " + ex);
            throw ex;
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String postNewUser(String json) {
        Samarit s = JSON_Converter.parseSamarit(json);

        s = cf.addNewSamarit(s);

        return JSON_Converter.jsonFromSamarit(s);
    }

    @GET
    @Path("user/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSingleUser(@PathParam("userid") String id) throws Exception {
        try {
            IUser user = uf.getUserByUserId(id);
            ObjectMapper mapper = new ObjectMapper();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
            mapper.setDateFormat(df);
            SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("password");
            FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", theFilter);
            return mapper.writer(filters).writeValueAsString(user);
        } catch (Exception ex) {
            log.Log.writeToLog("Exception When Creating JSON Object single event: " + ex);
            throw ex;
        }
    }
    

    @GET
    @Path("functions/{department}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getWatchFunctionsFromDepartment(@PathParam("department") String department) throws Exception {
        try {
            List<WatchFunction> allFromDepartment = cf.getWatchFunctionsFromDepartment(department);
            ObjectMapper mapper = new ObjectMapper();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
            mapper.setDateFormat(df);
            return mapper.writeValueAsString(allFromDepartment);
        } catch (Exception e) {
            log.Log.writeToLog("Exception in REST.getWatchFunctionsFromDepartment" + e);
            throw e;
        }
    }

    @POST
    @Path("functions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createNewFunctionForDepartment(String functionJSON) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            WatchFunction wf = mapper.readValue(functionJSON, WatchFunction.class);
            WatchFunction newlyCreated = cf.createNewFunctionForDepartment(wf);
            return mapper.writeValueAsString(newlyCreated);
        } catch (Exception e) {
            log.Log.writeToLog("Exception in REST.createNewfunctionForDepartment" + e);
            throw e;
        }
    }

}
