package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.DieteticienModel;
import fr.lamsoent.glucoseapplication.model.RoleModel;
import fr.lamsoent.glucoseapplication.pojo.Dieteticien;
import fr.lamsoent.glucoseapplication.pojo.Medecin;
import fr.lamsoent.glucoseapplication.pojo.Role;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class DieteticienController implements Serializable {

    private Dieteticien dieteticien = new Dieteticien();
    private Utilisateur utilisateurSelectionne = new Utilisateur();
    private List<Integer> utilisteursSelectionnesIds;

    @EJB
    private DieteticienModel dieteticienModel;

    @EJB
    private RoleModel roleModel;

    @Inject
    private PersonneController personneController;

    @Inject
    private UtilisateurController utilisateurController;

    @Named
    @Inject
    private ImageController imageController;

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurController.getUtilisateurs();
    }

    public void editDieteticien() {
        Role roleDieteticien = roleModel.getOrCreateRoleByName("DEFAUT");
        dieteticien.setRole(roleDieteticien);

        dieteticien = dieteticienModel.update(dieteticien);
        imageController.saveImage(dieteticien);

        if(!utilisteursSelectionnesIds.isEmpty()) {
            utilisteursSelectionnesIds.forEach(id -> {
                Utilisateur utilisateur;
                utilisateur = utilisateurController.read(id);

                if(utilisateur != null) {
                    utilisateur.setDieteticien(dieteticien);
                    utilisateurController.update(utilisateur);
                }
            });
        }

        dieteticien = new Dieteticien();
    }

    public void deleteDieteticien(Dieteticien dieteticien) {
        for (Utilisateur utilisateur : utilisateurController.getUtilisateurs()) {
            if (utilisateur.getDieteticien() != null &&
                    utilisateur.getDieteticien().getIdPersonne() == dieteticien.getIdPersonne()) {
                utilisateur.setDieteticien(null);
                utilisateurController.update(utilisateur);
            }
        }
        imageController.deleteImage(dieteticien);
        dieteticienModel.delete(dieteticien);
    }

    public void loadDieteticien(Dieteticien dieteticien) {
        this.dieteticien=dieteticien;
    }

    public Dieteticien getDieteticien() {
        return dieteticien;
    }

    public void setDieteticien(Dieteticien dieteticien) {
        this.dieteticien = dieteticien;
    }

    public List<Dieteticien> getDieteticiens() {
        return dieteticienModel.read();
    }

    public void resetForm() {
        this.dieteticien = new Dieteticien();
    }

    public Utilisateur getUtilisateurSelectionne() {
        return utilisateurSelectionne;
    }

    public void setUtilisateurSelectionne(Utilisateur utilisateurSelectionne) {
        this.utilisateurSelectionne = utilisateurSelectionne;
    }

    public List<Integer> getUtilisteursSelectionnesIds() {
        return utilisteursSelectionnesIds;
    }

    public void setUtilisteursSelectionnesIds(List<Integer> utilisteursSelectionnesIds) {
        this.utilisteursSelectionnesIds = utilisteursSelectionnesIds;
    }

    public DieteticienModel getDieteticienModel() {
        return dieteticienModel;
    }

    public void setDieteticienModel(DieteticienModel dieteticienModel) {
        this.dieteticienModel = dieteticienModel;
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
}
