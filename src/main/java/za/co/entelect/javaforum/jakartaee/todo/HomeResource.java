package za.co.entelect.javaforum.jakartaee.todo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/")
public class HomeResource {
        @GET
        @Produces("text/plain")
        public String hello() {
            return "Home";
        }
    }

