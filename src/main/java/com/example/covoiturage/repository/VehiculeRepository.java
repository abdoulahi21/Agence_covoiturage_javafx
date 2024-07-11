package com.example.covoiturage.repository;

import com.example.covoiturage.JpaUtil;
import com.example.covoiturage.model.Utilisateur;
import com.example.covoiturage.model.Vehicule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class VehiculeRepository {

    public void addVehicule(Vehicule vehicule) {
        //recuperant les informations de l'utilisateur connecté
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(vehicule);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
