package fr.lamsoent.glucoseapplication.controller;
import fr.lamsoent.glucoseapplication.model.ActiviteModel;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Capteur;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ActiviteController implements Serializable {

    @EJB
    private ActiviteModel activiteModel;

    @Inject
    private DonneeController donneeController;

    private Capteur capteur;
    private Activite activite ;
    @Named
    @Inject
    private UtilisateurController utilisateurController;

    public void loadActivite(){
        activite = (Activite) FacesContext.getCurrentInstance().getExternalContext().getFlash().getOrDefault("activite", new Activite());

        if (activite.getId() == 0) {
            activite =null;
            System.out.println("Erreur activité et 0");
        }
    }

    public String getSeuilMax(){
        activite=activiteModel.read(activite.getId());
        PrimeFaces.current().ajax().addCallbackParam("seuilMax", activite.getUtilisateur().getSeuilMax());
        return activite.getUtilisateur().getSeuilMax();
    }

    public String getSeuilMin(){
        activite=activiteModel.read(activite.getId());
        PrimeFaces.current().ajax().addCallbackParam("seuilMin", activite.getUtilisateur().getSeuilMin());
        return activite.getUtilisateur().getSeuilMin();
    }
    public void editActivite() {
        activiteModel.update(this.activite);
        activite = new Activite();
    }

    public void deleteActivite(Activite activite) {
        activiteModel.delete(activite);
    }

    public String accesActivite(Activite activite) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("activite", activite);

        return "graphiqueClient.html?faces-redirect=true";
    }

    public List<Activite> getActiviteUtilisateur(){
        return getActiviteUtilisateur(utilisateurController.getUtilisateur());
    }

    public List<Activite> getActiviteUtilisateur(Utilisateur utilisateur){
        Utilisateur utilAlive=utilisateur;

        if (utilAlive.getIdPersonne()==0){
            return new ArrayList<>();
        }

        List<Activite> list = activiteModel.getActivitesUtilisateur(utilAlive);

        return list;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public List<Activite> getActivites() {
        return activiteModel.read();
    }

    public void resetForm() {
        this.activite = new Activite();
    }

    public ActiviteModel getActiviteModel() {
        return activiteModel;
    }

    public void setActiviteModel(ActiviteModel activiteModel) {
        this.activiteModel = activiteModel;
    }

    public DonneeController getDonneeController() {
        return donneeController;
    }

    public void setDonneeController(DonneeController donneeController) {
        this.donneeController = donneeController;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }

    public UtilisateurController getUtilisateurController() {
        return utilisateurController;
    }

    public void setUtilisateurController(UtilisateurController utilisateurController) {
        this.utilisateurController = utilisateurController;
    }
}
