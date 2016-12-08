/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.RedCrossLevel;
import entity.WatchFunction;
import entity.user.Samarit;
import java.util.List;

/**
 *
 * @author dennisschmock
 */
public class SamaritConverter {

    private static Gson gson = new Gson();

    public static String samConverter(List<Samarit> samarits) {
        JsonArray jsonSamarits = new JsonArray();
        for (Samarit samarit : samarits) {
            jsonSamarits.add(convertSamarit(samarit));
        }
        return gson.toJson(jsonSamarits);
    }

    private static JsonObject convertSamarit(Samarit sam) {
        JsonObject jsonSam = new JsonObject();
        jsonSam.addProperty("userName", sam.getUserName());
        jsonSam.addProperty("firstName", sam.getFirstName());
        jsonSam.addProperty("lastName", sam.getLastName());
        if (sam.getRedCrossLevel() != null && sam.getRedCrossLevel().size() > 0) {
            JsonArray ja = new JsonArray();
            for (RedCrossLevel redCrossLevel : sam.getRedCrossLevel()) {
                JsonObject jo = new JsonObject();
                jo.addProperty("level", redCrossLevel.getLevel());
                ja.add(jo);

            }
            jsonSam.add("redCrossLevel", ja);
        }
        if (sam.getWatchFunctions() != null && sam.getWatchFunctions().size() > 0) {
            JsonArray ja = new JsonArray();
            for (WatchFunction watchFunction : sam.getWatchFunctions()) {
                                JsonObject jo = new JsonObject();

                jo.addProperty("functionName",watchFunction.getFunctionName());
                jo.addProperty("id", watchFunction.getId());
                ja.add(jo);

            }
            jsonSam.add("watchFunctions", ja);

        }
        JsonObject department = new JsonObject();
        department.addProperty("nameOfDepartment", sam.getDepartment().getNameOfDepartment());
        jsonSam.add("department", department);
        return jsonSam;
    }
}
