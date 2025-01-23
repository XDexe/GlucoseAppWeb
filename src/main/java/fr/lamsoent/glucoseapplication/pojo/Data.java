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
    private Activite activite;


}
