package rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Samarit;
import facades.CoordinatorFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import util.JSON_Converter;

@Path("coordinator")
@RolesAllowed("Coordinator")
public class Coordinator {
    private static CoordinatorFacade cf  = new CoordinatorFacade();
    private static JsonFactory factory = new JsonFactory();
    private static ObjectMapper mapper = new ObjectMapper();
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getSomething(){
    String now = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
    return "{\"message\" : \"REST call accesible by only authenticated ADMINS\",\n"+"\"serverTime\": \""+now +"\"}"; 
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{eventId}")
  public String getFreeSamarites(@PathParam("eventId") String id){
      List<Samarit> sams = cf.getAvailableSamaritesFromEventId(Integer.parseInt(id));
      String json = "fail";
        if (sams.size()>0){
        try {
            json =  mapper.writeValueAsString(sams);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
       
        return json;
  }
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public String postNewUser(String json){
      Samarit s= JSON_Converter.parseSamarit(json);
       
            s= cf.addNewSamarit(s);

        
      return JSON_Converter.jsonFromSamarit(s);
  }
 
}
