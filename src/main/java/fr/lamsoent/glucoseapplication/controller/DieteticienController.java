package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.DieteticienModel;
import fr.lamsoent.glucoseapplication.model.RoleModel;
import fr.lamsoent.glucoseapplication.model.UtilisateurModel;
import fr.lamsoent.glucoseapplication.pojo.*;
import fr.lamsoent.glucoseapplication.pojo.Dieteticien;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class DieteticienController implements Serializable {

    private Dieteticien dieteticien = new Dieteticien();
    private Utilisateur utilisateurSelectionne = new Utilisateur();
    private List<Integer> utilisteursSelectionnesIds = new ArrayList<>();

    @EJB
    private DieteticienModel dieteticienModel;

    @EJB
    private RoleModel roleModel;

    @EJB
    private UtilisateurModel utilisateurModel;

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

        if (dieteticien.getPlainTextPassword() != null && !dieteticien.getPlainTextPassword().isEmpty()) {
            dieteticien.setMotDePasse(dieteticien.getPlainTextPassword());
        }

        dieteticien = dieteticienModel.update(dieteticien);
        imageController.saveImage(dieteticien);

        Set<Integer> currentPatientIds = utilisateurModel.readByDieteticien(dieteticien.getIdPersonne())
                .stream()
                .map(Utilisateur::getIdPersonne)
                .collect(Collectors.toSet());

        Set<Integer> newPatientIds = utilisteursSelectionnesIds.stream().collect(Collectors.toSet());

        currentPatientIds.stream()
                .filter(id -> !newPatientIds.contains(id))
                .map(id -> utilisateurController.read(id))
                .filter(u -> u != null)
                .forEach(utilisateur -> {
                    utilisateur.setDieteticien(null);
                    utilisateurController.update(utilisateur);
                });

        newPatientIds.stream()
                .filter(id -> !currentPatientIds.contains(id))
                .map(id -> utilisateurController.read(id))
                .filter(u -> u != null)
                .forEach(utilisateur -> {
                    utilisateur.setDieteticien(dieteticien);
                    utilisateurController.update(utilisateur);
                });

        resetForm();
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
        this.utilisteursSelectionnesIds = new ArrayList<>(); // Correction : Réinitialisation de la liste
        if (dieteticien == null) {
            System.out.println("Dieteticien n'existe pas");
            this.dieteticien = new Dieteticien();
            return;
        }

        int dieteticienId = dieteticien.getIdPersonne();
        System.out.println("Loading dieteticien with ID: " + dieteticienId);

        // If we have a valid ID, load it fresh from the database
        if (dieteticienId > 0) {
            this.dieteticien = dieteticienModel.read(dieteticienId);
            if (this.dieteticien == null) {
                System.out.println("Médecin not found in database. Creating new instance.");
                this.dieteticien = new Dieteticien();
                return;
            }

            for (Utilisateur utilisateur:utilisateurModel.readByDieteticien(dieteticienId)){
                System.out.println("Utilisateur ID: " + utilisateur.getIdPersonne() + " is associated with Dieteticien ID: " + dieteticienId);
                utilisteursSelectionnesIds.add(utilisateur.getIdPersonne());
            }
        } else {
            this.dieteticien = dieteticien;
            System.out.println("Dieteticien creation mode - ID is 0");

        }
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
        this.utilisteursSelectionnesIds = new ArrayList<>();
        imageController.resetUploadedFile();
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

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }

    public UtilisateurModel getUtilisateurModel() {
        return utilisateurModel;
    }

    public void setUtilisateurModel(UtilisateurModel utilisateurModel) {
        this.utilisateurModel = utilisateurModel;
    }

    public ImageController getImageController() {
        return imageController;
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
    }
}
