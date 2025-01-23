package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Medecin;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class MedecinModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Medecin medecin) {
        em.persist(medecin);
    }
    public Medecin update(Medecin medecin) {
        return  em.merge(medecin);
    }
    public void delete(Medecin medecin) {
        em.remove(em.merge(medecin));
    }
    public Medecin read(int id) {
        return em.find(Medecin.class, id);
    }
    public List<Medecin> read(){
        Query query = em.createQuery("select medecins from Medecin as medecins ", Medecin.class);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
