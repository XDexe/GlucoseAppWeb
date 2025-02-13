package fr.lamsoent.glucoseapplication.controller;
import fr.lamsoent.glucoseapplication.model.ActiviteModel;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@RequestScoped
public class ActiviteController implements Serializable {

    @EJB
    private ActiviteModel activiteModel;

    private Activite activite = new Activite();

    public void editActivite() {
        activiteModel.update(this.activite);
        activite = new Activite();
    }

    public void deleteActivite(Activite activite) {
        activiteModel.delete(activite);
    }

    public void loadActivite(Activite activite) {
        this.activite=activite;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

}
