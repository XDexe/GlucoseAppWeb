package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Activite;
import fr.lamsoent.glucoseapplication.pojo.Capteur;
import fr.lamsoent.glucoseapplication.pojo.Donnee;
import fr.lamsoent.glucoseapplication.pojo.Utilisateur;
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
        return em.merge(activite);
    }

    public List<Activite> getActivitesByCapteur(Capteur capteur) {
        Query query = em.createQuery("SELECT a FROM Activite a WHERE a.capteur = :paramCapteur", Activite.class);
        query.setParameter("paramCapteur", capteur);
        return query.getResultList();
    }

    public void delete(Activite activite) {
        if (activite == null || activite.getId() == 0) {
            return; // Ne rien faire si l'activité est invalide
        }

        // Étape 1 : Interroger explicitement la base pour obtenir la liste des enfants.
        // Cette approche est plus fiable que de naviguer via la collection de l'objet parent,
        // car elle nous garantit d'obtenir une liste à jour, indépendamment du cache.
        Query findDonneesQuery = em.createQuery("SELECT d FROM Donnee d WHERE d.activite.id = :activiteId");
        findDonneesQuery.setParameter("activiteId", activite.getId());
        List<Donnee> donneesToDelete = findDonneesQuery.getResultList();

        // Étape 2 : Parcourir cette liste fraîchement obtenue et supprimer chaque enfant.
        for (Donnee donnee : donneesToDelete) {
            em.remove(donnee);
        }

        // Étape 3 : Maintenant que les enfants sont garantis d'être supprimés,
        // nous pouvons supprimer l'activité parente en toute sécurité.
        Activite activiteToDelete = em.find(Activite.class, activite.getId());
        if (activiteToDelete != null) {
            em.remove(activiteToDelete);
        }
    }
    public Activite read(int id) {
        return em.find(Activite.class, id);
    }
    public List<Activite> read(){
        Query query = em.createQuery("select activites from Activite as activites ", Activite.class);
        return query.getResultList();
    }

    public List<Activite> getActivitesUtilisateur(Utilisateur utilisateur){
        Query query = em.createQuery("select activites from Activite as activites where activites.utilisateur.idPersonne = :id order by activites.dateDebut DESC ", Activite.class);
        query.setParameter("id",utilisateur.getIdPersonne());
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}

