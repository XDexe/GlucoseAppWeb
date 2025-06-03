package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.MedecinModel;
import fr.lamsoent.glucoseapplication.model.RoleModel;
import fr.lamsoent.glucoseapplication.pojo.Medecin;
import fr.lamsoent.glucoseapplication.pojo.Personne;
import fr.lamsoent.glucoseapplication.pojo.Role;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MedecinController implements Serializable {

    private Medecin medecin = new Medecin();
    private Utilisateur utilisateurSelectionne = new Utilisateur();
    private List<Integer> utilisteursSelectionnesIds;
    private boolean addMode = false;

    @EJB
    private MedecinModel medecinModel;

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

    public void editMedecin() {
        Role roleMDefaut = roleModel.getOrCreateRoleByName("DEFAUT");
        medecin.setRole(roleMDefaut);

        if (medecin.getIdPersonne() != 0 &&
                (medecin.getPlainTextPassword() == null || medecin.getPlainTextPassword().isEmpty())) {
            Medecin existingMedecin = medecinModel.read(medecin.getIdPersonne());
            if (existingMedecin != null) {
                medecin.setMotDePasse(existingMedecin.getMotDePasse());
            }
        }

        if (imageController.hasUploadedFile()) {
            imageController.saveImage(medecin);
        }

        medecin = medecinModel.update(medecin);

        if (utilisteursSelectionnesIds != null && !utilisteursSelectionnesIds.isEmpty()) {
            utilisteursSelectionnesIds.forEach(id -> {
                Utilisateur utilisateur = utilisateurController.read(id);
                if (utilisateur != null) {
                    utilisateur.setMedecin(medecin);
                    utilisateurController.update(utilisateur);
                }
            });
        }

        resetForm();
    }

    public void deleteMedecin(Medecin medecin) {
        for (Utilisateur utilisateur : utilisateurController.getUtilisateurs()) {
            if (utilisateur.getMedecin() != null &&
                    utilisateur.getMedecin().getIdPersonne() == medecin.getIdPersonne()) {
                utilisateur.setMedecin(null);
                utilisateurController.update(utilisateur);
            }
        }
        imageController.deleteImage(medecin);
        medecinModel.delete(medecin);
    }

    public void loadMedecin(Medecin medecin) {
        this.medecin=medecin;
        this.addMode = false;
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
        this.addMode = true;

        imageController.resetUploadedFile();
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

    public boolean isAddMode() {
        return addMode;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }

    public ImageController getImageController() {
        return imageController;
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
    }
}
