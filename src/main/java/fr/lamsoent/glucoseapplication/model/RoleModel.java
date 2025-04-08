package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Role;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Singleton
@Startup
public class RoleModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @PostConstruct
    public void init() {
        createRole();
    }

    public void createRole() {
        String[] roleNames = {"DEFAUT", "ADMINISTRATEUR"};

        for (String roleName : roleNames) {
            if (getRoleByName(roleName) == null) {
                Role role = new Role();
                role.setNomRole(roleName);
                create(role);
                System.out.println("Role created: " + roleName);
            }
        }
    }

    public void create(Role role) {
        em.persist(role);
    }

    public Role update(Role role) {
        return em.merge(role);
    }

    public void delete(Role role) {
        em.remove(em.merge(role));
    }

    public Role read(int id) {
        return em.find(Role.class, id);
    }

    public List<Role> read() {
        Query query = em.createQuery("SELECT r FROM Role r", Role.class);
        return query.getResultList();
    }

    public Role getRoleByName(String roleName) {
        try {
            Query query = em.createQuery("SELECT r FROM Role r WHERE r.nomRole = :roleName", Role.class);
            query.setParameter("roleName", roleName);
            return (Role) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Role getOrCreateRoleByName(String roleName) {
        Role role = getRoleByName(roleName);
        if (role == null) {
            role = new Role();
            role.setNomRole(roleName);
            create(role);
        }
        return role;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
