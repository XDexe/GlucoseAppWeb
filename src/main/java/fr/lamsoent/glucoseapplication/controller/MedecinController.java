package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.MedecinModel;
import fr.lamsoent.glucoseapplication.pojo.Medecin;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MedecinController implements Serializable {

    @EJB
    private MedecinModel medecinModel;

    private Medecin medecin = new Medecin();

    @Inject
    private PersonneController personneController;

    public void editMedecin() {
        personneController.saveImage(medecin);
        medecinModel.update(medecin);
        medecin = new Medecin();
    }

    public void deleteMedecin(Medecin medecin) {
        medecinModel.delete(medecin);
    }

    public void loadMedecin(Medecin medecin) {
        this.medecin=medecin;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public List<Medecin> getMedecins() {
        return medecinModel.read();
    }

    public void resetForm() {
        this.medecin = new Medecin();
    }
}
