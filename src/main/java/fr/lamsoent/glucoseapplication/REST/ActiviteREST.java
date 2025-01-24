package fr.lamsoent.glucoseapplication.REST;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/activite")
public class ActiviteREST {
    @GET
    public Response deploy(){
        return Response.ok("deploiement ok").build();
    }
}
