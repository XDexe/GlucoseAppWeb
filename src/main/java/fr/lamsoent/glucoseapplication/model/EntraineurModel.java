package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Entraineur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class EntraineurModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Entraineur entraineur) {
        em.persist(entraineur);
    }
    public Entraineur update(Entraineur entraineur) {
        return  em.merge(entraineur);
    }
    public void delete(Entraineur entraineur) {
        em.remove(em.merge(entraineur));
    }
    public Entraineur read(int id) {
        return em.find(Entraineur.class, id);
    }
    public List<Entraineur> read(){
        Query query = em.createQuery("select entraineurs from Entraineur as entraineurs ", Entraineur.class);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
