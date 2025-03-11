package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Personne;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class PersonneModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

   
    public Personne read(int id) {
        return em.find(Personne.class, id);
    }
    public List<Personne> read(){
        Query query = em.createQuery("select personnes from Personne as personnes ", Personne.class);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
