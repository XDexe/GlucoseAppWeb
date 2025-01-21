package fr.lamsoent.glucoseapplication.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
@Entity
public class Utilisateur extends Personne {
    private String remarque;
    private String seuilMax;
    private String seuilMin;

    @ManyToOne
    private Medecin medecin = new Medecin();

    @ManyToOne
    private Entraineur entraineur = new Entraineur();


    @ManyToOne
    private Dieteticien dieteticien = new Dieteticien();

    public Utilisateur(){

    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public String getSeuilMax() {
        return seuilMax;
    }

    public void setSeuilMax(String seuilMax) {
        this.seuilMax = seuilMax;
    }

    public String getSeuilMin() {
        return seuilMin;
    }

    public void setSeuilMin(String seuilMin) {
        this.seuilMin = seuilMin;
    }
    public Dieteticien getDieteticien(){
        return dieteticien;
    }
    public void setDieteticien(Dieteticien dieteticien){
        this.dieteticien = dieteticien;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Entraineur getEntraineur() {
        return entraineur;
    }

    public void setEntraineur(Entraineur entraineur) {
        this.entraineur = entraineur;
    }
}
