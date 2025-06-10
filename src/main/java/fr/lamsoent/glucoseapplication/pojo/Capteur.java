package fr.lamsoent.glucoseapplication.pojo;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Capteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String numeroSerie;
    private String mac;
    private String longitude;
    private String latitude;

    @JsonbTransient
    @OneToMany(mappedBy = "capteur", cascade = CascadeType.ALL)
    @CascadeOnDelete
    private List<Activite> listActivites = new ArrayList<Activite>();


    public Capteur() {
    System.out.println("création de capteur");
    }
    public String getNumeroSerie(){
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie){
        this.numeroSerie = numeroSerie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonbTransient
    public List<Activite> getListActivites() {
        return listActivites;
    }

    public void setListActivites(List<Activite> listActivites) {
        this.listActivites = listActivites;
    }
}
