package com.example.covoiturage.repository;

import com.example.covoiturage.JpaUtil;
import com.example.covoiturage.model.Utilisateur;
import com.example.covoiturage.model.Vehicule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.ObservableList;

import java.util.List;

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
    public List<Vehicule> getAllVehicule(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Vehicule> vehicule = entityManager.createQuery("from Vehicule ", Vehicule.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
       // entityManagerFactory.close();
        return vehicule;
    }
    public void updateVehicule(Vehicule vehicule){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(vehicule);
        entityManager.getTransaction().commit();
        entityManager.close();
       // entityManagerFactory.close();
    }
    public void deleteVehicule(Long id){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Vehicule vehicule = entityManager.find(Vehicule.class, id);
        entityManager.remove(vehicule);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public List<Vehicule> searchVehicule(String search){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Vehicule> vehicule = entityManager.createQuery("from Vehicule v where v.marque like :search or v.modele like :search or v.immatriculation like :search", Vehicule.class).setParameter("search", "%"+search+"%").getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return vehicule;
    }
}
