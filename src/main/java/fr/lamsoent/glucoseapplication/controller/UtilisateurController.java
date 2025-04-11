package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.RoleModel;
import fr.lamsoent.glucoseapplication.model.UtilisateurModel;
import fr.lamsoent.glucoseapplication.pojo.*;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UtilisateurController implements Serializable {

    private Utilisateur utilisateur = new Utilisateur();
    private Medecin medecinSelectionne = new Medecin();
    private Entraineur entraineurSelectionne = new Entraineur();
    private Dieteticien dieteticienSelectionne = new Dieteticien();
    private Categorie categorieSelectionnee = new Categorie();

    @EJB
    private UtilisateurModel utilisateurModel;

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

    public void editUtilisateur() {
        Role roleUtilisateur = roleModel.getRoleByName("DEFAUT");
        if (roleUtilisateur == null) {
            roleUtilisateur = new Role();
            roleUtilisateur.setNomRole("DEFAUT");
        }
        utilisateur.setRole(roleUtilisateur);
        utilisateur = utilisateurModel.update(utilisateur);
        imageController.saveImage(utilisateur);

        if(medecinSelectionne.getIdPersonne() == 0){
            System.out.println(medecinSelectionne.getIdPersonne());
            utilisateur.setMedecin(null);
        }
        else {
            System.out.println(medecinSelectionne.getIdPersonne());
            utilisateur.setMedecin(medecinSelectionne);
        }

        if(entraineurSelectionne.getIdPersonne() == 0){
            System.out.println(entraineurSelectionne.getIdPersonne());
            utilisateur.setEntraineur(null);
        }
        else{
            System.out.println(entraineurSelectionne.getIdPersonne());
            utilisateur.setEntraineur(entraineurSelectionne);
        }

        if(dieteticienSelectionne.getIdPersonne() == 0){
            System.out.println(dieteticienSelectionne.getIdPersonne());
            utilisateur.setDieteticien(null);
        }
        else{
            System.out.println(dieteticienSelectionne.getIdPersonne());
            utilisateur.setDieteticien(dieteticienSelectionne);
        }

        if(categorieSelectionnee.getId() == 0){
            utilisateur.setCategorie(null);
        }
        else{
            utilisateur.setCategorie(categorieSelectionnee);
        }

        utilisateurModel.update(utilisateur);

        resetForm();
    }

    public void deleteUtilisateur(Utilisateur utilisateur) {
        imageController.deleteImage(utilisateur);
        utilisateurModel.delete(utilisateur);
    }

    public void loadUtilisateur(Utilisateur utilisateur) {
        this.utilisateur=utilisateur;
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
        this.entraineurSelectionne = new Entraineur();
        this.dieteticienSelectionne = new Dieteticien();
        this.categorieSelectionnee = new Categorie();
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


}

