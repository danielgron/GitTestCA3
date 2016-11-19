/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Request;

/**
 *
 * @author Dennis
 */
public class ValidatorObject {
    private boolean validJson;
    private Request request;

    /**
     * @return the validJson
     */
    public boolean isValidJson() {
        return validJson;
    }

    /**
     * @param validJson the validJson to set
     */
    public void setValidJson(boolean validJson) {
        this.validJson = validJson;
    }

    /**
     * @return the request
     */
    public Request getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(Request request) {
        this.request = request;
    }
    
    
}
