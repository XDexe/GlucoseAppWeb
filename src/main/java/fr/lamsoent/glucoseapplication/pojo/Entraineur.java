package fr.lamsoent.glucoseapplication.pojo;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Entraineur extends Personne {

    @OneToMany(mappedBy = "entraineur", cascade = CascadeType.ALL)
    @JsonbTransient
    private List<Utilisateur> listUtilisateurs = new ArrayList<>();

    public Entraineur() {
    }
    @JsonbTransient
    public List<Utilisateur> getListUtilisateurs() {
        return listUtilisateurs;
    }

    public void setListUtilisateurs(List<Utilisateur> listUtilisateurs) {
        this.listUtilisateurs = listUtilisateurs;
    }


}
