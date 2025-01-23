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


}
