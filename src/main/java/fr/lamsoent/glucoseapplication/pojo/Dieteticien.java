package fr.lamsoent.glucoseapplication.pojo;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Dieteticien extends Personne{

    @OneToMany(mappedBy = "dieteticien")
    @JsonbTransient
    private List<Utilisateur> listUtilisateurs = new ArrayList<>();

    public Dieteticien(){
    }
    @JsonbTransient
    public List<Utilisateur> getListUtilisateurs() {
        return listUtilisateurs;
    }

    public void setListUtilisateurs(List<Utilisateur> listUtilisateurs) {
        this.listUtilisateurs = listUtilisateurs;
    }

}
