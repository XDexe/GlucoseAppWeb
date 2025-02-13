package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.UtilisateurModel;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class UtilisateurController implements Serializable {

    @EJB
    private UtilisateurModel utilisateurModel;

    private Utilisateur utilisateur = new Utilisateur();

    public void editUtilisateur() {
        utilisateurModel.update(this.utilisateur);
        utilisateur = new Utilisateur();
    }

    public void deleteUtilisateur(Utilisateur utilisateur) {
        utilisateurModel.delete(utilisateur);
    }

    public void loadUtilisateur(Utilisateur utilisateur) {
        this.utilisateur=utilisateur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

}
