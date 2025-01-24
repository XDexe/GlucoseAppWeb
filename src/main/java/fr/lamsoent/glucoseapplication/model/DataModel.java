package fr.lamsoent.glucoseapplication.model;

import fr.lamsoent.glucoseapplication.pojo.Data;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class DataModel {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public void create(Data data) {
        em.persist(data);
    }
    public Data update(Data data) {
        return  em.merge(data);
    }
    public void delete(Data data) {
        em.remove(em.merge(data));
    }
    public Data read(int id) {
        return em.find(Data.class, id);
    }
    public List<Data> read(){
        Query query = em.createQuery("select datas from Data as datas ", Data.class);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}


