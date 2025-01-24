package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Categorie;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class CategorieModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Categorie categorie) {
        em.persist(categorie);
    }
    public Categorie update(Categorie categorie) {
        return  em.merge(categorie);
    }
    public void delete(Categorie categorie) {
        em.remove(em.merge(categorie));
    }
    public Categorie read(int id) {
        return em.find(Categorie.class, id);
    }
    public List<Categorie> read(){
        Query query = em.createQuery("select categories from Categorie as categories ", Categorie.class);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}

