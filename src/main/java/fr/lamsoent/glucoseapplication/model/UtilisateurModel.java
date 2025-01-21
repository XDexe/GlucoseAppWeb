package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class UtilisateurModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Utilisateur utilisateur) {
        em.persist(utilisateur);
    }
    public Utilisateur update(Utilisateur utilisateur) {
        return  em.merge(utilisateur);
    }
    public void delete(Utilisateur utilisateur) {
        em.remove(em.merge(utilisateur));
    }
    public Utilisateur read(int id) {
        return em.find(Utilisateur.class, id);
    }
    public List<Utilisateur> read(){
        Query query = em.createQuery("select utilisateurs from Utilisateur as utilisateurs ", Utilisateur.class);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
