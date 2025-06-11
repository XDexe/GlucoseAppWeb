package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.MedecinModel;
import fr.lamsoent.glucoseapplication.model.RoleModel;
import fr.lamsoent.glucoseapplication.model.UtilisateurModel;
import fr.lamsoent.glucoseapplication.pojo.Medecin;
import fr.lamsoent.glucoseapplication.pojo.Role;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class MedecinController implements Serializable {

    private Medecin medecin = new Medecin();
    private Utilisateur utilisateurSelectionne = new Utilisateur();
    private List<Integer> utilisateursSelectionnesIds = new ArrayList<>();

    @EJB
    private MedecinModel medecinModel;

    @EJB
    private UtilisateurModel utilisateurModel;

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
        Role roleMedecin = roleModel.getOrCreateRoleByName("DEFAUT");
        medecin.setRole(roleMedecin);

        if (medecin.getPlainTextPassword() != null && !medecin.getPlainTextPassword().isEmpty()) {
            medecin.setMotDePasse(medecin.getPlainTextPassword());
        }

        medecin = medecinModel.update(medecin);
        imageController.saveImage(medecin);

        Set<Integer> currentPatientIds = utilisateurModel.readByMedecin(medecin.getIdPersonne())
                .stream()
                .map(Utilisateur::getIdPersonne)
                .collect(Collectors.toSet());

        Set<Integer> newPatientIds = utilisateursSelectionnesIds.stream().collect(Collectors.toSet());

        currentPatientIds.stream()
                .filter(id -> !newPatientIds.contains(id))
                .map(id -> utilisateurController.read(id))
                .filter(u -> u != null)
                .forEach(utilisateur -> {
                    utilisateur.setMedecin(null);
                    utilisateurController.update(utilisateur);
                });

        newPatientIds.stream()
                .filter(id -> !currentPatientIds.contains(id))
                .map(id -> utilisateurController.read(id))
                .filter(u -> u != null)
                .forEach(utilisateur -> {
                    utilisateur.setMedecin(medecin);
                    utilisateurController.update(utilisateur);
                });

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

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Le médecin " + medecin.getNom() + " a été supprimé.");
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void loadMedecin(Medecin medecin) {
        this.utilisateursSelectionnesIds = new ArrayList<>(); // Correction : Réinitialisation de la liste
        if (medecin == null) {
            System.out.println("Medecin n'existe pas");
            this.medecin = new Medecin();
            return;
        }

        int medecinId = medecin.getIdPersonne();
        System.out.println("Loading medecin with ID: " + medecinId);

        // If we have a valid ID, load it fresh from the database
        if (medecinId > 0) {
            this.medecin = medecinModel.read(medecinId);
            if (this.medecin == null) {
                System.out.println("Médecin not found in database. Creating new instance.");
                this.medecin = new Medecin();
                return;
            }

            for (Utilisateur utilisateur:utilisateurModel.readByMedecin(medecinId)){
                System.out.println("Utilisateur ID: " + utilisateur.getIdPersonne() + " is associated with Medecin ID: " + medecinId);
                utilisateursSelectionnesIds.add(utilisateur.getIdPersonne());
            }
        } else {
            this.medecin = medecin;
            System.out.println("Medecin creation mode - ID is 0");

        }
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
        this.utilisateursSelectionnesIds = new ArrayList<>();
        imageController.resetUploadedFile();
    }

    public Utilisateur getUtilisateurSelectionne() {
        return utilisateurSelectionne;
    }

    public void setUtilisateurSelectionne(Utilisateur utilisateurSelectionne) {
        this.utilisateurSelectionne = utilisateurSelectionne;
    }

    public List<Integer> getUtilisateursSelectionnesIds() {
        return utilisateursSelectionnesIds;
    }

    public void setUtilisateursSelectionnesIds(List<Integer> utilisteursSelectionnesIds) {
        this.utilisateursSelectionnesIds = utilisteursSelectionnesIds;
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

    public UtilisateurModel getUtilisateurModel() {
        return utilisateurModel;
    }

    public void setUtilisateurModel(UtilisateurModel utilisateurModel) {
        this.utilisateurModel = utilisateurModel;
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
