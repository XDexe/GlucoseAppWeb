package fr.lamsoent.glucoseapplication.controller;
import fr.lamsoent.glucoseapplication.model.ActiviteModel;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

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

    public String accesActivite(Activite activite) {
        this.activite = activite;
        return "/graphiqueClient.xhtml";
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public List<Activite> getActivites() {
        return activiteModel.read();
    }

    public void resetForm() {
        this.activite = new Activite();
    }

    public ActiviteModel getActiviteModel() {
        return activiteModel;
    }

    public void setActiviteModel(ActiviteModel activiteModel) {
        this.activiteModel = activiteModel;
    }

}
