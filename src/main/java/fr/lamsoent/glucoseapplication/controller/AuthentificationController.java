package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.PersonneModel;
import fr.lamsoent.glucoseapplication.pojo.*;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class AuthentificationController implements Serializable {

    private List<Personne> listPersonne = new ArrayList<Personne>();
    private Personne personneLogin = new Personne();


    @Named
    @Inject
    private MedecinController medecinController;

    @Named
    @Inject
    private DieteticienController dieteticienController;

    @Named
    @Inject
    private EntraineurController entraineurController;

    @Inject
    private UtilisateurController utilisateurController;

    @Inject
    private AdministrateurController administrateurController;

    @EJB
    private PersonneModel personneModel;

    public String AuthPersonne() {
        System.out.println("Authentification en cours");
        listPersonne = personneModel.read();

        boolean isLogged = false;
        for (Personne p : listPersonne) {
            if (p.getIdentifiant() == null || p.getIdentifiant().isEmpty() ||
                    p.getMotDePasse() == null || p.getMotDePasse().isEmpty()) {
                continue;
            }

            System.out.println(p.getIdentifiant() + " " + p.getMotDePasse());
            System.out.println(personneLogin.getIdentifiant() + " " + personneLogin.getMotDePasse());

            boolean resultpass = Outil.verifyPasswordBcrypt(personneLogin.getPlainTextPassword(), p.getMotDePasse());
            System.out.println(resultpass);

            if (p.getIdentifiant().equals(personneLogin.getIdentifiant()) && resultpass) {
                personneLogin = p;
                isLogged = true;
                break;
            }
        }

        if (isLogged) {
            System.out.println("Authentification Réussie");
            String role = personneLogin.getRole() != null ? personneLogin.getRole().getNomRole() : null;

            System.out.println(role);

            if (role != null && role.equals("ADMINISTRATEUR")) {
                Utilisateur admin = (Utilisateur) personneLogin;
                administrateurController.setAdministrateur(admin);
                return "admin/interfaceAdmin?faces-redirect=true";
            } else if (personneLogin instanceof Utilisateur) {
                Utilisateur utilisateur = (Utilisateur) personneLogin;
                utilisateurController.setUtilisateur(utilisateur);
                return "client/listeActivites?faces-redirect=true";
            } else if (personneLogin instanceof Medecin) {
                Medecin medecin = (Medecin) personneLogin;
                medecinController.setMedecin(medecin);
                return "utilisateur/dashboardMedecin?faces-redirect=true";
            } else if (personneLogin instanceof Dieteticien) {
                Dieteticien dieteticien = (Dieteticien) personneLogin;
                dieteticienController.setDieteticien(dieteticien);
                return "utilisateur/dashboardDieteticien?faces-redirect=true";
            } else if (personneLogin instanceof Entraineur) {
                Entraineur entraineur = (Entraineur) personneLogin;
                entraineurController.setEntraineur(entraineur);
                return "utilisateur/dashboardEntraineur?faces-redirect=true";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erreur", "Identifiant ou mot de passe incorrect"));
            System.out.println("Authentification échouée");
        }

        return "";
    }

    public String logout() {
        System.out.println("Déconnexion en cours");

        // Reset the personneLogin
        personneLogin = new Personne();

        // Get the external context to manipulate response
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jakarta.servlet.http.HttpServletResponse response =
                (jakarta.servlet.http.HttpServletResponse) facesContext.getExternalContext().getResponse();

        // Set cache control headers to prevent caching
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        // Invalidate the session
        facesContext.getExternalContext().invalidateSession();

        // Add a random parameter to prevent browser caching
        return "/authentification.html?faces-redirect=true&nocache=" + System.currentTimeMillis();
    }

    public boolean isUserAuthenticated() {
        //System.out.println(personneLogin != null && personneLogin.getIdentifiant() != null &&
          //      personneLogin.getIdentifiant() != null && !personneLogin.getIdentifiant().isEmpty() && personneLogin.getIdPersonne() !=0);
  
        return personneLogin != null && personneLogin.getIdentifiant() != null &&
                personneLogin.getIdentifiant() != null && !personneLogin.getIdentifiant().isEmpty() && personneLogin.getIdPersonne() !=0;
    }


    public List<Personne> getListPersonne() {
        return listPersonne;
    }

    public void setListPersonne(List<Personne> listPersonne) {
        this.listPersonne = listPersonne;
    }

    public Personne getPersonneLogin() {
        return personneLogin;
    }

    public void setPersonneLogin(Personne personneLogin) {
        this.personneLogin = personneLogin;
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

    public UtilisateurController getUtilisateurController() {
        return utilisateurController;
    }

    public void setUtilisateurController(UtilisateurController utilisateurController) {
        this.utilisateurController = utilisateurController;
    }

    public PersonneModel getPersonneModel() {
        return personneModel;
    }

    public void setPersonneModel(PersonneModel personneModel) {
        this.personneModel = personneModel;
    }


}
