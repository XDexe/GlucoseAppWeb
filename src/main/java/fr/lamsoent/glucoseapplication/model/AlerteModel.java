package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Alerte;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
@Stateless
public class AlerteModel {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Alerte alerte) {
        em.persist(alerte);
    }
    public Alerte update(Alerte alerte) {
        return em.merge(alerte);
    }
    public void delete(Alerte alerte) {
        em.remove(em.merge(alerte));
    }
    public Alerte read(int id) {
        return em.find(Alerte.class, id);
    }
    public List<Alerte> read(){
        Query query = em.createQuery("select alertes from Alerte as alertes ", Alerte.class);
        return query.getResultList();
    }

}

