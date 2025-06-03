package fr.lamsoent.glucoseapplication.pojo;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date dateDebut;
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date dateFin;
    private boolean isAlive;

    @OneToMany(mappedBy = "activite", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Donnee> listDonnees = new ArrayList<>();

    @ManyToOne
    private Utilisateur utilisateur ;

    @ManyToOne
    private Capteur capteur ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public List<Donnee> getListDonnees() {
        return listDonnees;
    }

    public void setListDonnees(List<Donnee> listDonnees) {
        this.listDonnees = listDonnees;
    }
}
