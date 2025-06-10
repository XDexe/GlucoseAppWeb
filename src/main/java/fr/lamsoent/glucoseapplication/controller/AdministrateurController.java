package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.RoleModel;
import fr.lamsoent.glucoseapplication.model.UtilisateurModel;
import fr.lamsoent.glucoseapplication.pojo.Role;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AdministrateurController implements Serializable {

    private Utilisateur administrateur = new Utilisateur();

    @EJB
    private UtilisateurModel utilisateurModel;

    @EJB
    private RoleModel roleModel;

    @Inject
    private PersonneController personneController;
    @Named
    @Inject
    private ImageController imageController;


    public void editAdministrateur() {
        Role roleAdmin = roleModel.getOrCreateRoleByName("ADMINISTRATEUR");
        administrateur.setRole(roleAdmin);

        if (administrateur.getPlainTextPassword() != null && !administrateur.getPlainTextPassword().isEmpty()) {
            administrateur.setMotDePasse(administrateur.getPlainTextPassword());
        }

        administrateur = utilisateurModel.update(administrateur);
        imageController.saveImage(administrateur);

        resetForm();
    }

    public void deleteAdministrateur(Utilisateur administrateur) {
        imageController.deleteImage(administrateur);
        utilisateurModel.delete(administrateur);
    }

    public void loadAdministrateur(Utilisateur administrateur) {
        this.administrateur = administrateur;
    }

    public List<Utilisateur> getAdministrateurs() {
        return utilisateurModel.readByRole("ADMINISTRATEUR");
    }

    public void resetForm() {
        this.administrateur = new Utilisateur();
        imageController.resetUploadedFile();
    }

    public Utilisateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Utilisateur administrateur) {
        this.administrateur = administrateur;
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

    public PersonneController getPersonneController() {
        return personneController;
    }

    public void setPersonneController(PersonneController personneController) {
        this.personneController = personneController;
    }
}
