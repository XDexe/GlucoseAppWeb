package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.EntraineurModel;
import fr.lamsoent.glucoseapplication.model.RoleModel;
import fr.lamsoent.glucoseapplication.pojo.Entraineur;
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
public class EntraineurController implements Serializable {

    private Entraineur entraineur = new Entraineur();
    private Utilisateur utilisateurSelectionne = new Utilisateur();
    private List<Integer> utilisteursSelectionnesIds;

    @EJB
    private EntraineurModel entraineurModel;

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

    public void editEntraineur() {

        Role roleDefaut = roleModel.getRoleByName("DEFAUT");
        entraineur.setRole(roleDefaut);

        entraineur = entraineurModel.update(entraineur);
        imageController.saveImage(entraineur);

        if(!utilisteursSelectionnesIds.isEmpty()) {
            utilisteursSelectionnesIds.forEach(id -> {
                Utilisateur utilisateur;
                utilisateur = utilisateurController.read(id);

                if(utilisateur != null) {
                    utilisateur.setEntraineur(entraineur);
                    utilisateurController.update(utilisateur);
                }
            });
        }
        entraineur = new Entraineur();
    }

    public void deleteEntraineur(Entraineur entraineur) {
        for (Utilisateur utilisateur : utilisateurController.getUtilisateurs()) {
            if (utilisateur.getEntraineur() != null &&
                    utilisateur.getEntraineur().getIdPersonne() == entraineur.getIdPersonne()) {
                utilisateur.setEntraineur(null);
                utilisateurController.update(utilisateur);
            }
        }
        imageController.deleteImage(entraineur);
        entraineurModel.delete(entraineur);
    }

    public void loadEntraineur(Entraineur entraineur) {
        this.entraineur=entraineur;
    }

    public Entraineur getEntraineur() {
        return entraineur;
    }

    public void setEntraineur(Entraineur entraineur) {
        this.entraineur = entraineur;
    }

    public List<Entraineur> getEntraineurs() {
        return entraineurModel.read();
    }

    public void resetForm() {
        this.entraineur = new Entraineur();
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

    public EntraineurModel getEntraineurModel() {
        return entraineurModel;
    }

    public void setEntraineurModel(EntraineurModel entraineurModel) {
        this.entraineurModel = entraineurModel;
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
