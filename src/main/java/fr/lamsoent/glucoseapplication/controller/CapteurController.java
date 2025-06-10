package fr.lamsoent.glucoseapplication.controller;
import fr.lamsoent.glucoseapplication.model.CapteurModel;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Capteur;
import fr.lamsoent.glucoseapplication.pojo.Medecin;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class CapteurController implements Serializable {

    @EJB
    private CapteurModel capteurModel;


    private Capteur capteur = new Capteur();

    public void editCapteur() {
        capteurModel.update(this.capteur);
        capteur = new Capteur();
    }

    public void deleteCapteur(Capteur capteur) {
        capteurModel.delete(capteur);
    }

    public void loadCapteur(Capteur capteur) {
        this.capteur=capteur;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }
    public String localiser(Activite activite){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("capteur", activite.getCapteur());
        return"localisation.html?faces-redirect=true";
    }

    public void loadLocaliser(){
        capteur = (Capteur) FacesContext.getCurrentInstance().getExternalContext().getFlash().getOrDefault("capteur", new Capteur());

        if (capteur.getId() == 0) {
            capteur =null;
            System.out.println("Erreur activité et 0");
        }
    }

    public CapteurModel getCapteurModel() {
        return capteurModel;
    }

    public void resetForm() {
        this.capteur = new Capteur();
    }

    public void afficherCapteurs() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./../client/localisation.html?faces-redirect=true");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setCapteurModel(CapteurModel capteurModel) {
        this.capteurModel = capteurModel;
    }

    public List<Capteur> getCapteurs() {
        return capteurModel.read();
    }
}


