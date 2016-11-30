
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@JsonFilter("eventFilter")
public class EventFilter{
    SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("samarit");
    FilterProvider filters = new SimpleFilterProvider().addFilter("samaritFilter", theFilter);
}