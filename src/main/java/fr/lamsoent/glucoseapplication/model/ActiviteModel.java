package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Activite;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class ActiviteModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Activite activite) {
        em.persist(activite);
    }
    public Activite update(Activite activite) {
        return  em.merge(activite);
    }
    public void delete(Activite activite) {
        em.remove(em.merge(activite));
    }
    public Activite read(int id) {
        return em.find(Activite.class, id);
    }
    public List<Activite> read(){
        Query query = em.createQuery("select activites from Activite as activites ", Activite.class);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}

