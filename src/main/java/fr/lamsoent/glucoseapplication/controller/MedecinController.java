package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.MedecinModel;
import fr.lamsoent.glucoseapplication.pojo.Medecin;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MedecinController implements Serializable {

    private Medecin medecin = new Medecin();
    private Utilisateur utilisateurSelectionne = new Utilisateur();
    private List<Integer> utilisteursSelectionnesIds;

    @EJB
    private MedecinModel medecinModel;

    @Inject
    private PersonneController personneController;

    @Inject
    private UtilisateurController utilisateurController;

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurController.getUtilisateurs();
    }

    public void editMedecin() {
            medecin = medecinModel.update(medecin);
            personneController.saveImage(medecin);

            if(!utilisteursSelectionnesIds.isEmpty()) {
                utilisteursSelectionnesIds.forEach(id -> {
                    Utilisateur utilisateur;
                    utilisateur = utilisateurController.read(id);

                    if(utilisateur != null) {
                        utilisateur.setMedecin(medecin);
                        utilisateurController.update(utilisateur);
                    }
                });
            }
            medecin = new Medecin();
    }

    public void deleteMedecin(Medecin medecin) {
        for (Utilisateur utilisateur : utilisateurController.getUtilisateurs()) {
            if (utilisateur.getMedecin() != null &&
                    utilisateur.getMedecin().getIdPersonne() == medecin.getIdPersonne()) {
                utilisateur.setMedecin(null);
                utilisateurController.update(utilisateur);
            }
        }
        personneController.deleteImage(medecin);
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

    public Utilisateur getUtilisateurSelectionne() {
        return utilisateurSelectionne;
    }

    public void setUtilisateurSelectionne(Utilisateur utilisateurSelectionne) {
        this.utilisateurSelectionne = utilisateurSelectionne;
    }

    public MedecinModel getMedecinModel() {
        return medecinModel;
    }

    public void setMedecinModel(MedecinModel medecinModel) {
        this.medecinModel = medecinModel;
    }

    public PersonneController getPersonneController() {
        return personneController;
    }

    public void setPersonneController(PersonneController personneController) {
        this.personneController = personneController;
    }

    public UtilisateurController getUtilisateurController() {
        return utilisateurController;
    }

    public void setUtilisateurController(UtilisateurController utilisateurController) {
        this.utilisateurController = utilisateurController;
    }

    public List<Integer> getUtilisteursSelectionnesIds() {
        return utilisteursSelectionnesIds;
    }

    public void setUtilisteursSelectionnesIds(List<Integer> utilisteursSelectionnesIds) {
        this.utilisteursSelectionnesIds = utilisteursSelectionnesIds;
    }


}
