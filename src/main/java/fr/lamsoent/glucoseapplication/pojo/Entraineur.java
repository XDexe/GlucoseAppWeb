package fr.lamsoent.glucoseapplication.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Entraineur extends Personne {

    @OneToMany(mappedBy = "entraineur")
    private List<Utilisateur> listUtilisateurs = new ArrayList<>();

    public Entraineur() {
    }

    public List<Utilisateur> getListUtilisateurs() {
        return listUtilisateurs;
    }

    public void setListUtilisateurs(List<Utilisateur> listUtilisateurs) {
        this.listUtilisateurs = listUtilisateurs;
    }


}
