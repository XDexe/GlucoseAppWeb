package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.MedecinModel;
import fr.lamsoent.glucoseapplication.pojo.Medecin;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class MedecinController implements Serializable {

    @EJB
    private MedecinModel activiteModel;

    private Medecin activite = new Medecin();

    public void editMedecin() {
        activiteModel.update(this.activite);
        activite = new Medecin();
    }

    public void deleteMedecin(Medecin activite) {
        activiteModel.delete(activite);
    }

    public void loadMedecin(Medecin activite) {
        this.activite=activite;
    }

    public Medecin getMedecin() {
        return activite;
    }

    public void setMedecin(Medecin activite) {
        this.activite = activite;
    }

}
