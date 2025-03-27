package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.UtilisateurModel;
import fr.lamsoent.glucoseapplication.pojo.Medecin;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

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

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurModel.read();
    }
    public List<Utilisateur> read() {
        return utilisateurModel.read();
    }

    public void resetForm() {
        this.utilisateur = new Utilisateur();
    }

    public Utilisateur update(Utilisateur utilisateur) {
        return utilisateurModel.update(utilisateur);
    }
    public Utilisateur read(int id){
        return utilisateurModel.read(id);
    }

}
