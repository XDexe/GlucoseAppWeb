package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.EntraineurModel;
import fr.lamsoent.glucoseapplication.pojo.Entraineur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class EntraineurController implements Serializable {

    @EJB
    private EntraineurModel entraineurModel;

    private Entraineur entraineur = new Entraineur();

    public void editEntraineur() {
        entraineurModel.update(this.entraineur);
        entraineur = new Entraineur();
    }

    public void deleteEntraineur(Entraineur entraineur) {
        entraineurModel.delete(entraineur);
    }

    public void loadEntraineur(Entraineur entraineur) {
        this.entraineur=entraineur;
    }

    public Entraineur getEntraineur() {
        return entraineur;
    }

    public void setEntraineur(Entraineur entraineur) {
        this.entraineur = entraineur;
    }

    public List<Entraineur> getEntraineurs() {
        return entraineurModel.read();
    }

    public void resetForm() {
        this.entraineur = new Entraineur();
    }
}
