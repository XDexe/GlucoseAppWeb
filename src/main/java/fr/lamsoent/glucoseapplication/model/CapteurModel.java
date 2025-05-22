package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Capteur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class CapteurModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Capteur capteur) {
        em.persist(capteur);
    }
    public Capteur update(Capteur capteur) {
        return  em.merge(capteur);
    }
    public void delete(Capteur capteur) {
        em.remove(em.merge(capteur));
    }
    public Capteur read(int id) {
        return em.find(Capteur.class, id);
    }
    public List<Capteur> read(){
        Query query = em.createQuery("select capteurs from Capteur as capteurs ", Capteur.class);
        return query.getResultList();
    }

    public Capteur findBySn(String sn) {
        Query query = em.createQuery("SELECT c FROM Capteur c WHERE c.numeroSerie = :sn", Capteur.class);
        query.setParameter("sn", sn);
        List<Capteur> capteurs = query.getResultList();
        if (capteurs.isEmpty()) {
            return null;
        } else {
            return capteurs.get(0);
        }
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}


