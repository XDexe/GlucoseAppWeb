package fr.lamsoent.glucoseapplication.REST;

import fr.lamsoent.glucoseapplication.model.DonneeModel;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/donnee")
public class DonneeREST {

    @EJB
    private DonneeModel donneeModel;
    @GET
    public Response deploy(){
        return Response.ok("deploiement ok").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/graphiqueComplet")
    public Response graphiqueComplet(@QueryParam("idUtilisateur") int idUtilisateur){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdPersonne(idUtilisateur);
        return Response.accepted(donneeModel.donneeWithUser(utilisateur)).build();
    }
}
