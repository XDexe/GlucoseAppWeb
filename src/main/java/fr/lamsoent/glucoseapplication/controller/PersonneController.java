package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.PersonneModel;
import fr.lamsoent.glucoseapplication.pojo.Entraineur;
import fr.lamsoent.glucoseapplication.pojo.Personne;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.eclipse.persistence.oxm.annotations.XmlNamedAttributeNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class PersonneController implements Serializable {
    private List<Personne> listPersonne = new ArrayList<Personne>();
    private Personne personneLogin=new Personne();
    @EJB
    private PersonneModel personneModel;

    public String AuthPersonne() {
        System.out.println("Authentification en cours");
        listPersonne=personneModel.read();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        boolean isLogged = false;

        for (Personne p : listPersonne) {
            if (p.getIdentifiant().equals(personneLogin.getIdentifiant()) && p.getMotDePasse().equals(personneLogin.getMotDePasse())) {
                personneLogin=p;
                isLogged = true;
                break;
            }


        if(isLogged){
            System.out.println("Authentification Réussi");
            try {
                if(personneLogin.getClass().equals(Utilisateur.class)){
                    return "listeActivites";
                }
                externalContext.redirect("Accueil.xhtml");
            } catch (Exception e) {
                e.getMessage();
            }
        }else {
            System.out.println("Authentification échoué");
        }

    }
        return "";
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

    public PersonneModel getPersonneModel() {
        return personneModel;
    }

    public void setPersonneModel(PersonneModel personneModel) {
        this.personneModel = personneModel;
    }
}
