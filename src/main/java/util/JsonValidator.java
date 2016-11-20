/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import entity.Request;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.JsonObject;

/**
 *
 * @author dennisschmock
 */
public class JsonValidator {

    private static  Gson gson = new Gson();
    private static String json = "{\n"
            + "  \"id\" : 1,\n"
            + "  \"eventName\" : \"Julefrokost\",\n"
            + "  \"numberGuests\" : 400,\n"
            + "  \"agegroup\" : \"Børn\",\n"
            + "  \"eventDate\" : 1479423600000,\n"
            + "  \"venue\" : \"Vega\",\n"
            + "  \"street\" : \"Julevej\",\n"
            + "  \"zip\" : 1300,\n"
            + "  \"doorsopen\" : 1479471503000,\n"
            + "  \"eventstart\" : 1479471503000,\n"
            + "  \"eventend\" : 1479471503000,\n"
            + "  \"watchStart\" : 1479471503000,\n"
            + "  \"catering\" : \"Klares\",\n"
            + "  \"treatmentfacility\" : true,\n"
            + "  \"comments\" : \"Test\",\n"
            + "  \"contact\" : {\n"
            + "    \"id\" : 2,\n"
            + "    \"name\" : \"Hans Jensen\",\n"
            + "    \"phone\" : \"44556677\",\n"
            + "    \"mail\" : \"dennis@schmock.eu\"\n"
            + "  },\n"
            + "  \"invoice\" : {\n"
            + "    \"id\" : 2,\n"
            + "    \"company\" : \"Google\",\n"
            + "    \"cvr\" : \"55667788\",\n"
            + "    \"name\" : \"test\",\n"
            + "    \"street\" : \"Somestreet\",\n"
            + "    \"zip\" : \"2222\"\n"
            + "  },\n"
            + "  \"medics\" : true,\n"
            + "  \"ambulance\" : true,\n"
            + "  \"emergencyOffice\" : true,\n"
            + "  \"stretcherTeam\" : true,\n"
            + "  \"responseTeam\" : true,\n"
            + "  \"visibility\" : \"visi\"\n"
            + "}";

    public static void main(String[] args) {

        validateRequest(json);
    }

    private static JsonFactory factory = new JsonFactory();
    private static ObjectMapper mapper = new ObjectMapper();

    public static String validateRequest(String json) {
        Request request;
        ValidatorObject jsonObj = new ValidatorObject();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            request = mapper.readValue(json, Request.class);
            jsonObj.setValidJson(true);
            jsonObj.setRequest(request);
        } catch (IOException ex) {
            Logger.getLogger(JsonValidator.class.getName()).log(Level.SEVERE, null, ex);
            jsonObj.setValidJson(false);
        }
        

  
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JsonValidator.class.getName()).log(Level.SEVERE, null, ex);
                    return ex.getMessage();

        }
       
    }

    //public static createRequest
}
