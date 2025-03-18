package rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-17
 */
@Path("/hello-world")
public class HelloWorld {

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, suckers!";
    }

    @GET
    @Produces("text/plain")
    @Path("/{name}")
    public String doGreeting(@PathParam("name") String theName) {
        return "Hello, " + theName;
    }
}
