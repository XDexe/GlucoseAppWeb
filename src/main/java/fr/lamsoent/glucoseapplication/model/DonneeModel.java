package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Donnee;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class DonneeModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Donnee donnee) {
        em.persist(donnee);
    }
    public Donnee update(Donnee donnee) {
        return  em.merge(donnee);
    }
    public void delete(Donnee donnee) {
        em.remove(em.merge(donnee));
    }
    public Donnee read(int id) {
        return em.find(Donnee.class, id);
    }
    public List<Donnee> read(){
        Query query = em.createQuery("select datas from Donnee as datas ", Donnee.class);
        return query.getResultList();
    }

    public List<Donnee> donneeListWithIdActivite(int idActivite){
        Query query = em.createQuery("SELECT d FROM Donnee d WHERE d.activite.id = :idActivite", Donnee.class);
        query.setParameter("idActivite", idActivite);
        return query.getResultList();
    }

    public List<Donnee> donneeWithUser(Utilisateur utilisateur) {
        Query query = em.createQuery("select d from Donnee d where d.activite.utilisateur.idPersonne = :idPersonne");
        query.setParameter("idPersonne", utilisateur.getIdPersonne());
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}


