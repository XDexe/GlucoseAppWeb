package fr.lamsoent.glucoseapplication.pojo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String dateDebut;
    private String dateFin;

    @OneToMany(mappedBy = "activite")
    private List<Data> listDatas = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public List<Data> getListDatas() {
        return listDatas;
    }

    public void setListDatas(List<Data> listDatas) {
        this.listDatas = listDatas;
    }
}
