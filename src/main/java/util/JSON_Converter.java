/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entity.Event;
import entity.Samarit;
import entity.User;

/**
 *
 * @author danie
 */
public class JSON_Converter {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public Samarit parseSamarit(String jsonString){
        Samarit s = null;
        
        
        return s;
    }
    
    public JsonObject parseEvent(Event event){
        JsonObject jEvent = new JsonObject();
        jEvent.addProperty("id", event.getId());
        jEvent.addProperty("title", event.getEventName());
        jEvent.addProperty("start", event.getDateStart().toString());
        jEvent.addProperty("end", event.getDateEnd().toString());
        jEvent.addProperty("allDay", Boolean.FALSE);
        return jEvent; 
    }
}
