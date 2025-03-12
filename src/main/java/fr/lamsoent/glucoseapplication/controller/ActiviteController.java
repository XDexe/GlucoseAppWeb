package fr.lamsoent.glucoseapplication.controller;
import fr.lamsoent.glucoseapplication.model.ActiviteModel;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

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

    private Activite activite ;
    @Named
    @Inject
    private UtilisateurController utilisateurController;

    public void loadActivite(){
        activite = (Activite) FacesContext.getCurrentInstance().getExternalContext().getFlash().getOrDefault("activiter", new Activite());

        if (activite.getId() == 0) {
            activite =null;
            System.out.println("Erreur activiter et 0");
        }
    }

    public void editActivite() {
        activiteModel.update(this.activite);
        activite = new Activite();
    }

    public void deleteActivite(Activite activite) {
        activiteModel.delete(activite);
    }

    public String accesActivite(Activite activite) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("activiter", activite);

        return "graphiqueClient.html?faces-redirect=true";
    }

    public List<Activite> getActiviteUtilisateur(){
        Utilisateur utilAlive=utilisateurController.getUtilisateur();

        if (utilAlive.getIdPersonne()==0){
            return new ArrayList<>();
        }
        return activiteModel.getActivitesUtilisateur(utilAlive);
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


}
