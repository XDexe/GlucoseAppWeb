package fr.lamsoent.glucoseapplication.pojo;

import jakarta.persistence.*;
import sun.net.www.protocol.http.AuthenticationInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date dateDebut;
    private Date dateFin;

    @OneToMany(mappedBy = "activite")
    private List<Data> listDatas = new ArrayList<>();

    @ManyToOne
    private Utilisateur utilisateur= new Utilisateur();

    @ManyToOne
    private Capteur capteur = new Capteur();

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

    public List<Data> getListDatas() {
        return listDatas;
    }

    public void setListDatas(List<Data> listDatas) {
        this.listDatas = listDatas;
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
}
