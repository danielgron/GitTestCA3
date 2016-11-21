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
