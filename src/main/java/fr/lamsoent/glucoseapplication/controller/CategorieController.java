package fr.lamsoent.glucoseapplication.controller;

import fr.lamsoent.glucoseapplication.model.CategorieModel;
import fr.lamsoent.glucoseapplication.pojo.Categorie;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class CategorieController implements Serializable {

    @EJB
    private CategorieModel categorieModel;

    private Categorie categorie = new Categorie();

    public void editCategorie() {
        categorieModel.update(this.categorie);
        categorie = new Categorie();
    }

    public void deleteCategorie(Categorie categorie) {
        categorieModel.delete(categorie);
    }

    public void loadCategorie(Categorie categorie) {
        this.categorie=categorie;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Categorie> getCategories() {
        return categorieModel.read();
    }

    public void resetForm() {
        this.categorie = new Categorie();
    }

}
