package fr.lamsoent.glucoseapplication.pojo;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categorie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String nom;


    @OneToMany(mappedBy = "categorie")
    @JsonbTransient
    private List<Utilisateur> listUtilisateurs = new ArrayList<Utilisateur>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonbTransient
    public List<Utilisateur> getListUtilisateurs() {
        return listUtilisateurs;
    }

    public void setListUtilisateurs(List<Utilisateur> listUtilisateurs) {
        this.listUtilisateurs = listUtilisateurs;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
