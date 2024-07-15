package com.example.covoiturage.repository;

import com.example.covoiturage.JpaUtil;
import com.example.covoiturage.model.Trajet;
import com.example.covoiturage.model.Vehicule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrajetRepository {
    public void addTrajet(Trajet trajet) {
        //recuperant les informations de l'utilisateur connecté
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(trajet);
        //entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void deleteTrajet(Long id){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Trajet trajet = entityManager.find(Trajet.class, id);
        entityManager.remove(trajet);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void updateTrajet(Trajet trajet){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(trajet);
        entityManager.getTransaction().commit();
        entityManager.close();
        // entityManagerFactory.close();
    }
    public List<Trajet> getAllTrajet(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Trajet> trajet = entityManager.createQuery("from Trajet ", Trajet.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        //entityManagerFactory.close();
        return trajet;
    }

   /* public Map<Month, Integer> getTrajetsParMois() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Object[]> result = entityManager.createQuery("SELECT MONTH(t.dateHeureDepart), COUNT(t) FROM Trajet t GROUP BY MONTH(t.dateHeureDepart)").getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

        Map<Month, Integer> trajetsParMois = new HashMap<>();
        for (Object[] objects : result) {
            int month = ((Number) objects[0]).intValue();
            int count = ((Number) objects[1]).intValue();
            trajetsParMois.put(Month.of(month), count);
        }
        return trajetsParMois;
    }*/
}
