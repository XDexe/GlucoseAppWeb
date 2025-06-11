package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.RoleModel;
import fr.lamsoent.glucoseapplication.model.UtilisateurModel;
import fr.lamsoent.glucoseapplication.pojo.*;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class UtilisateurController implements Serializable {

    private Utilisateur utilisateur = new Utilisateur();
    private Medecin medecinSelectionne = new Medecin();
    private Entraineur entraineurSelectionne = new Entraineur();
    private Dieteticien dieteticienSelectionne = new Dieteticien();
    private Categorie categorieSelectionnee = new Categorie();
    private Capteur capteurSelectionne = new Capteur();

    @EJB
    private UtilisateurModel utilisateurModel;

    @Inject
    EmailController emailController;

    @Inject
    private PersonneController personneController;

    @Inject
    private MedecinController medecinController;

    @Inject
    private DieteticienController dieteticienController;

    @Inject
    private EntraineurController entraineurController;
    @Named
    @Inject
    private ImageController imageController;

    @EJB
    private RoleModel roleModel;

    @Inject
    private CategorieController categorieController;

    @Inject
    private CapteurController capteurController;

    public void sendMail(String to, String subject, String body) {;
        if (to == null || to.isEmpty()) {
            System.out.println("L'adresse e-mail est vide.");
            return;
        }
        emailController.sendEmail(to, subject, body);
    }

    public void editUtilisateur() {
        Role roleUtilisateur = roleModel.getOrCreateRoleByName("DEFAUT");
        utilisateur.setRole(roleUtilisateur);

        if (utilisateur.getPlainTextPassword() != null && !utilisateur.getPlainTextPassword().isEmpty()) {
            utilisateur.setMotDePasse(utilisateur.getPlainTextPassword());
        }

        utilisateur.setMedecin(medecinSelectionne != null && medecinSelectionne.getIdPersonne() != 0 ? medecinSelectionne : null);
        utilisateur.setEntraineur(entraineurSelectionne != null && entraineurSelectionne.getIdPersonne() != 0 ? entraineurSelectionne : null);
        utilisateur.setDieteticien(dieteticienSelectionne != null && dieteticienSelectionne.getIdPersonne() != 0 ? dieteticienSelectionne : null);
        utilisateur.setCategorie(categorieSelectionnee != null && categorieSelectionnee.getId() != 0 ? categorieSelectionnee : null);
        utilisateur.setCapteur(capteurSelectionne != null && capteurSelectionne.getId() != 0 ? capteurSelectionne : null);

        utilisateur = utilisateurModel.update(utilisateur);
        imageController.saveImage(utilisateur);

        resetForm();
    }

    public void deleteUtilisateur(Utilisateur utilisateur) {
        if (utilisateur != null) {
            imageController.deleteImage(utilisateur);
        }
        utilisateurModel.delete(utilisateur);
    }

    public void loadUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            resetForm();
            return;
        }

        if (utilisateur.getIdPersonne() > 0) {
            this.utilisateur = utilisateurModel.read(utilisateur.getIdPersonne());
        } else {
            this.utilisateur = utilisateur;
        }

        this.medecinSelectionne = this.utilisateur.getMedecin() != null ? this.utilisateur.getMedecin() : new Medecin();
        this.dieteticienSelectionne = this.utilisateur.getDieteticien() != null ? this.utilisateur.getDieteticien() : new Dieteticien();
        this.entraineurSelectionne = this.utilisateur.getEntraineur() != null ? this.utilisateur.getEntraineur() : new Entraineur();
        this.categorieSelectionnee = this.utilisateur.getCategorie() != null ? this.utilisateur.getCategorie() : new Categorie();
        this.capteurSelectionne = this.utilisateur.getCapteur() != null ? this.utilisateur.getCapteur() : new Capteur();
    }

    public List<Utilisateur> readByMedecin(int medecinId) {
        return utilisateurModel.readByMedecin(medecinId);
    }

    public List<Utilisateur> readByDieteticien(int dieteticienId) {
        return utilisateurModel.readByDieteticien(dieteticienId);
    }

    public List<Utilisateur> readByEntraineur(int entraineurId) {
        return utilisateurModel.readByEntraineur(entraineurId);
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurModel.readByRole("DEFAUT");
    }
    public List<Utilisateur> read() {
        return utilisateurModel.read();
    }

    public void resetForm() {
        this.utilisateur = new Utilisateur();
        this.medecinSelectionne = new Medecin();
        this.dieteticienSelectionne = new Dieteticien();
        this.entraineurSelectionne = new Entraineur();
        this.categorieSelectionnee = new Categorie();
        this.capteurSelectionne = new Capteur();

        if (imageController != null) {
            imageController.resetUploadedFile();
        }
    }

    public Utilisateur update(Utilisateur utilisateur) {
        return utilisateurModel.update(utilisateur);
    }
    public Utilisateur read(int id){
        return utilisateurModel.read(id);
    }

    public List<Medecin> getMedecins() {
        return medecinController.getMedecins();
    }

    public List<Dieteticien> getDieteticiens() {
        return dieteticienController.getDieteticiens();
    }

    public Categorie getCategorieSelectionnee() {
        return categorieSelectionnee;
    }

    public void setCategorieSelectionnee(Categorie categorieSelectionnee) {
        this.categorieSelectionnee = categorieSelectionnee;
    }

    public List<Categorie> getCategories() {
        return categorieController.getCategories();
    }

    public CategorieController getCategorieController() {
        return categorieController;
    }

    public void setCategorieController(CategorieController categorieController) {
        this.categorieController = categorieController;
    }

    public List<Utilisateur> getUtilisateursByRole() {
        return utilisateurModel.readByRole("DEFAUT");
    }

    public List<Entraineur> getEntraineurs() {
        return entraineurController.getEntraineurs();
    }

    public Medecin getMedecinSelectionne() {
        return medecinSelectionne;
    }

    public void setMedecinSelectionne(Medecin medecinSelectionne) {
        this.medecinSelectionne = medecinSelectionne;
    }

    public Entraineur getEntraineurSelectionne() {
        return entraineurSelectionne;
    }

    public void setEntraineurSelectionne(Entraineur entraineurSelectionne) {
        this.entraineurSelectionne = entraineurSelectionne;
    }

    public Dieteticien getDieteticienSelectionne() {
        return dieteticienSelectionne;
    }

    public void setDieteticienSelectionne(Dieteticien dieteticienSelectionne) {
        this.dieteticienSelectionne = dieteticienSelectionne;
    }

    public void accesActivitesPatient(Utilisateur patient) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("patientSelectionne", patient);
        setUtilisateur(patient);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./../client/listeActivites.html?faces-redirect=true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ImageController getImageController() {
        return imageController;
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
    }

    public UtilisateurModel getUtilisateurModel() {
        return utilisateurModel;
    }

    public void setUtilisateurModel(UtilisateurModel utilisateurModel) {
        this.utilisateurModel = utilisateurModel;
    }

    public PersonneController getPersonneController() {
        return personneController;
    }

    public void setPersonneController(PersonneController personneController) {
        this.personneController = personneController;
    }

    public MedecinController getMedecinController() {
        return medecinController;
    }

    public void setMedecinController(MedecinController medecinController) {
        this.medecinController = medecinController;
    }

    public DieteticienController getDieteticienController() {
        return dieteticienController;
    }

    public void setDieteticienController(DieteticienController dieteticienController) {
        this.dieteticienController = dieteticienController;
    }

    public EntraineurController getEntraineurController() {
        return entraineurController;
    }

    public void setEntraineurController(EntraineurController entraineurController) {
        this.entraineurController = entraineurController;
    }

    public EmailController getEmailController() {
        return emailController;
    }

    public void setEmailController(EmailController emailController) {
        this.emailController = emailController;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }

    public Capteur getCapteurSelectionne() {
        return capteurSelectionne;
    }

    public void setCapteurSelectionne(Capteur capteurSelectionne) {
        this.capteurSelectionne = capteurSelectionne;
    }

    public CapteurController getCapteurController() {
        return capteurController;
    }

    public void setCapteurController(CapteurController capteurController) {
        this.capteurController = capteurController;
    }
}

