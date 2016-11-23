package httpErrors;

import javax.ws.rs.NotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import exceptions.DateNullException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DateNullExceptionMapper implements ExceptionMapper<DateNullException> {

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  @Context
  ServletContext context;

  @Override
  public Response toResponse(DateNullException ex) {
    JsonObject error = new JsonObject();
    JsonObject errorDetail = new JsonObject();
    errorDetail.addProperty("code", 500);
    errorDetail.addProperty("message", "An error occured on the server trying to manage an event in the database without a starttime");
    error.add("error", errorDetail);
    return Response.status(500).entity(GSON.toJson(error)).type(MediaType.APPLICATION_JSON).build();
  }
}
