package fr.lamsoent.glucoseapplication.REST;

import fr.lamsoent.glucoseapplication.model.ActiviteModel;
import fr.lamsoent.glucoseapplication.model.CapteurModel;
import fr.lamsoent.glucoseapplication.model.DonneeModel;
import fr.lamsoent.glucoseapplication.model.UtilisateurModel;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Capteur;
import fr.lamsoent.glucoseapplication.pojo.Donnee;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import fr.lamsoent.glucoseapplication.websocket.Graphique;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Init;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/activite")
public class ActiviteREST {

    @EJB
    private ActiviteModel activiteModel;
    @EJB
    private CapteurModel capteurModel;

    @EJB
    private DonneeModel donneeModel;
    
    @Inject
    private Graphique graphiqueWebServer;

    @EJB
    private UtilisateurModel utilisateurModel;
    @GET
    public Response deploy(){
        return Response.ok("deploiement ok").build();
    }

    @GET
    @Path("/read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response lireActivite(@QueryParam("idActivite") int idActivite){
        Activite actSelect=activiteModel.read(idActivite);
    return Response.accepted(actSelect).build();
    }


    @GET
    @Path("/getDonneesAct")
    public Response getData(@QueryParam("idActivite") int idActivite){
               List<Donnee> datas = donneeModel.donneeListWithIdActivite(idActivite);
        return Response.accepted(datas).build();
    }

    //Créer
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createActivite(@QueryParam("dateDebut") String dateDebut,
                                 @QueryParam("mac") String mac) {

        List<Capteur> lc = capteurModel.read();
        List<Utilisateur> lu = utilisateurModel.read();

        Capteur c =null;
        Utilisateur u =null;

        for (Capteur capteur : lc) {
            if(capteur.getMac() == null || capteur.getMac().isEmpty()){
                continue;
            }

            if (capteur.getMac().equalsIgnoreCase(mac)) {
                System.out.println("Capteur trouver " + capteur.getMac());
                c = capteur;
            }
        }
        if (c==null){
            System.out.println("Le capteur c est nulle");
            return Response.noContent().build();
        }


        for (Utilisateur user : lu) {
            if ( user.getCapteur() != null && user.getCapteur().getMac().equalsIgnoreCase(c.getMac())) {
                System.out.println("Utilisateur trouver " + user.getNom());
                System.out.println("Capteur lier utilisateur : " + user.getCapteur().getMac());
                System.out.println("Capteur :" + c.getMac());
                u=user;
            }
        }
        if (u==null){
            System.out.println("L' utilisateur u est nulle");
            return Response.noContent().build();
        }
        Activite a1 = new Activite();
        a1.setAlive(true);
        a1.setUtilisateur(u);
        a1.setCapteur(c);
        a1.setDateDebut(formatDate(dateDebut));
        a1=activiteModel.update(a1);
        a1 = activiteModel.update(a1);
        System.out.println(a1.getId());
        return  Response.ok(a1.getId()).build();
    }

    public Date formatDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        try {
            Date date1 = format.parse(date);
            return date1;
        }
        catch (Exception e){
            return new Date();
        }
    }

    @POST
    @Path("/envoiDonnee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response envoyerDonnees(@QueryParam("dateData") String dateDebut,
                                @QueryParam("tauxGlucose") String tauxGlucose,
                                @QueryParam("idActivite") int idActivite){

        if(activiteModel.read(idActivite)== null){

            return Response.serverError().build();
        }
        Donnee donnee = new Donnee();
        donnee.setDateData(formatDate(dateDebut));
        donnee.setGlucose(tauxGlucose);
        donnee.getActivite().setId(idActivite);


        donnee = donneeModel.update(donnee);
        graphiqueWebServer.sendMessage(donnee);

        return Response.ok().build();
    }
}
