package fr.lamsoent.glucoseapplication.REST;

import fr.lamsoent.glucoseapplication.model.CapteurModel;
import fr.lamsoent.glucoseapplication.model.CapteurModel;
import fr.lamsoent.glucoseapplication.model.UtilisateurModel;
import fr.lamsoent.glucoseapplication.pojo.Capteur;
import fr.lamsoent.glucoseapplication.pojo.Capteur;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response; 

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/capteur")
public class CapteurREST {


    @EJB
    private CapteurModel capteurModel;
    @GET
    public Response deploy(){
        return Response.ok("deploiement ok").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/read")
    public Response read(){
        List<Capteur> lc = capteurModel.read();
        return Response.ok(lc).build();
    }

}
