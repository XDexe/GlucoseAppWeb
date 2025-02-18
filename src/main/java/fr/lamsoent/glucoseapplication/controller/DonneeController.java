package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.DonneeModel;
import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Donnee;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@RequestScoped
public class DonneeController implements Serializable {

    @EJB
    private DonneeModel donneeModel;
    private Activite activite;
    private Donnee donnee = new Donnee();

    public void editData() {
        donneeModel.update(this.donnee);
        donnee = new Donnee();
    }

    public void deleteData(Donnee donnee) {
        donneeModel.delete(donnee);
    }

    public void loadData(Donnee donnee) {
        this.donnee = donnee;
    }

    public Donnee getData() {
        return donnee;
    }

    public void setData(Donnee donnee) {
        this.donnee = donnee;
    }

    public DonneeModel getDonneeModel() {
        return donneeModel;
    }

    public void setDonneeModel(DonneeModel donneeModel) {
        this.donneeModel = donneeModel;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Donnee getDonnee() {
        return donnee;
    }

    public void setDonnee(Donnee donnee) {
        this.donnee = donnee;
    }
}
