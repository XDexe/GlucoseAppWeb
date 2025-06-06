package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Capteur;
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

    public Utilisateur readByCapteur(Capteur capteur){

        Query query = em.createQuery("select utilisateurs from Utilisateur  as utilisateurs where utilisateurs.capteur.mac = :capteur", Utilisateur.class);
        query.setParameter("capteur",capteur.getMac());
        return (Utilisateur) query.getSingleResult();
    }

    public List<Utilisateur> readByRole(String roleName) {
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.role.nomRole = :roleName", Utilisateur.class);
        query.setParameter("roleName", roleName);
        return query.getResultList();
    }

    public List<Utilisateur> readByMedecin(int medecinId) {
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.medecin.idPersonne = :medecinId", Utilisateur.class);
        query.setParameter("medecinId", medecinId);
        return query.getResultList();
    }

    public List<Utilisateur> readByDieteticien(int dieteticienId) {
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.dieteticien.idPersonne = :dieteticienId", Utilisateur.class);
        query.setParameter("dieteticienId", dieteticienId);
        return query.getResultList();
    }

    public List<Utilisateur> readByEntraineur(int entraineurId) {
        Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.entraineur.idPersonne = :entraineurId", Utilisateur.class);
        query.setParameter("entraineurId", entraineurId);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
