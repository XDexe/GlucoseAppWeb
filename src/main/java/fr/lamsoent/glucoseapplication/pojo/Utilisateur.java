package fr.lamsoent.glucoseapplication.pojo;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Utilisateur extends Personne {
    private String remarque;
    private String seuilMax;
    private String seuilMin;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    @JsonbTransient
    private List<Alerte> listAlertes = new ArrayList<>();

    @ManyToOne
    private Categorie categorie;

    @ManyToOne
    private Medecin medecin;

    @ManyToOne
    private Entraineur entraineur;

    @ManyToOne
    private Capteur capteur;

    @ManyToOne
    private Dieteticien dieteticien;

    @OneToMany(mappedBy = "utilisateur")
    @JsonbTransient
    private List<Activite> activite = new ArrayList<Activite>();

    public Utilisateur(){
    }

    @JsonbTransient
    public List<Alerte> getListAlertes(){
        return listAlertes;
    }

    public void setListAlertes(List<Alerte> listAlertes){
        this.listAlertes =listAlertes;
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

    public Capteur getCapteur() {
        return capteur;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }

    @JsonbTransient
    public List<Activite> getActivite() {
        return activite;
    }

    public void setActivite(List<Activite> activite) {
        this.activite = activite;
    }

    public  void setCategorie(Categorie categorie){
        this.categorie = categorie;
    }

    public Categorie getCategorie(){
        return categorie;
    }


}
