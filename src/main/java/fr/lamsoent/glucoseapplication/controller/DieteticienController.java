package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.DieteticienModel;
import fr.lamsoent.glucoseapplication.pojo.Dieteticien;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class DieteticienController implements Serializable {

    @EJB
    private DieteticienModel dieteticienModel;

    private Dieteticien dieteticien = new Dieteticien();

    public void editDieteticien() {
        dieteticienModel.update(this.dieteticien);
        dieteticien = new Dieteticien();
    }

    public void deleteDieteticien(Dieteticien dieteticien) {
        dieteticienModel.delete(dieteticien);
    }

    public void loadDieteticien(Dieteticien dieteticien) {
        this.dieteticien=dieteticien;
    }

    public Dieteticien getDieteticien() {
        return dieteticien;
    }

    public void setDieteticien(Dieteticien dieteticien) {
        this.dieteticien = dieteticien;
    }

}
