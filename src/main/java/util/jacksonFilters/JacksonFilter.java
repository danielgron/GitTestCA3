/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 *
 * @author dennisschmock
 */
@JsonFilter("samaritFilter")
public class JacksonFilter {

    SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("samarit");
    FilterProvider filters = new SimpleFilterProvider().addFilter("samaritFilter", theFilter);

}
