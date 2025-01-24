package fr.lamsoent.glucoseapplication.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Dieteticien extends Personne{

    @OneToMany(mappedBy = "dieteticien")
    private List<Utilisateur> listUtilisateurs = new ArrayList<>();

    public Dieteticien(){
    }

    public List<Utilisateur> getListUtilisateurs() {
        return listUtilisateurs;
    }

    public void setListUtilisateurs(List<Utilisateur> listUtilisateurs) {
        this.listUtilisateurs = listUtilisateurs;
    }

}
