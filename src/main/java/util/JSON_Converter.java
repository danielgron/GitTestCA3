/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entity.Department;
import entity.Event;
import entity.Samarit;
import entity.User;
import log.Log;

/**
 *
 * @author danie
 */
public class JSON_Converter {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static Samarit parseSamarit(String jsonString){
        Samarit s = null;
        Department d = null;
        s=gson.fromJson(jsonString, Samarit.class);
        d = s.getDepartment();
        d.addUser(s);
        
        return s;
    }
    public static String jsonFromSamarit(Samarit s){
        // This solution creates a new department with the correct name to avoid a circular reference between Samarit and Department
        // This might not be the final solution as any attributes that is not the name would be lost!
        Department unCircular = new Department();
        unCircular.setNameOfDepartment(s.getDepartment().getNameOfDepartment());
        s.setDepartment(unCircular);
        Log.writeToLog("Return samarite JSON: "+gson.toJson(s));
        try{
        return gson.toJson(s);
        }
        catch(Exception e){
            Log.writeToLog("Exception in json from samarite: "+e.getMessage());
            return null;
        }
        
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

