package com.example.covoiturage.repository;

import com.example.covoiturage.JpaUtil;
import com.example.covoiturage.model.Reservation;
import com.example.covoiturage.model.Trajet;
import com.example.covoiturage.model.Vehicule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


import java.util.List;

public class ReservationRepository {
     public List<Reservation> getAllReservation(){
     EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
     EntityManager entityManager = entityManagerFactory.createEntityManager();
     entityManager.getTransaction().begin();
     List<Reservation> reservation = entityManager.createQuery("from Reservation ", Reservation.class).getResultList();
     entityManager.getTransaction().commit();
     entityManager.close();
    return reservation;
    }
    public Long countReservation(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Long resservation = entityManager.createQuery("select count(u) from Reservation u", Long.class).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return resservation;
    }
    public List<Reservation> getAllReservationStarted(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Reservation> reservation = entityManager.createQuery("from Reservation  where etat='en cours' ", Reservation.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return reservation;
    }
    public List<Reservation> getAllReservationFinished(){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Reservation> reservation = entityManager.createQuery("from Reservation  where etat='terminé'", Reservation.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return reservation;
    }
    public void addReservation(Reservation reservation){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(reservation);
        Trajet trajet = reservation.getTrajet();
        trajet.setPlacesDisponibles(trajet.getPlacesDisponibles()-reservation.getPlacesReservees());
        entityManager.merge(trajet);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateReservation(Reservation reservation){
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(reservation);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void deleteReservation(Long id){
    }
    public void searchReservation(String search){
    }
}
