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
public class PersonneController implements Serializable {
    private List<Personne> listPersonne = new ArrayList<Personne>();

    public PersonneController() {
    }

    public List<Personne> getListPersonne() {
        return listPersonne;
    }

    public void setListPersonne(List<Personne> listPersonne) {
        this.listPersonne = listPersonne;
    }

}
