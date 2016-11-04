package rest;

import entity.Samarit;
import facades.CoordinatorFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import util.JSON_Converter;

@Path("coordinator")
@RolesAllowed("Coordinator")
public class Coordinator {
    private static CoordinatorFacade cf  = new CoordinatorFacade();
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getSomething(){
    String now = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
    return "{\"message\" : \"REST call accesible by only authenticated ADMINS\",\n"+"\"serverTime\": \""+now +"\"}"; 
  }
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public String postNewUser(String json){
      Samarit s= JSON_Converter.parseSamarit(json);
       
            s= cf.addNewSamarit(s);

        
      return JSON_Converter.jsonFromSamarit(s);
  }
 
}
