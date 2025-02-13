package fr.lamsoent.glucoseapplication.controller;
import fr.lamsoent.glucoseapplication.model.CapteurModel;
import fr.lamsoent.glucoseapplication.pojo.Capteur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;

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

}
