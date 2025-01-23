package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Dieteticien;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class DieteticienModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Dieteticien dieteticien) {
        em.persist(dieteticien);
    }
    public Dieteticien update(Dieteticien dieteticien) {
        return  em.merge(dieteticien);
    }
    public void delete(Dieteticien dieteticien) {
        em.remove(em.merge(dieteticien));
    }
    public Dieteticien read(int id) {
        return em.find(Dieteticien.class, id);
    }
    public List<Dieteticien> read(){
        Query query = em.createQuery("select dieteticiens from Dieteticien as dieteticiens ", Dieteticien.class);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
