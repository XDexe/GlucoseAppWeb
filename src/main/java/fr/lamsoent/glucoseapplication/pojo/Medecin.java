package fr.lamsoent.glucoseapplication.pojo;

import fr.lamsoent.glucoseapplication.model.UtilisateurModel;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Medecin extends Personne{

    @JsonbTransient
    @OneToMany(mappedBy = "medecin")
    private List<Utilisateur> listUtilisateurs = new ArrayList<>();

    public Medecin(){
    }
    @JsonbTransient
    public List<Utilisateur> getListUtilisateurs() {
        return listUtilisateurs;
    }

    public void setListUtilisateurs(List<Utilisateur> listUtilisateurs) {
        this.listUtilisateurs = listUtilisateurs;
    }




}
