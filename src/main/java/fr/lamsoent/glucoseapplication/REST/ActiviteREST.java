package fr.lamsoent.glucoseapplication.REST;

import fr.lamsoent.glucoseapplication.controller.UtilisateurController;
import fr.lamsoent.glucoseapplication.model.*;
import fr.lamsoent.glucoseapplication.pojo.*;
import fr.lamsoent.glucoseapplication.websocket.Graphique;
import fr.lamsoent.glucoseapplication.websocket.Localiser;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Init;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/activite")
public class  ActiviteREST {

    @EJB
    private ActiviteModel activiteModel;
    @EJB
    private CapteurModel capteurModel;

    @EJB
    private DonneeModel donneeModel;

    @EJB
    private AlerteModel alerteModel ;

    @Inject
    private Graphique graphiqueWebServer;

    @Inject
    private Localiser localiserWebsocket;

    @EJB
    private UtilisateurModel utilisateurModel;
    @Named
    @Inject
    private UtilisateurController utilisateurController;


    @GET
    public Response deploy(){
        return Response.ok("deploiement ok").build();
    }

    @GET
    @Path("/read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response lireActivite(@QueryParam("idActivite") int idActivite){
        Activite actSelect=activiteModel.read(idActivite);
        List<Donnee> donnees = donneeModel.donneeListWithIdActivite(idActivite);
        actSelect.setListDonnees(donnees);
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
            if ( user.getCapteur() != null && user.getCapteur().getMac() !=null &&!user.getCapteur().getMac().isEmpty() && user.getCapteur().getMac().equalsIgnoreCase(c.getMac())) {
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
        System.out.println(a1.getId());
        return  Response.ok(a1.getId()).build();
    }


    //Créer
    @POST
    @Path("/createsn")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createActiviteWithSn(@QueryParam("dateDebut") String dateDebut, @QueryParam("sn") String sn) {

        List<Capteur> lc = capteurModel.read();
        List<Utilisateur> lu = utilisateurModel.read();

        Capteur c =null;
        Utilisateur u =null;

        for (Capteur capteur : lc) {
            if(capteur.getNumeroSerie() == null || capteur.getNumeroSerie().isEmpty()){
                continue;
            }

            if (capteur.getNumeroSerie().equalsIgnoreCase(sn)) {
                System.out.println("Capteur trouver " + capteur.getNumeroSerie());
                c = capteur;
            }
        }
        if (c==null){
            System.out.println("Le capteur c est nulle");
            return Response.noContent().build();
        }


        for (Utilisateur user : lu) {
            if ( user.getCapteur() != null && user.getCapteur().getNumeroSerie() !=null &&!user.getCapteur().getNumeroSerie().isEmpty() && user.getCapteur().getNumeroSerie().equalsIgnoreCase(c.getNumeroSerie())) {
                System.out.println("Utilisateur trouver " + user.getNom());
                System.out.println("Capteur lier utilisateur : " + user.getCapteur().getNumeroSerie());
                System.out.println("Capteur :" + c.getNumeroSerie());
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
    @Path("/stopAct")
    @Produces(MediaType.APPLICATION_JSON)
    public Response stopActivite(@QueryParam("dateFin") String dateFin,
                                 @QueryParam("idActivite") int idActivite) {
        System.out.println("Declenchement");
        System.out.println(idActivite);
        System.out.println(dateFin);
        if(activiteModel.read(idActivite) == null)
        {
            System.out.println("act pas trouvé");
            return Response.noContent().build();
        }else {
            System.out.println("actTrouvé");
            Activite activiteStop = activiteModel.read(idActivite);
            activiteStop.setAlive(false);
            activiteStop.setDateFin(formatDate(dateFin));
            activiteModel.update(activiteStop);
            return  Response.ok(activiteStop.getId()).build();
        }
    }

    @POST
    @Path("/envoiDonnee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response envoyerDonnees(@QueryParam("dateData") String dateDebut,
                                   @QueryParam("tauxGlucose") String tauxGlucose,
                                   @QueryParam("idActivite") int idActivite,
                                   @QueryParam("latitude") String latitude,
                                   @QueryParam("longitude") String longitude){



        if(activiteModel.read(idActivite)== null){
            return Response.serverError().build();
        }

        if(activiteModel.read(idActivite).getDateFin() != null){
            return Response.serverError().build();
        }

        if(latitude == null || latitude.isEmpty()){
            latitude = "0";
        }

        if (longitude == null || longitude.isEmpty()){
            longitude = "0";
        }


        Activite activite =activiteModel.read(idActivite);
        if(activite.getCapteur() == null){
            System.out.println("Capteur null");
            return Response.noContent().build();
        }
        activite.getCapteur().setLatitude(latitude);
        activite.getCapteur().setLongitude(longitude);
        capteurModel.update(activite.getCapteur());

        Donnee donnee = new Donnee();
        donnee.setDateData(formatDate(dateDebut));
        donnee.setGlucose(tauxGlucose);
        donnee.getActivite().setId(idActivite);
        donnee = donneeModel.update(donnee);

        if (Double.parseDouble(activite.getUtilisateur().getSeuilMax()) < Double.parseDouble(donnee.getGlucose()) ||
                Double.parseDouble(activite.getUtilisateur().getSeuilMin()) > Double.parseDouble(donnee.getGlucose())){

            Alerte alerte = new Alerte();
            alerte.setUtilisateur(activite.getUtilisateur());
            alerte.setDonnee(donnee);

            if (Double.parseDouble(activite.getUtilisateur().getSeuilMax()) < Double.parseDouble(donnee.getGlucose())){
                alerte.setEstAuDessusTaux(true);
                utilisateurController.sendMail(activite.getUtilisateur().getEmail(),"Alerte hyperGlycémie","Bonjour "+activite.getUtilisateur().getNom()+" "+activite.getUtilisateur().getPrenom()+",\n" +
                        "Votre taux de glucose est supérieur à la limite autorisée de " + activite.getUtilisateur().getSeuilMax() + " mg/dL "+ "("+donnee.getGlucose()+")" +". Veuillez prendre les mesures nécessaires pour votre santé.");
            }else if (Double.parseDouble(activite.getUtilisateur().getSeuilMin()) > Double.parseDouble(donnee.getGlucose())){
                alerte.setEstAuDessusTaux(false);
                utilisateurController.sendMail(activite.getUtilisateur().getEmail(),"Alerte hypoGlycémie","Bonjour "+activite.getUtilisateur().getNom()+" "+activite.getUtilisateur().getPrenom()+",\n" +
                        "Votre taux de glucose est inférieur à la limite minimale de " + activite.getUtilisateur().getSeuilMin() +" mg/dL "+ "("+donnee.getGlucose()+")" +". Veuillez prendre les mesures nécessaires pour votre santé.");
            }
            alerteModel.update(alerte);
        }


        graphiqueWebServer.sendMessage(donnee);
        localiserWebsocket.sendMessage(activite.getCapteur());
        return Response.ok(activite.getUtilisateur()).build();
    }
}
