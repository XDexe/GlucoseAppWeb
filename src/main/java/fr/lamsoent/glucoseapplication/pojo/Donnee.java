package fr.lamsoent.glucoseapplication.pojo;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.util.Date;


@Entity
public class Donnee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String glucose;

    private Date dateData;

    @JsonbTransient
    @ManyToOne
    private Activite activite = new Activite();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGlucose() {
        return glucose;
    }

    public void setGlucose(String glucose) {
        this.glucose = glucose;
    }

    public Date getDateData() {
        return dateData;
    }

    public void setDateData(Date dateData) {
        this.dateData = dateData;
    }

    @JsonbTransient
    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }
}
