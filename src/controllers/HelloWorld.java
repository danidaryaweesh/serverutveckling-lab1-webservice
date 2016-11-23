package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by dani on 2016-11-21.
 */

@Path("/hello")
public class HelloWorld {

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello World";
    }

}
