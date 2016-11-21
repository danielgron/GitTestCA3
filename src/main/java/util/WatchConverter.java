/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.SamaritCalenderEvent;

/**
 *
 * @author dennisschmock
 */
public class WatchConverter {
    private static Gson gson = new Gson();
    
    /**
     * This method is a Json Wrapper. It takes SamaritObject and converts
     * it to a JsonObject. 
     * It deals with a watch of type: Unavailable
     * @param watch
     * @return
     */
    public JsonObject jsonWatchUnavailable(SamaritCalenderEvent watch){
        JsonObject jsonWatch = new JsonObject();
        
        if(watch !=null){
            
            
            
            
            
        }
        
        
        return jsonWatch;
    }
    
}
