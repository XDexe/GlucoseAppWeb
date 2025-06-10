package fr.lamsoent.glucoseapplication.pojo;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Dieteticien extends Personne{

    @JsonbTransient
    @OneToMany(mappedBy = "dieteticien", cascade = CascadeType.ALL)
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
