package fr.lamsoent.glucoseapplication.pojo;

import jakarta.persistence.*;

@Entity
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String glucose;

    private String dateData;

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

    public String getDateData() {
        return dateData;
    }

    public void setDateData(String dateData) {
        this.dateData = dateData;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }
}
